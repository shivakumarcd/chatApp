### Quick start
* cd collabchat
* java -version         ##### 20 
* ./gradlew clean
* ./gradlew build -x test    # to build skipping tests
* ./gradlew build   OR   ./gradlew test   # to run tests
* docker build -t collabchat .
*  to start app with mysql db
   * docker-compose up
   * docker ps -a       ##### to health check running containers (Collabchat, mysql, nginx)
* all API/endpoint documentation available at :  
  * http://localhost:8080/swagger-ui.html
* Simple static server rendered HTML+JavaScript front end for testing above API endpoints(CRUD, Login, etc)
  * https://localhost/test-simple-post.html             or
  * http://localhost:8080/test-simple-post.html/    ##### currently blocked       
  
  * To make your browser trust the self-signed certificate, you may need to add collabchat/certs/fullchain.pem to your system's trusted certificates.
  * docker logs d6d5c4782781 ##### to check logs. Replace stopped/running container id with your own
  * docker cp d6d5c4782781:/app/logs ./logs  ##### To copy logs. Replace stopped/running container id with your own
* To start and check record inserted in database
  * docker start -ai collabchat-mysql 
  * docker exec -it collabchat-mysql mysql -uroot -prootpass collabchat_db
    * mysql> show tables


# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.6/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.6/gradle-plugin/packaging-oci-image.html)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

---

### Build and Run Instructions

#### Java Version Compatibility : 20

To build the project:
Ensure you have Java 20 and Gradle installed. Then, run the following commands in the project root directory:

```
./gradlew clean  
./gradlew build
./gradlew build -x test     //to skip tests
```
To run the project:

```
./gradlew bootRun   // no longer works as app uses external DB, skip to next step to start using docker-compose
```

The application will start on [http://localhost:8080](http://localhost:8080).

All api documentation is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).



### Docker Instructions

To build the Docker image (ensure Docker Desktop is running):

```
docker build -t collabchat .
```

To run the Docker container:

```
docker run -p 8080:8080 collabchat //no longer works as app depends on external DB
docker-compose up  // to start with external DB as per docker-compose.yml

```

### Testing the Endpoints via browser UI

To test the HelloController endpoint, open your browser or use curl:

To test server running status:
```
curl http://localhost:8080/hello
```
http://localhost:8080/hello

To test POST endpoint  ```http://localhost:8080/simplePost```
use http://localhost:8080/test-simple-post.html

### Start with Docker Compose

Step1 : To generating Self-Signed SSL Certificates(Optional as certificated already provided in repo for testing purpose only)
```
cd ./collabachat/nginx/certs
openssl req -x509 -nodes -newkey rsa:4096 -keyout privkey.pem -out fullchain.pem -days 365 -subj "/CN=localhost"
```

Note: To make your browser trust the self-signed certificate, you may need to add certs/fullchain.pem to your system's trusted certificates.

Step2 : To build images and start services in detached mode, run:

    docker compose up --build -d

- This command builds and starts services collabchat with Nginx server as api gateway handling network traffic, routing and SSL termination.
- `docker compose logs -f` for logs and `docker compose down` to stop and remove containers and networks.
- Test https connection using https://localhost/hello
- All apis on http still accessible on 8080 port
- All same apis also accessible via  https://localhost/hello vai Nginx gateway (port 443 for https)


Note: For production, use valid SSL certificates from a trusted CA instead of self-signed ones and should not be committed in repository.