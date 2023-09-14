# Skypro graduate work: Backend Ad Server routines Implementation
<u>Backend ad server<u>
<br>
<br>
team work
<br>authors: Stanislav Dudin, Katerina Petrova, Sergei Gots
<br>
Project is the backend part implementation for ad service site.
The site  is represented with frontend part module running as a docker application.

## Project structure

The backend application's architecture includes:
<ul>
  <li><b>Controllers</b> to handle requests getting from frontend</li>
  <li><b>Services</b> implementing data manipulation level</li>
  <li><b>JpaRepositories</b> - schemes that describe Object-Relation Management</li>
  <li><b>Entities</B> of ORM that include <b>Ad</b>s, <b>Comment</b>s to the ads and <b>User</b>s that
  are divided to <b>ADMIN</b>s and <b>USER</b>b>s</li>
    <ul></ul>
  <li><b>DTO</b>-s provide correspondence between backend Entities and frontend JSON data objects.
DTO are described </li>
</ul>

DTO and Response statuses are specified within <b>openapi.yaml<b>.

## Database scheme

The data shceme is provided with liquibase change-log that describe creating the tables <b>users, ads, comments</b>
and inserting some initial data in order to demonstrate application's functionality.
Db-changelog is avaliable under <b>resources/db/changelog</b> 

## Authentification

Authentification is configured with the <b>config.WebSecurityConfig</b> which provides the Application with
the <b>@Bean</b> of a <b>Password Encoder</b> and <b>filterChain</b>-method

## Authorization

According to the specification in <b>openapi.yaml<b> users can have <b>ROLE_USER</b> and <b>ROLE_ADMIN</b>
ADMIN can patch and delete any comment or ad when USER is allowed to patch and delete only their own enties.
When USER try to patch or delete an ad or comment of other person then the status <b>403/Forbidden</b> will be returned to the front.

## How to build and launch

The project is developed as a spring-boot application, intented to be build with maven
so there is a <b>pom.xml</b> in the root directory.

to build the jar use the next command:


to run the projectd first you need to run docker container of the frontend part:


The app uses the postgresql database. The data to access the database can be cofigured with the
<b>application.properties</b>-file or specified in cmd-line:

The db-server itself can be run as a docker container too:



<u>Command to launch<u>:

mvn package 

java -jar target/pet-shelter-bot-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod --spring.datasource.username=<DB_USERNAME> --spring.datasource.password=<DB_PASSWORD> --telegram.bot.token=<YOUR_BOT_TELEGRAM_TOKEN>
<br><br>
 where <br>
    <li>DB_USERNAME, DB_PASSWORD -  username/password to your Postgres DB Server
    <li>YOUR_TELEGRAM_BOT_TOKEN - a token you've got with Telegram @BotFather bot 
<br><br>
After a successfull launch you can interact with the bot in Telegram.
<br>There are two different roles/scenarios for users.
Users can have role either of a <b>Vounteers</b> or an <b>Adopter</b>
<br>If the user is listed in the database as a shelter's volunteer 
then they have within the bot a notification desk about requests for dialogs 
and can participate dialogs answering questions asked by adopters.
<br>Both adopters and volunteers are able to terminate their dialog session.
<br>
<br>Also there is API provided for shelter's employees who
can manage list of volunteeers, asign adopters for pets,
review reports from adopters about their pets etc.
<br>To interact with API there is swagger-UI provided available on:
<br>
<br> http://localhost:8080/shelter/swagger-ui/index.html#/
