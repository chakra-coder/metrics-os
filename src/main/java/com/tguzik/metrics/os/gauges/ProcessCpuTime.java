package com.tguzik.metrics.os.gauges;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import com.codahale.metrics.Gauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@ParametersAreNonnullByDefault
public class ProcessCpuTime implements Gauge<Long> {
    private final ThreadMXBean threadMxBean;

    public ProcessCpuTime() {
        this( ManagementFactory.getThreadMXBean() );
    }

    public ProcessCpuTime( ThreadMXBean threadMxBean ) {
        this.threadMxBean = Objects.requireNonNull( threadMxBean );

        if ( !threadMxBean.isThreadCpuTimeEnabled() ) {
            // We could try to enable it ourselves, but it's generally a bad idea to change anything on
            // user's JVM without their explicit consent.

            final Logger logger = LoggerFactory.getLogger( getClass() );
            logger.warn( "CPU Time measurement for JVM threads is disabled" );
            logger.info( "You can enable CPU Time measurement by ManagementFactory.getThreadMXBean()" +
                         ".setThreadCpuTimeEnabled(true);" );
        }
    }

    @Override
    public Long getValue() {
        try {
            return sum( threadMxBean.getAllThreadIds() );
        }
        catch ( Exception IGNORED ) {
            // Not much we can do about it..
            // Since we're inside of a metric, it would be extremely annoying to the user if we logged an exception
            // each time the exception was thrown.
            return -1L;
        }
    }

    protected long sum( final long[] activeThreads ) {
        long accumulator = 0;

        for ( final long threadId : activeThreads ) {
            final long threadTimeNanos = threadMxBean.getThreadCpuTime( threadId );

            if ( threadTimeNanos < 0 ) {
                continue;
            }

            accumulator += threadTimeNanos;
        }

        return accumulator;
    }
}
