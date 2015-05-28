RANDOM JAVA APP
===============

# How to use

* Run
    ```
    java -jar bin/app.jar [port_number]
    ```
    _parameter port_number is optional. If none given it defaults to port 8000_

* Access localhost:[port_number]
* Available users: user1, user2, user2
* Password is equal to the username
* Each user has allowed to access a single page: user1 can access page_1, user2
  can access page_2, and user3 can access page_3. All the users have access to
  /index and /logout. Every non-logged in user has access to the /login page.

# Arquitecture

## HttpFoundation

Abstraction to handle http Requests and Responses

## Framework

It has the following components:

1. Authentication
    In charge of authenticating the user of the given request.
    The accessing user get's assigned a role.
    If no user information, then the current user role is annonymous.
1. Authorization
    Given a role, this component takes care of handling if the current user has
    access to the requested URI.
1. Controller
    Given the requested URI and HTTP method, finds out which controller should
    handle the request.
1. SessionStorage
    Repository for all the session information.
1. Templating
    Templating engine, Accepts a template name and an array of values, and
    returns the content of the template with the placeholders substituted.
1. AppKernel
    Puts together all the previows components
1. HttpKernel
    Translates HttpExchange to HttpFoundation abstraction and back to
    HttpExchange

## App

Defines the application specific logic.

1. Controller
    Has one controller for action that the framework supports
1. Model
    Definition of the user entity and user repository
1. Templates
    Application templates
1. AppKernel
    Application specific kernel. Where all the configuration of the app is put
    together
1. AuthenticationResolver
    Implementation of the authentication mechanism

# Things to do (A lot)

1. Add comments
1. Add tests
1. Extract the AppKernel configuration into an actual DI container
1. Expand each one of the components (authentication, authorization,
   controller, templating) to be really usable.
1. Expand HttpFoundation to support multiple headers (for example, now it only
   supports one cookie at a time, which was enough to support sessions)
1. Make explicit where the boundaries of a single request are. Right now every
   object except the generated Request is shared between different requests (at
   least in the same handler).
