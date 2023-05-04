# Subscribe CloudEvents in Kafka

`order-service` is a microservice to demonstrate:
- How to use a publish-subscribe pattern using CloudEvents in Kafka. The service listen for messages using the [CloudEvents](https://cloudevents.io/) specification.

## Pre-requisites

* Java JDK 17 (or greater):
  * [Microsoft JDK 17](https://docs.microsoft.com/en-us/java/openjdk/download#openjdk-17)
  * [Oracle JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
  * [OpenJDK 17](https://jdk.java.net/17/)
* [Apache Maven](https://maven.apache.org/install.html) version 3.x.


### Run Java service

1. Open a new terminal window and navigate to `order-service` directory:

```bash
cd ./order-service
```

2. Run the Java service app: 

```bash
java -jar target/order-service-1.0.0-SNAPSHOT.jar
```
