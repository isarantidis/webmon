Spring boot was used for the development. The application reads the configuration from the application.yaml file.

Architecturally is consists by 4 services each in its own package. The logic was that these could be separately deployed in the future, so that
for example you could have multiple check services (for each geographic location). Then there could be a result aggregation service to which
the consumers would subscribe. The CheckResult entity could also be modified to include the location information.

1. .../service/check package
Reads list of urls and content checks from the application.yaml file. This hot stream is processed repeatedly 
by a parallel scheduler and is available for injection to other services.
The period of checking is configured via the webchecks.period property
The number of concurrent web requests to URLs is configured via the webchecks.concurrentRequests property.
Also the request timeout is configurable.

2. .../service/http package
Subscribes to the stream of results published by the 'check' service. 
Exposes two streams to be sent as SSE to clients, a single item stream accesible via localhost:8080/single
and a stream of windowed results (20sec period) accessible via localhost:8080/window

3. .../service/logger package
Subscribes to the stream of results published by the 'check' service. 
It aggregates data within a certain time window (check-store.window configuration property).
Logs the results to a file (logging.file configuration property).
Improvement: Currently all logs go to the same file. CheckResult data should go to a separate file.

4. .../service/resultStore package
Subscribes to the stream of results published by the 'check' service. 
It aggregates data within a certain time window (check-store.window configuration property).
Stores the data to an in memory database (H2) (preferably should be an append only event store or maybe a time series database)


There is also a very basic (and barely working) html page that reads the SSEs and presents the list of URLs, the result code and the latency