# spring-reactive-stack
A Webservice project which is based on  Spring Reactive Framework using Spring WebFlux and MongoDB

https://spring.io/reactive

## Spring Reactive stack is mainly used to develop Non-Blocking IO based APIs. 

### How to Install and Run MongoDB
1. ```brew tap mongodb/brew```
2. ```brew install mongodb-community@4.2```
3. ```brew services start mongodb-community@4.2```
4. If in case mongodb misbehaves ```brew services restart mongodb-community@4.2```

### How to run Docker
1. Navigate to folder where Dockerfile is present
2. ```docker build .```
3. ```docker run -p 8080:8080 -d <docker-image-id>``` [The Docker image id will be available from Step #2]

### Hot to run docker-compose.yml
#### This is only required if you are running WebFlux client and Webflux Web-app [Please ignore if you are only running the web-app]
1. Build both the Dockerfiles
2. For Spring Reactive Client the context should be the path where Dockerfile for the project is available Example: ```context: /Users/a0r00rf/Downloads/spring-react-client```
3. Run ```docker-compose up```

#### Web-flux webclient: https://github.com/abhi117a/spring-reactive-stack-client
