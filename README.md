## Fry Rank ##

Become the world's most-esteemed French Fry food critic! This web application enables users to rate french fries at all of their favorite restaurants.

### Tech Stack ###

1. MongoDB Database (NoSQL)
2. Spring Boot Backend, written in Java
3. ReactJS frontend

### Current Status ###

The app is functional, allowing users to view reviews and create new ones for a pre-set list of restaurants.

In progress:
- Hosting the website on a server so it is available to the public internet
- Integrating with Google APIs to retrieve restaurants worldwide

### Rapid Testing Instructions ###
1. Create a `.env` file. Reference the `.env.sample` file for help with the env variables to set.
2. We use the Springdoc-OpenAPI library to automate API documentation. 
   It provides a standard, language-agnostic interface for developers to visualize and interact with the API. 
   To enable, set `springdoc.swagger-ui.enabled=true` in your local `.env` file. After starting the application locally or via Docker, 
   `visit http://localhost:8080/swagger-ui/index.html` for use.
3. From the repository root: run `docker-compose up -d --build`

