package com.tguzik.metrics.os.gauges;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.management.RuntimeMXBean;

import org.junit.Before;
import org.junit.Test;

public class ProcessUptimeTest {
    private RuntimeMXBean runtime;
    private ProcessUptime gauge;

    @Before
    public void setUp() throws Exception {
        this.runtime = mock( RuntimeMXBean.class );
        this.gauge = new ProcessUptime( runtime );
    }

    @Test
    public void default_constructor_bootstraps_the_class_without_exceptions_and_returns_positive_value() {
        final long value = new ProcessUptime().getValue();

        // What would it mean for the uptime to be negative anyway?
        assertThat( value ).isPositive();
    }

    @Test
    public void getValue_returns_the_value_from_RuntimeMXBean() {
        doReturn( 123L ).when( runtime ).getUptime();

        assertThat( gauge.getValue() ).isEqualTo( 123L );
    }
}
