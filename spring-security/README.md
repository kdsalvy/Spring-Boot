# Spring Boot Security

The project demonstrates various Spring Security implementations

* In Memory Authentication + Authorization (Not Recommended for live projects)
* DB Authentication + Authorization (Authentication from DB where user information is stored)
	* Using JDBC (Retrieving User information through JDBC)
	* Using JPA (Retrieving User information through JPA)
* JWT Authorization + JPA Authentication (Authentication from DB + Authorizing each call with a JWT token)
* LDAP Authentication (Authentication from an embedded LDAP server -- Needs to be replaced with real LDAP server in live project)
* OAuth Authentication (Authenticating using Facebook) 

## Configurations Explained

### Feature Flag

To show all the security implementations in a single project a feature flag has been used. 

```
kd:
  app:
    security:
      config: in-mem # oauth(default), ldap, in-mem, jdbc, jpa, jwt
```
You just need to mention the security type you wish to be configured and the same will get used in the project. By default it will use OAuth.

### LDAP configuration

Below is the configuration for an embedded LDAP server. You can replace embedded with an LDAP server's details 

```
spring:  
  ldap:
    embedded:
      port: 8389
      ldif: classpath:ldap-data.ldif
      base-dn: dc=springframework,dc=org
```

### OAuth Configuration

Below is the OAuth Configuration to login by authenticating using Facebook.

```
security:
  oauth2:
    client:
      clientId: {facebook app account id}
      clientSecret: {facebook app account secret}
      accessTokenUri: https://graph.facebook.com/oauth/access_token
      userAuthorizationUri: https://www.facebook.com/dialog/oauth
      tokenName: oauth_token
      authenticationScheme: query
      clientAuthenticationScheme: form
    resource:
      userInfoUri: https://graph.facebook.com/me
```
