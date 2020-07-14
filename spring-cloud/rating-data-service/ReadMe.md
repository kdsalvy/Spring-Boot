# Ratings Info Service

* This is a sample service that returns hardcoded ratings for harcoded movie Ids. This uses service discovery but unlike movies info service, it doesn't using config server in order to spawn multiple instances by passing port in command line to test load balancing.

* Included Sleuth and Zipkin dependency for distributed tracing