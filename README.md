# couchinator-java-wrapper

![](https://travis-ci.org/cdimascio/couchinator-java-wrapper.svg?branch=master)

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
    	"http://localhost:5984", // couchdb or cloudant url (include usename/password)
	"./src/test/resources/fixtures" // fixtures resource location
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

### Getting Started

Couchinator enables you to represent your db as a simple directory structure residing on the filesystem. **couchinator** then reads this representation to create, update, and destroy your database(s) on demand.

Here is an example directory structure:
Note: The `_design` folder contains your design documents

```
  shools
    _design
    	students.json
		teachers.json
	students-docs.json
	teachers-docs.json
	users
    _design
    	classrooms.json
	classrooms-docs.json
```

### The Details

1. Create a folder (`RESOURCE_PATH`) to contain your database representation.
2. Within this folder, create a folder for each database. We will refer to each of these as a `db_folder`

   Each `db_folder` name is used as the database name.

3. Within each `db_folder`, _optionally_ create a `_design` folder.

   Within each `_design` folder, create zero or more `.json` files. Each `.json` file _must_ contain a _single_ [design document](http://docs.couchdb.org/en/2.0.0/json-structure.html#design-document).

   For example, `users/_design/students.json`

   ```
   {
     "_id": "_design/students",
     "views": {
       "byId": {
         "map": "function (doc) {  if (doc.type === 'student') emit(doc._id, doc);}"
       }
     },
     "language": "javascript"
   }
   ```

4. Within each `db_folder`, _optionally_ create zero or more `.json` files. Each `.json` file should contain the documents to be added at creation time. They must be specified using CouchDB [bulk document](http://docs.couchdb.org/en/2.0.0/json-structure.html#bulk-documents) format.

   For example, `users/students-docs.json

   ```
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

   **Note** that Documents may reside in a single `.json file` or may span multiple `.json` files for readability. **couchinator** aggregrates documents spanning multiple files into a single bulk request.

## Example

See examples/db-resources.

To run the the example:

- clone this repo
- `cd examples`
- edit examples.js and set `<CLOUDANT-URL>` to your cloudant url
- Run `node example`
- Your database should now contain documents

### Design doc representation

Its a standard cloudant design doc.
e.g. `designdoc1-1.json` above

```json
{
  "_id": "_design/districts",
  "views": {
    "byId": {
      "map": "function (doc) {  if (doc.type === 'district') emit(doc._id, doc);}"
    }
  },
  "language": "javascript"
}
```

### Doc representation

It's a standard bulk doc e.g. `bulkdocs1-1.json` above

```json
{
  "docs": [
    {
      "name": "Sammy",
      "type": "person"
    },
    {
      "name": "June",
      "type": "person"
    }
  ]
}
```

## License

[MIT](LICENSE)
