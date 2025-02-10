Documentation url = https://hallow.onrender.com/hallow/swagger-ui/index.html

Building docker imgae
- mvn clean package
- docker build -t hallow:latest .
- docker run -p 8080:8080 --name hallow-container hallow
- docker ps
- docker tag hallow:latest your-dockerhub-username/hallow:latest
- docker push your-dockerhub-username/hallow:latest

