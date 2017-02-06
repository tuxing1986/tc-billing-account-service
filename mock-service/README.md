# Billing Account Service

## Dependencies
- runs on node.js (min) v5.11.1 / node.js v6
- [MongoDB v3+](https://www.mongodb.com/)
- [express 4+](http://expressjs.com/)
- [eslint][http://eslint.org/]
- postman chrome extension for verification https://www.getpostman.com

## Configuration
- Edit configuration in `config.js`

| Variable       | Description                                |
| :------------- | :----------------------------------------- |
| PORT           | The port that the app will listen          |
| MONGODB_URI    | The Mongo database URI                     |
| AUTH_SECRET    | The secret used to encrypt/decrypt the JWT |
| LOG_LEVEL      | The log level `debug` or `info`            |
| VERSION        | The API version                            |

## Local Deployment

- for development it is very handy to have buildpack deployed to your local machine.
- Please make sure to configure url of database rightly in `config.js`.
  - current default `mongodb://localhost:27017/billing-account`
- Start the Mongo service `mongod`
- Install dependencies `npm i`
- run lint check `npm run lint`
- Start app `npm start`

## Heroku deployment

- You will need to install [Heroku Toolbelt](https://toolbelt.heroku.com/) for this step if you don't already have it installed.

- In the main project folder, run the following commands:

        heroku login
        git init
        git add .
        git commit -m "init"
        heroku create
        heroku addons:create mongolab:sandbox
        git push heroku master
        heroku logs -t
        heroku open

## Setup postman
- Load postman collection:

  - endpoints: Billing Accounts.postman_collection.json
  - environment: billing-accounts.postman_environment.json

- postman environment variables:

  - `URL` the base API url  for local testing use `http://localhost:3000` or heroku app url
  - `ADMIN_TOKEN` sample admin role token
  - `USER_TOKEN` sample user role token

## Authentication & Authorization

- Currently there is no endpoint to authenticate a user so I generated to valid tokens using [jwt.io](https://jwt.io/)
  - USER_TOKEN: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiU2FtcGxlIFVzZXIiLCJpZCI6MiwiZGVzY3JpcHRpb24iOiJzYW1wbGUgZGVzY3JpcHRpb24iLCJyb2xlcyI6WyJ1c2VyIl19.YWVxCwjQK290fvNc0kUOg053hLm97LKJrK2IBZeWHpE
  - ADMIN_TOKEN: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiU2FtcGxlIEFkbWluIiwiaWQiOjEsImRlc2NyaXB0aW9uIjoic2FtcGxlIGRlc2NyaXB0aW9uIiwicm9sZXMiOlsiYWRtaW4iXX0.OqMzjYXiIM9G_SDBP0cdLBr4GPnLU8Ilnp4Mdzd44AQ

- Each token represents an object with the following format:
        {
          "name": "Sample Admin",
          "id": 1,
          "description": "sample description",
          "roles": [ "admin" ]
        }

- Those tokens are already added in the postman_collection so you won't have to copy them.

## Demo video

- See the demo video [here](https://youtu.be/35fqqO5gavA)
