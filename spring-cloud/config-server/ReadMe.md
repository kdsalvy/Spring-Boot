# Config Server

The central configuration server which fetches the latest configuration from Git and then
we can call /actuator/refresh API to refresh the configurations of the client without bouncing the server.
(Provided Client has @RefreshScope on the @Configuration class whose properties need to be refreshed)
 
