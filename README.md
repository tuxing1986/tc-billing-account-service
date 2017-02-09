# TOPCODER - IMPLEMENT BILLING ACCOUNT SERVICE

## Prerequesits

- Java 8 with Maven 3
- Docker

# Docker Image

To run Informix locally go to the 'local' folder and execute the following command:

```
docker-compose up
```

If you should ever encounter the error:

```
[java] Caused by: com.topcoder.db.connectionfactory.DBConnectionException: error occurs while creating the connection.
You already have 20 connections at this
time. Any further connections
```

Try to restart the informix database server as below:
```
# log into the tc-informix docker container first
docker exec -it tc-informix bash

# restart the database server
onmode -ky
oninit
```

## Configuration

The configuration is done via environment variables, these can be set in 'local/run.sh'.

|Name	           | Description                     |
|------------------|:-------------------------------:|
|IP                | The IP of your docker container |
|OLTP_PW           | The Informix password           |
|OLTP_USER         | The Informix user               |
|TCS_CATALOG_URL   | The Informix URL                |
|TC_JWT_KEY        | The JWT key                     |

## Start Microservice

In the 'service' folder of this project run:

```
mvn clean package
```

Then start the service using the following command in the 'local' folder:

```
./run.sh
```

You may use git bash under windows to run shell script.

## Verification

If the maven build process was successful the unit tests ran without error.

To verify the 'me/billingAccounts' endpoint the database needs to be updated.
Please execute the following statements after you connected to the Informix database with URL TCS_CATALOG_URL (same as in configuration):

```SQL
INSERT INTO tc_direct_project (project_id, name, user_id, create_date) VALUES (1, 'test', 1, current);
INSERT INTO direct_project_account VALUES (1,1,1);
INSERT INTO user_permission_grant VALUES (1, 1, 1, 1, 0);

INSERT INTO time_oltp:payment_terms VALUES (1, '30 Days', current, 'System', current, 'System', 1, 30);
INSERT INTO time_oltp:payment_terms VALUES (2, '45 Days', current, 'System', current, 'System', 1, 45);
INSERT INTO time_oltp:payment_terms VALUES (3, '60 Days', current, 'System', current, 'System', 1, 60);
INSERT INTO time_oltp:payment_terms VALUES (4, '90 Days', current, 'System', current, 'System', 1, 90);
INSERT INTO time_oltp:payment_terms VALUES (5, '120 Days', current, 'System', current, 'System', 1, 120);
INSERT INTO time_oltp:payment_terms VALUES (6, 'Net Zero', current, 'System', current, 'System', 1, 0);
```

Load the postman collection in the 'doc' folder and execute the provided requests to verify the REST API.
