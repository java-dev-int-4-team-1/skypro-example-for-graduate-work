sudo systemctl start docker
sudo docker run --rm -p 3000:3000 ghcr.io/bizinmitya/front-react-avito:v1.19
sudo docker ps

export JAVA_HOME="/usr/lib/jvm/java-11-openjdk"

mvn clean install -Dmaven.test.skip=true
