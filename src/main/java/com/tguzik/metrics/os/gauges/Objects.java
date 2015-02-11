package com.tguzik.metrics.os.gauges;

import javax.annotation.Nonnull;
import javax.annotation.meta.When;

/**
 * Shim class for convenience methods found in {@link java.util.Objects} on JDK 8.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
final class Objects {

    @Nonnull( when = When.ALWAYS )
    public static <T> T requireNonNull( T object ) throws NullPointerException {
        if ( object == null ) {
            throw new NullPointerException();
        }

        return object;
    }

    private Objects() {
    }
}
