# GSite Manager
Please visit GSite on cloud: https://gsite.cf

### Development
##### Prerequisites:
- Have running MongoDB server on localhost:27017

##### Run app
    
    ./gradlew bootRun

### Testing
##### Test app
 
    ./gradlew test
    
    
### Production
##### Prerequisites:
- Have running MongoDB server on localhost:27017

##### Packaging
    
    ./gradlew -Pprod bootRepackage
##### Run app
    
    ./build/libs/gsite-manager-1.0.war
or

    java -jar build/libs/gsite-manager-1.0.war

### Production with Docker
##### Prerequisites:
- Running Docker Engine (Daemon). To check Docker run:
    
        docker -v
        docker ps
        
##### Build Docker image
      
    ./gradlew bootRepackage -Pprod buildDocker
        
##### Deploy app and mongodb on single Docker engine
Run app and mongodb containers:

    docker-compose -f src/main/docker/app.yml up -d

Check running app by logs until available on port:
    
    docker logs -f {container name} 
example:

    docker logs -f  docker_gsitemanager-app_1
Use postman or open browser port 8082 on host. For localhost:
     
     http://localhost:8082
or 
   
     http://{IP or host}:8082

Test APIs:
    
     http://localhost:8082/api/web-templates
or
     
     http://{IP or host}:8082/api/web-templates
     
Get public manager token from logs:
    
    ./gradlew -Dtest.single=TokenProvider test -i

Use postman or other tools to test APIs with:
- url: 
    
        http://{IP or host}:8082/api/websites
        
- header:
        
        Authorization: Bearer {token}

To stop containers run:

    docker-compose -f src/main/docker/app.yml stop
    
To remove stopped containers run:

    docker-compose -f src/main/docker/app.yml rm -v -f   
    
### Have fun!    
Please visit GSite on cloud: https://gsite.cf
