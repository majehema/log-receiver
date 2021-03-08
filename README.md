# Protofuf case Study

## Description

You are tasked with building 2 applications: one that receives events and one that stores the events. These 2 applications must communicate with each other via Google Protocol Buffers. The first application receives logs of events via HTTP and must pass them to the second application for storing them in the database (for the case study we will not use a database, logging them to a file on the disk will suffice).

## Solution

- The log receiving application is created in Java and connects via sockets to the saving application created in python. 
- The receiving app will automatically run the receiving app.


## Set up

Open a terminal on the project folder.

Use maven to run the service:
```
$ mvn clean install
```

Go to target dir and run the .jar file: 
```
$ java -jar log-receiver-0.0.1-SNAPSHOT.jar
```

The service will run on the port 8090.


## How to use the service?

POST request example:
```
{
    "timestamp" : 1518609008,
    "userId" : 1123,
    "event" : "2 hours of downtime occurred due to the release of version 1.0.5 of the system"
}
```

To test an example in the app you can go to 
http://localhost:8090/swagger-ui.html
log-controller -> POST

### Note:

The data will be saved in the file ./src/main/resources/logReport.txt
