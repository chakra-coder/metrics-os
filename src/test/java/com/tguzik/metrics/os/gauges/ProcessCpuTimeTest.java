package com.tguzik.metrics.os.gauges;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessCpuTimeTest {

    @BeforeClass
    public static void enableCpuTimeMeasurement() {
        // This is required only for the 'real thing' test really
        ManagementFactory.getThreadMXBean().setThreadCpuTimeEnabled( true );
    }

    private ThreadMXBean threads;
    private ProcessCpuTime gauge;

    @Before
    public void setUp() throws Exception {
        this.threads = mock( ThreadMXBean.class );
        doReturn( new long[] { 123L, 234L, 345L, 456L } ).when( threads ).getAllThreadIds();
        doReturn( 1234L ).when( threads ).getThreadCpuTime( 123L );
        doReturn( 2345L ).when( threads ).getThreadCpuTime( 234L );
        doReturn( 3456L ).when( threads ).getThreadCpuTime( 345L );
        doReturn( 4567L ).when( threads ).getThreadCpuTime( 456L );

        this.gauge = new ProcessCpuTime( threads );
    }

    @Test
    public void default_constructor_bootstraps_the_class_without_exceptions() {
        new ProcessCpuTime().getValue();
    }

    @Test
    public void getValue_returns_some_value_with_no_exceptions_on_the_real_thing() {
        this.gauge = new ProcessCpuTime( ManagementFactory.getThreadMXBean() );

        assertThat( gauge.getValue() ).isNotNegative();
    }

    @Test
    public void getValue_returns_sum_of_cpu_times_for_all_threads() {
        final long actual = gauge.getValue();

        assertThat( actual ).isEqualTo( 1234L + 2345L + 3456L + 4567L );
    }

    @Test
    public void getValue_returns_minus_one_when_getting_the_list_of_threads_fails() {
        doThrow( new RuntimeException() ).when( threads ).getAllThreadIds();

        final long actual = gauge.getValue();

        assertThat( actual ).isEqualTo( -1L );
    }

    @Test
    public void getValue_returns_minus_one_when_getting_individual_thread_cpu_time_fails() {
        doThrow( new RuntimeException() ).when( threads ).getThreadCpuTime( anyLong() );

        final long actual = gauge.getValue();

        assertThat( actual ).isEqualTo( -1L );
    }

    @Test
    public void sum_returns_value_for_exact_threads_passed_in_argument() {
        final long actual = gauge.sum( new long[] { 123L } );

        assertThat( actual ).isEqualTo( 1234L );
    }

    @Test
    public void sum_calculates_sum_of_cpu_time_for_thread_ids_passed_in_argument() {
        final long actual = gauge.sum( new long[] { 123L, 234L, 345L, 456L } );

        assertThat( actual ).isEqualTo( 1234L + 2345L + 3456L + 4567L );
    }

    @Test
    public void sum_skips_non_positive_measurements() {
        doReturn( -1000L ).when( threads ).getThreadCpuTime( 42L );

        final long actual = gauge.sum( new long[] { 123L, 42L, 234L, 345L, 456L } );

        assertThat( actual ).isEqualTo( 1234L + 2345L + 3456L + 4567L );
    }

    @Test
    public void sum_doesnt_throw_exceptions_when_given_empty_argument() {
        final long actual = gauge.sum( new long[ 0 ] );

        assertThat( actual ).isZero();
    }

}
