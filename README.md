metrics-os
==========

Include extra operating system and JVM metrics with your application. This is an extension to the
[dropwizard metrics](https://github.com/dropwizard/metrics) library that aims to include more detailed
view on what goes on the machine.

So far this library provides following metrics:

* Through `CurrentProcessGaugeSet`:
    * `process.uptime` - uptime of the JVM in milliseconds

* Through `OperatingSystemGaugeSet`:
    * `os.load` - load average of the operating system for the last minute
        * This is standard Unix `load` - the sum of the number of runnable entities (threads/processes) queued to the
          available processors
        * Note that this metric might be unavailable on some operating systems.
    * `os.info` - string value that includes the name of the operating system, its version, available processors and
      their architecture. Useful when working in diverse computing environment (different OS patch levels, different
      numbers of cores).


The plan is to include additional metrics such as current CPU usage (estimated) or time spent in GC soon. The
functionality already included in `io.dropwizard.metrics:metrics-jvm` will not be duplicated here.


Is this PROD worthy?
--------------------

Not yet. This library is still in prototyping phase.


How do I get it?
----------------

Since this library is still in prototyping phase, it is not yet available on Maven Central. As soon as things
stabilise, it will be available from there.


Contributing
------------

You are more than welcome to contribute! If there's something you would like to see in this library, please open a
feature request through GitHub issues. If there's some code you would like submit, please don't hesitate to open a
pull request.


Dependencies
------------

* JDK 1.7+
* [dropwizard metrics](https://github.com/dropwizard/metrics) 3.1.0+
* Maven 3+


License
-------

This library is available under [MIT License](LICENSE).
