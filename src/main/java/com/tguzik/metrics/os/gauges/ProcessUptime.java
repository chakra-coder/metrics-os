package com.tguzik.metrics.os.gauges;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.codahale.metrics.Gauge;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@ParametersAreNonnullByDefault
public class ProcessUptime implements Gauge<Long> {
    private final RuntimeMXBean runtimeMxBean;

    public ProcessUptime() {
        this( ManagementFactory.getRuntimeMXBean() );
    }

    public ProcessUptime( RuntimeMXBean runtimeMxBean ) {
        this.runtimeMxBean = Objects.requireNonNull( runtimeMxBean );
    }

    @Override
    public Long getValue() {
        return runtimeMxBean.getUptime();
    }
}
