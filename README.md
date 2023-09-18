# Skypro graduate work: Back-end Ad Server routines Implementation
<br>
Team work.
<br>Authors: Stanislav Dudin, Katerina Petrova, Sergei Gots
<br><br>
The Project is the backend part implementation for an ad service web application.
The site, or front-end part is represented with the ready-to-use frontend part module running as a docker application.
The interaction protocol between frontend and backend is specified with the <b>openapi.yaml</b>.  

## Project structure

The back-end application's architecture includes:
<ul>
  <li><b>Controllers</b> to handle requests getting from frontend</li>
  <li><b>Services</b> implementing data manipulation level</li>
  <li><b>JpaRepositories</b> - schemes that describe Object-Relation Management</li>
  <li><b>Entities</B> of ORM that include <b>Ad</b>s, <b>Comment</b>s to the ads and <b>User</b>s that
  are divided to <b>ADMIN</b>s and <b>USER</b>s</li>
    <ul></ul>
  <li><b>DTO</b>-s provide correspondence between backend Entities and frontend JSON data objects. </li>
</ul>

DTO and Responses statuses are specified within <b>openapi.yaml</b>.

## Database scheme

The data scheme is provided with the liquibase change-log that describes creating the tables <b>users, ads, comments</b>
and inserting some initial data in order to demonstrate application's functionality.
Db-changelog is available under <b>resources/db/changelog</b> 

## Authentication

Authentication is configured with the <b>config.WebSecurityConfig</b> which provides the Application with
the <b>@Bean</b> of a <b>Password Encoder</b> and <b>filterChain</b>-method.<br>
A not authenticated user can view existing ads.
An authenticated user can create ads and write comments and edit and delete created ads and comments.
An authenticated user has their avatar image, ads also supposed to have their images.


## Authorization

According to the specification in <b>openapi.yaml</b> users can have <b>ROLE_USER</b> and <b>ROLE_ADMIN</b>.
ADMIN can patch and delete any comment or ad when USER is allowed to patch and delete only their own entries.
If a USER tries to patch or delete an ad or comment of other person then the status <b>403/Forbidden</b> will be returned to the front.

## How to build and launch

The project is developed as a spring-boot application, intended to be built with <b>maven</b>
There is a <b>pom.xml</b> in the root directory. Below there are the steps describing how to build and run the application.

1) build the jar-file using the next command:

<code>mvn clean install package [-DskipTests]</code>

2) be sure your postgresql server is working. In case when there isn't a postgresql database server installed there is an option to launch it within a docker container, e.g.:

<code>docker run -name habr-pg-13.3 -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -d postgres:13.3</code>
<br>In this example there are username, password and name of the database specified as 'postgres' for each of them.
<br><br>

3) run the app:
    
<code>java -jar target/ads-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/postgres --spring.datasource.username=postgres  --spring.datasource.password=postgres</code> 
    
4) There is an option to check functionality with the http-requests which are placed in the files in the <b>http-requests</b> directory.
   
5) Run the front end within docker container using the command:
   
<code>docker run --rm -p 3000:000 ghcr.io/bizinmitya/front-react-avito:v1.19</code>

7) Use in browser the next address to access the app:

<code>http://localhost:3000/</code>
   
