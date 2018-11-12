# couchinator-java-wrapper

![](https://travis-ci.org/cdimascio/couchinator-java-wrapper.svg?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/67edd40fa18644d3b86b36d7f44ea258)](https://www.codacy.com/app/cdimascio/couchinator-java-wrapper?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cdimascio/couchinator-java-wrapper&amp;utm_campaign=Badge_Grade)
![](https://img.shields.io/badge/license-MIT-blue.svg)

Fixtures for [CouchDB](http://couchdb.apache.org/) and [IBM Cloudant](https://www.ibm.com/cloud/cloudant).

_Setup_ and _teardown_ cloudant databases with ease. Couchinator is a great tool for unit testing and more. Couchinator is both a library and a [command line utility](https://github.com/cdimascio/couchinator).

<p align="center">
	<image src="https://raw.githubusercontent.com/cdimascio/couchinator-java-wrapper/master/assets/couchinator-java-wrapper.png" width="600" />
</p>

The project is a Java wrapper around [couchinator](https://github.com/cdimascio/couchinator), thus it requires Node.js.

## Install

### Prequisites

- [Node.js](https://nodejs.org/) 8.x or greater

### Maven

```xml
<dependency>
    <groupId>io.github.cdimascio</groupId>
    <artifactId>couchinator-java-wrapper</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

```groovy
compile 'io.github.cdimascio:couchinator-java-wrapper:1.0.1'
```

### Import

```java
import io.github.cdimascio.couchinatorw.Couchinator;
```

## Usage

```java
Couchinator couchinator = new Couchinator(url, resourceDir)

// Setup the databases and fixtures defined in your data layout
couchinator.create();

// Teardown, then setup the databases and fixtures defined in your data layout
couchinator.reCreate();

// Teardown the database defined in your data layout
couchinator.destroy();
```

**Note:** Couchinator will only setup and destroy databases defined in your data layout.

### Junit Example

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExampleUnitTest {
    private Couchinator couchinator = new Couchinator(
        "http://localhost:5984",         // couchdb or cloudant url (include usename/password)
        "./src/test/resources/fixtures"  // fixtures resource location
    );

    @BeforeAll
    void beforeAll() throws CouchinatorException{
    	// setup the dbs defined in your data layout
        couchinator.create();
    }

    @Test
    public void doStuff() {
        assertNotNull("test stuff");
    }

    @AfterAll
    void afterAll() throws CouchinatorException{
        // teardown the dbs defined in your data layout
        couchinator.destroy();
    }
}
```

## Data Layout

The following sections describe how to create a data layout.

To skip directly to a working example, go [here](https://github.com/cdimascio/couchinator/blob/master/examples/db-resources)

### Getting Started

Couchinator enables you to represent CouchDB and Cloudant database(s) using a simple filesystem structure that mimics the actual database structure.

A couchinator filesystem data layout might look as such:


```shell
users
    _design
        students.json
	 teachers.json
    students-docs.json
    teachers-docs.json
classrooms
    _design
        classrooms.json
        classrooms-docs.json
```

### Create a data layout representing 2 databases

Let's create a data layout to describe two databases **users** and **classrooms**

1. **Create two folders, one for `users` and another for `classrooms`.**

	```shell
	users/
	classrooms/
	```
	**Note:** Couchinator will use the folder name as the database name

2. **Within each folder _optionally_ create a `_design` folder to store any design documents**

	```shell
	users/
	    _design/
	classrooms/
	    _design/
	```

3. **Create design document(s) and store them in the appropriate `_design` folder**

   In the example below, we create two design documents in the `schools` database and one in the `users` database.

	```shell
	users/
	    _design/
	        students.json
	        teachers.json
	classrooms/
	    _design/
	        classrooms.json
	```

	The contents of each design document `.json` must be a valid CouchDB [design document]([design document](http://docs.couchdb.org/en/2.0.0/json-structure.html#design-document)).
	
	For example, `students.json`:
	
   ```json
   {
     "_id": "_design/students",
     "views": {
       "byId": {
          "map": "function (doc) {  
	      if (doc.type === 'student') {
	          emit(doc._id, doc);
	      }
          }"
       }
     },
     "language": "javascript"
   }
   ```

4. **Create the data to store in each database**

	- Data must be stored using CouchDB's [bulk document](http://docs.couchdb.org/en/2.0.0/json-structure.html#bulk-documents) format
	- The data may be stored in a _single_ JSON file or spread across _multiple_ JSON files (useful for organizing data)

	```shell
	users/
	    _design/
	        students.json
	        teachers.json
	    students-docs.json   # contains student data
	    teachers-docs.json   # contains teacher data
	
	classrooms/
	    _design/
	        classrooms.json
	    users-docs.json
	```
	

   For example, `student-docs.json` contains students

   ```json
   {
     "docs": [
       {
         "_id": "sam895454857",
         "name": "Sam C.",
         "type": "student"
       },
       {
         "_id": "josie895454856",
         "name": "Josie D.",
         "type": "student"
       }
     ]
   }
   ```

5. **Run couchinator to create each database**

See [Junit example](#junit-example)

## Integrating with Travis

couchinator-java-wrapper wraps [couchinator](https://github.com/cdimascio/couchinator), a Node.js based tool, hence to Node.js must be installed in the running environment.

```yaml
language: java
jdk:
- oraclejdk8
- 
before_install:
- nvm install 10 # install node.js

script:
# do stuff
# ...
```	

## License

[Apache 2.0](LICENSE)
