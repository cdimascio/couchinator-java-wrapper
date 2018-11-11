# couchinator-java-wrapper

Fixtures for [CouchDB](http://couchdb.apache.org/) and [IBM Cloudant](https://www.ibm.com/cloud/cloudant).

Setup ad teardown cloudant databases with ease. couchinator is a great tool for unit testing and more. couchinator is both a library and a command line utility.

<p align="center">
	<image src="https://raw.githubusercontent.com/cdimascio/couchinator-java-wrapper/master/assets/couchinator-java-wrapper.png"/>
</p>

## Install



## Prequisites
- Node.js 8.x or greater


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