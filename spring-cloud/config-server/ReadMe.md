# Config Server

*The central configuration server which fetches the latest configuration from Git and then we can call /actuator/refresh API to refresh the configurations of the client without bouncing the server(Provided Client has @RefreshScope on the @Configuration class whose properties need to be refreshed).

* Included Sleuth and Zipkin dependency for distributed tracing
* Integrated Spring CLoud Bus for auto config fetch and refresh for services.

## Spring Cloud Bus Details for Config server

* Need to run rabbitmq server for this feature to work.
* Most simple way to do that is using docker image. Run below command after installing docker to start a rabbitmq server.

```
docker pull rabbitmq:3-management
docker run -d --hostname my-rabbit --name some-rabbit-new -p 15672:15672 -p 5672:5672 rabbitmq:3-management
``` 
* All the dependencies and configurations must be in place, both in config server and client.
* We can add a webhook to call /monitor uri of config-server to automatically update the configurations or we can do it manually by below step for all services at once. 
* We can directly call /actuator/bus-refresh uri to refresh the configurations. This will refresh all the configurations in config server and all other services connected to cloud bus.  
