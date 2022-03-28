# Book Catalouge
This is a spring boot application where you can add, update, delete and search books.
## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)


## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.catalouge.book.bookcatalouge.BookCatalougeApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Sample cURLs for the apis

### Add Book

```
curl --location --request POST 'http://localhost:8080/api/v1/book/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title":"Harry Potter and the Philosopher’s Stone",
    "isbn":"1234567890122",
    "publicationDate" : "2019-04-28",
    "authors" : [ 
        {"name": "J.K. Rowling"}
    ]
}
```
### Update Book
```
curl --location --request PUT 'http://localhost:8080/api/v1/book/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title":"Harry Potter",
    "isbn":"1234567890122",
    "publicationDate" : "2019-04-28",
    "authors" : [ 
        {"name": "J.K. Rowling"}
    ]
}'
```
### Delete Book
```
curl --location --request DELETE 'http://localhost:8080/api/v1/book/1'
```
### Search Book
```
curl --location --request GET 'http://localhost:8080/api/v1/books?search=ISBN:1234567890122 AND id:1'
```