# Spring AI & Milvus Vector Database 

## References:

- [Spring AI.](https://docs.spring.io/spring-ai/reference/api/vectordbs/milvus.html)
- [Milvus Documentation.](https://milvus.io/)
- [Vector Store.](https://zilliz.com/blog/spring-ai-and-milvus-using-milvus-as-spring-ai-vector-store)

## Pre requirements

- [OpenJDK 21](https://adoptium.net/es/temurin/releases/)
- [Gradle 8.10](https://gradle.org/releases/)


## How to run

### Start Milvus Vector Database server

Execute the following docker-compose to start Milvus Server.

```sh
cd docker_compose
docker-compose up -d
cd ..
```

### Get Open AI Key

Follow the next [guide](https://www.splendidfinancing.com/blog/how-to-get-an-openai-api-key-for-chatgpt) to get and setup the Open AI Key.

### Setup the Open AI Key in env.property

Following the next step to create and setup the Open AI Key environment variable.

1. In the root of the project; create a file "env.properties".
1. Add the following content in "env.properties" file.

```properties
OPEN_IA_SECRET_KEY=[Open AI Key]
```

### Build and Test

```sh
# For Linux or Mac OS
./gradlew clean build

# For Windows
gradlew clean build
```

### Run application

```sh
# For Linux or Mac OS
./gradlew bootRun

# For Windows
gradlew bootRun
```

### Access to endpoints

The application start on the URL: http://localhost:8080

| Description   | Method | URI         | Parameters                                                       |
|---------------|--------|-------------|------------------------------------------------------------------|
| Search        | Get    | /document   | query=[to search] & topK=[Num max result]                        | 
| Add Documents | Post   | /document   | [ { "content": "[content]", "metadata": { "key1": "value1" } } ] |
