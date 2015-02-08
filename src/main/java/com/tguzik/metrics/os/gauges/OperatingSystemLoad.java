package com.tguzik.metrics.os.gauges;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import com.codahale.metrics.Gauge;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@ParametersAreNonnullByDefault
public class OperatingSystemLoad implements Gauge<Double> {
    private final OperatingSystemMXBean osMxBean;

    public OperatingSystemLoad() {
        this( ManagementFactory.getOperatingSystemMXBean() );
    }

    public OperatingSystemLoad( OperatingSystemMXBean osMxBean ) {
        if ( osMxBean == null ) {
            throw null;
        }

        this.osMxBean = osMxBean;
    }

    @Override
    public Double getValue() {
        final double value = osMxBean.getSystemLoadAverage();

        if ( Double.isInfinite( value ) || Double.isNaN( value ) || value < 0.0 ) {
            // First two don't require much explanation, but the last one does. Per OperatingSystemMXBean
            // docs, it will return negative value when the system load is not available.
            return Double.NaN;
        }

        return value;
    }
}
