package com.tguzik.metrics.os.gauges;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.Clock;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@ParametersAreNonnullByDefault
public class OperatingSystemInfo extends CachedGauge<String> {
    private final OperatingSystemMXBean osMxBean;

    public OperatingSystemInfo() {
        this( ManagementFactory.getOperatingSystemMXBean(), 1, TimeUnit.HOURS, Clock.defaultClock() );
    }

    public OperatingSystemInfo( OperatingSystemMXBean osMxBean ) {
        this( osMxBean, 1, TimeUnit.HOURS, Clock.defaultClock() );
    }

    public OperatingSystemInfo( OperatingSystemMXBean osMxBean,
                                long timeout,
                                TimeUnit timeoutUnit,
                                Clock timeoutClock ) {
        super( Objects.requireNonNull( timeoutClock ), timeout, Objects.requireNonNull( timeoutUnit ) );
        this.osMxBean = Objects.requireNonNull( osMxBean );
    }

    @Override
    protected String loadValue() {
        return String.format( "%s, %s (%d %s cpus)",
                              osMxBean.getName(),
                              osMxBean.getVersion(),
                              osMxBean.getAvailableProcessors(),
                              osMxBean.getArch() );
    }
}
