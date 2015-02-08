package com.tguzik.metrics.os;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.tguzik.metrics.os.gauges.ProcessUptime;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@ParametersAreNonnullByDefault
public class CurrentProcessGaugeSet implements MetricSet {
    private final RuntimeMXBean runtime;

    public CurrentProcessGaugeSet() {
        this( ManagementFactory.getRuntimeMXBean() );
    }

    public CurrentProcessGaugeSet( RuntimeMXBean runtime ) {
        this.runtime = runtime;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        final HashMap<String, Metric> metrics = new HashMap<>();

        metrics.put( "process.uptime", new ProcessUptime( runtime ) );

        return metrics;
    }
}
