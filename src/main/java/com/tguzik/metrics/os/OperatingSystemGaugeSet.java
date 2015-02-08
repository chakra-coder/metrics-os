package com.tguzik.metrics.os;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Clock;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.tguzik.metrics.os.gauges.OperatingSystemInfo;
import com.tguzik.metrics.os.gauges.OperatingSystemLoad;

/**
 * Provides gauges with basic operating system info, system load average and current system CPU utilization.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@ParametersAreNonnullByDefault
public class OperatingSystemGaugeSet implements MetricSet {
    private final OperatingSystemMXBean osMxBean;
    private final int cacheTimeoutValue;
    private final TimeUnit cacheTimeoutUnit;
    private final Clock cacheTimeoutClock;

    public OperatingSystemGaugeSet() {
        this( ManagementFactory.getOperatingSystemMXBean() );
    }

    public OperatingSystemGaugeSet( OperatingSystemMXBean osMxBean ) {
        this( osMxBean, 1, TimeUnit.HOURS, Clock.defaultClock() );
    }

    public OperatingSystemGaugeSet( OperatingSystemMXBean osMxBean,
                                    int cacheTimeoutValue,
                                    TimeUnit cacheTimeoutUnit,
                                    Clock cacheTimeoutClock ) {
        this.osMxBean = osMxBean;
        this.cacheTimeoutValue = cacheTimeoutValue;
        this.cacheTimeoutUnit = cacheTimeoutUnit;
        this.cacheTimeoutClock = cacheTimeoutClock;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        final Map<String, Metric> metrics = new HashMap<>();

        metrics.put( "os.load", new OperatingSystemLoad( osMxBean ) );
        metrics.put( "os.info",
                     new OperatingSystemInfo( osMxBean, cacheTimeoutValue, cacheTimeoutUnit, cacheTimeoutClock ) );

        return metrics;
    }
}
