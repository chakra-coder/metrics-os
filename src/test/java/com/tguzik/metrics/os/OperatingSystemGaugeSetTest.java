package com.tguzik.metrics.os;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.lang.management.OperatingSystemMXBean;
import java.util.Map;

import com.codahale.metrics.Metric;
import com.tguzik.metrics.os.gauges.OperatingSystemInfo;
import com.tguzik.metrics.os.gauges.OperatingSystemLoad;
import org.junit.Before;
import org.junit.Test;

public class OperatingSystemGaugeSetTest {
    private OperatingSystemGaugeSet gaugeSet;

    @Before
    public void setUp() throws Exception {
        this.gaugeSet = new OperatingSystemGaugeSet( mock( OperatingSystemMXBean.class ) );
    }

    @Test
    public void default_constructor_bootstraps_the_class_without_exceptions() {
        new OperatingSystemGaugeSet().getMetrics();
    }

    @Test
    public void getMetrics_returns_map_with_os_info() {
        final Map<String, Metric> actual = gaugeSet.getMetrics();

        assertThat( actual ).containsKey( "os.info" );
        assertThat( actual.get( "os.info" ) ).isExactlyInstanceOf( OperatingSystemInfo.class );
    }

    @Test
    public void getMetrics_returns_map_with_os_load() {
        final Map<String, Metric> actual = gaugeSet.getMetrics();

        assertThat( actual ).containsKey( "os.load" );
        assertThat( actual.get( "os.load" ) ).isExactlyInstanceOf( OperatingSystemLoad.class );
    }
}
