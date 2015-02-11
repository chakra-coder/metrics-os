package com.tguzik.metrics.os.gauges;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Clock;
import org.junit.Before;
import org.junit.Test;

public class OperatingSystemInfoTest {
    private OperatingSystemMXBean os;
    private OperatingSystemInfo gauge;

    @Before
    public void setUp() throws Exception {
        this.os = mock( OperatingSystemMXBean.class );
        doReturn( "[arch name]" ).when( os ).getArch();
        doReturn( "[os name]" ).when( os ).getName();
        doReturn( "[os version]" ).when( os ).getVersion();
        doReturn( 42 ).when( os ).getAvailableProcessors();

        this.gauge = new OperatingSystemInfo( os );
    }

    @Test
    public void default_constructor_fully_bootstraps_the_gauge() {
        new OperatingSystemInfo().getValue();
    }

    @Test
    public void getValue_returns_some_value_with_no_exceptions_on_the_real_thing() {
        this.gauge = new OperatingSystemInfo( ManagementFactory.getOperatingSystemMXBean() );

        assertThat( gauge.getValue() ).isNotEmpty();
    }

    @Test
    public void getValue_returns_string_with_architecture_os_name_version_and_available_processors() {
        assertThat( gauge.getValue() ).isEqualTo( "[os name], [os version] (42 [arch name] cpus)" );
    }

    @Test
    public void getValue_caches_gauge_value() {
        final String first = gauge.getValue();
        final String second = gauge.getValue();

        assertThat( first ).isSameAs( second );
        verify( os, times( 1 ) ).getArch();
        verify( os, times( 1 ) ).getName();
        verify( os, times( 1 ) ).getVersion();
        verify( os, times( 1 ) ).getAvailableProcessors();
        verifyNoMoreInteractions( os );
    }

    @Test( expected = NullPointerException.class )
    public void constructor_throws_NullPointerException_when_OperatingSystemMXBean_is_null() {
        new OperatingSystemInfo( null );
    }

    @Test( expected = NullPointerException.class )
    public void constructor_throws_NullPointerException_when_TimeUnit_is_null() {
        new OperatingSystemInfo( mock( OperatingSystemMXBean.class ), 123, null, mock( Clock.class ) );
    }

    @Test( expected = NullPointerException.class )
    public void constructor_throws_NullPointerException_when_Clock_is_null() {
        new OperatingSystemInfo( mock( OperatingSystemMXBean.class ), 123, TimeUnit.HOURS, null );
    }
}
