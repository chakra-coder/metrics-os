package com.tguzik.metrics.os.gauges;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.lang.management.OperatingSystemMXBean;

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

    @Test
    public void default_constructor_fully_bootstraps_the_gauge() {
        this.gauge = new OperatingSystemInfo();
        final String actual = gauge.getValue();

        assertThat( actual ).isNotEmpty();
    }
}
