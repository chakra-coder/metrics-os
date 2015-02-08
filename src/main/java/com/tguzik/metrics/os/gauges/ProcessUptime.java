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
    private final RuntimeMXBean runtime;

    public ProcessUptime() {
        this( ManagementFactory.getRuntimeMXBean() );
    }

    public ProcessUptime( RuntimeMXBean runtime ) {
        if ( runtime == null ) {
            // I'd look nicer if we had JDK 8 or Guava.
            throw null;
        }

        this.runtime = runtime;
    }

    @Override
    public Long getValue() {
        return runtime.getUptime();
    }
}
