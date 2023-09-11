#docker commands
sudo systemctl start docker

sudo docker run --name habr-pg-13.3 -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -d postgres:13.3
sudo docker run --rm -p 3000:3000 ghcr.io/bizinmitya/front-react-avito:v1.19
sudo docker ps

#java commands
export JAVA_HOME="/usr/lib/jvm/java-11-openjdk"
mvn clean install -Dmaven.test.skip=true
java -jar target/ads-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
