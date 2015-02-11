package com.tguzik.metrics.os;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.tguzik.metrics.os.gauges.ProcessCpuTime;
import com.tguzik.metrics.os.gauges.ProcessUptime;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@ParametersAreNonnullByDefault
public class CurrentProcessGaugeSet implements MetricSet {
    private final RuntimeMXBean runtime;
    private final ThreadMXBean threads;

    public CurrentProcessGaugeSet() {
        this( ManagementFactory.getRuntimeMXBean(), ManagementFactory.getThreadMXBean() );
    }

    public CurrentProcessGaugeSet( RuntimeMXBean runtime, ThreadMXBean threads ) {
        this.runtime = runtime;
        this.threads = threads;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        final HashMap<String, Metric> metrics = new HashMap<>();

        metrics.put( "process.uptime", new ProcessUptime( runtime ) );
        metrics.put( "process.cpu.time", new ProcessCpuTime( threads ) );

        return metrics;
    }
}
