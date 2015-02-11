TODO
====

Items in no particular order.


* Create JMH-based benchmarks to test the impact on the target JVM. For example enabling
  CPU Time measurement can impact the performance of the JVM - we'd like to allow users
  of this library to be able to make a judgement call themselves whether they want that
  risk or not.

* Determine feasibility of a current CPU usage metric (either system-wide or just for the
  current JVM)

* Create metric that would count the time spent in GC - GarbageCollectorMXBean would be
  the source of the data. Need to determine feasibility of differentiating between
  minor and major collections.
