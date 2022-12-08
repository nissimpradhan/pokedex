# Pokedex Application

A simple application to search for pokemons using the publickly available api from https://pokeapi.co/

## How to run

You will need docker installed in your computer. Download it from here
https://docs.docker.com/get-docker/


Please run the following commands
```
docker build -t example/pokedex .
docker run -p 8080:8080 example/pokedex
```

## Setup for development

You will need the following libs before we start the dev environment
- maven - https://maven.apache.org/install.html
- java sdk 11 - https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html

Then you can start the application with the command below

`mvn spring-boot:run`

Everytime you modify the code, it will be reflected in the running application

## Extra consideration for production API

More robust error handling. Errors on translations are simply ignored, and original description is preserved in case of error, but currently error handling on https://pokeapi.co/ isn't handled cleanly, the default framework error handling takes over.
