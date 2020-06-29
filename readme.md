
# Liquibase Example

## About

Liquibase is an open-source database-independent library for tracking, managing and applying database schema changes. Liquibase uses changesets to represent a single change to a database. Each changeset has an “id” and “author” attribute which, along with the directory and file name of the changelog file, uniquely identify it.

## Support

There are a substantial about of questions regarding the library on [StackOverflow](https://stackoverflow.com/questions/tagged/liquibase)

The [docs](https://docs.liquibase.com/home.html?_ga=2.220870747.275713281.1593008508-2074212102.1593008508) contain good information for getting started, and full extensive details for all functions within the library

If you notice a bug, you can [open an issue here](https://github.com/liquibase/liquibase/issues)

## Set up

For adding liquibase to a project, a maven plugin with a dependency is required:

		<plugin>  
		    <groupId>org.liquibase</groupId>  
		    <artifactId>liquibase-maven-plugin</artifactId>  
		    <version>3.8.0</version>  
		    <configuration>  
		        <propertyFile>liquibase.properties</propertyFile>  
		    </configuration>  
		    <dependencies>  
		      <dependency>  
		          <groupId>postgresql</groupId>  
		          <artifactId>postgresql</artifactId>  
		          <version>9.1-901-1.jdbc4</version>  
		      </dependency>  
		  </dependencies>  
		</plugin>  
		
You'll also need the JDBC for the respective database management system, in this case Postgres. For example purposes, I've included the Postgres JDBC in the resources path.

Within the resources path, there is also a `liquibase.properties` file which contains information which will build the db connection string, credentials etc.

Get the liquibase installer from [here]([https://www.liquibase.org/download](https://www.liquibase.org/download))

## Getting started

Liquibase uses a change set structure to represent a single change to the database. Each change set has an author and id which can be used to uniquely identify each change set. In this example, there are two change sets. 

#### Creating the first change set

Change sets can be written in various mark up languages but for purposes of this example, we will use YAML. A change log is defined by `databaseChangeLog` tag, with children of type `changeSet`.

	databaseChangeLog:  
	- changeSet:  
	    id: '12598'  
	  author: Jordan  
	    objectQuotingStrategy: LEGACY  
	    changes:  
	    - sql:  
	          dbms:  'postgresql'  
	  endDelimiter: ';;'  
	  splitStatements:  true  
	          sql:  'create schema security'  
	  stripComments:  true  
	    - createTable:  
	        columns:  
	        - column:  
	            autoIncrement: true  
	            constraints:  
	              nullable: false  
	              primaryKey: true  
	              primaryKeyName: pk_security_role  
	            name: id  
	            type: INTEGER  
	        - column:  
	            constraints:  
	              nullable: false  
	            name: name  
	            type: VARCHAR  
	        tableName: role  
	        schemaName: security

Above is an excerpt of the change log in the example provided. First, we are running a custom sql script to create a new schema named `security` (as liquibase does not have native functionality for this). After this, we are then creating a new table, with 2 columns, one of which being a primary key. We give this table a name - `role`, and the schema which it belongs to - `security`.

#### Running the migration

There are two main ways of updating the database, either directly from the java code or from the command line. For demonstration purposes, I will use the command line. After running the liquibase installer, liquibase should now be part of your system path, and the command `liquibase` should be recognised in the command line. Ensure that within `liquibase.properties` that the path to the change log is defined and is correct. To run the database update, cd into the directory `src/main/resources` in this project and execute the command:

	liquibase update
	
This will then run the update for the database for all change sets within the change log file. 
