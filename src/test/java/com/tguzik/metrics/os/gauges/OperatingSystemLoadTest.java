package com.tguzik.metrics.os.gauges;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;

public class OperatingSystemLoadTest {
    private OperatingSystemMXBean os;
    private OperatingSystemLoad gauge;

    @Before
    public void setUp() throws Exception {
        this.os = mock( OperatingSystemMXBean.class );
        this.gauge = new OperatingSystemLoad( os );
    }

    @Test
    public void default_constructor_bootstraps_the_class_without_exceptions() {
        new OperatingSystemLoad().getValue();
    }

    @Test
    public void getValue_returns_some_value_with_no_exceptions_on_the_real_thing() {
        this.gauge = new OperatingSystemLoad( ManagementFactory.getOperatingSystemMXBean() );

        // NOTE: The actual value might be Double.NaN on Windows and this test won't fail.
        // There is no practical workaround to get the system load average on Windows from inside of the
        // JVM/management extensions, so we can't do much about it.
        assertThat( gauge.getValue() ).isNotNegative();
    }

    @Test
    public void getValue_returns_positive_numeric_value_from_OperatingSystemMXBean() {
        doReturn( 0.15 ).when( os ).getSystemLoadAverage();
        assertThat( gauge.getValue() ).isEqualTo( 0.15, Offset.offset( 0.00001 ) );
    }

    @Test
    public void getValue_returns_zero_from_OperatingSystemMXBean() {
        doReturn( 0.0 ).when( os ).getSystemLoadAverage();
        assertThat( gauge.getValue() ).isZero();
    }

    @Test
    public void getValue_returns_NaN_when_OperatingSystemMXBean_returns_negative_value() {
        // This can mean that the operating system doesn't support calculating load, or that the JVM doesn't know how
        // to talk to the system.

        doReturn( -Double.MIN_VALUE ).when( os ).getSystemLoadAverage();
        assertThat( gauge.getValue() ).isNaN();
    }

    @Test
    public void getValue_returns_NaN_when_OperatingSystemMXBean_returns_plus_infinity() {
        // Either JVM or the OS went crazy.

        doReturn( Double.POSITIVE_INFINITY ).when( os ).getSystemLoadAverage();
        assertThat( gauge.getValue() ).isNaN();
    }

    @Test
    public void getValue_returns_NaN_when_OperatingSystemMXBean_returns_negative_infinity() {
        // Either JVM or the OS went crazy.

        doReturn( Double.NEGATIVE_INFINITY ).when( os ).getSystemLoadAverage();
        assertThat( gauge.getValue() ).isNaN();
    }

    @Test
    public void getValue_returns_NaN_when_OperatingSystemMXBean_returns_NaN() {
        // Either JVM or the OS went crazy.

        doReturn( Double.NaN ).when( os ).getSystemLoadAverage();
        assertThat( gauge.getValue() ).isNaN();
    }

    @Test( expected = NullPointerException.class )
    public void constructor_throws_NullPointerException_when_RuntimeMXBean_is_null() {
        new OperatingSystemLoad( null );
    }
}
