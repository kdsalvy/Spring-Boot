# Catalog Service

Sample Project to Understand the concepts of Spring Cloud Modules

* The Client Application which is going to call Rating-Data-Service and Movies-Info-Service. 
* The mentioned web services are being called using service discovery through Eureka (Services registered at Eureka server)
* For Fault Tolerance and Resilience, Hystrix is being used to provide fallback methods and circuit-breaker features
* The Config is being fetched from a central config server for Catalog Service and Movies Info Service
* RoundRobin load balancing has been implemented using Ribbon module
* Feign Client Integration for calling movie-info-service and ratings-data-service in a declarative way.
* Retry using Feign Client (Commented Ribbon BackOffRetry Bean, doesn't work otherwise)
* Included Sleuth and Zipkin dependency for distributed tracing

## Work In Progress Concepts
* Retry and BackOff strategies using Ribbon and Spring Retry (Not Working)
* ~~Retry using Feign Client and Spring Retry (Intermittent Retries)~~
* CustomFeignDecoder is not working as expected. It masks the response and always call the fallback method, even when the API returns correct response 