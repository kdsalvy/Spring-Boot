# Catalog Service

Sample Project to Understand the concepts of Spring Cloud Modules

* The Client Application which is going to call Rating-Data-Service and Movies-Info-Service. 
* The mentioned web services are being called using service discovery through Eureka (Services registered at Eureka server)
* For Fault Tolerance and Resilience, Hystrix is being used to provide fallback methods and circuit-breaker features
* The Config is being fetched from a central config server for Catalog Service and Movies Info Service
* RoundRobin load balancing has been implemented using Ribbon module

## Work In Progress Concepts
* Retry and BackOff strategies using Spring Cloud - Netflix Ribbon Module