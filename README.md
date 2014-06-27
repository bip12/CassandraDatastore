CassandraDatastore
==================

Basic distributed data storage systems using Cassandra, CQL3, and Java.

CASSANDRA
----------
1.	Install Cassandra, cqlsh (python CQL interface program), and Datastax Cql3 driver.
2.	Save Ebook text files and bookIndex.txt file in a folder.
3.	Import the project file Gutenburg in the Eclipse.
4.	Build a jar files path environments by running pom.xml using Maven.
5.	On cqlsh interface, execute CQL command CREATE KEYSPACE bkarki with 

		REPLICATION ={'class':'SimpleStrategy','replication_factor':3};

6.	On cqlsh, execute USE bkarki;
7.	On cqlsh, execute, CREATE TABLE books(
				textid text,
				title text,
				author text,
				releasedate text,
				language text,
			   	PRIMARY KEY(textid));

8.	On cqlsh, execute, CREATE TABLE bookrepo(textid text,text text,PRIMARY KEY(textid));
9.	Run the main method in Gutenburg Java project.
