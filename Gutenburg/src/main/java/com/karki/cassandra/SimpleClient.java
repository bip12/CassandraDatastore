package com.karki.cassandra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class SimpleClient {
	
	static Logger logger = Logger.getLogger(SimpleClient.class);
	private Session session;
	private Cluster cluster;
	
	public void connect(String node) {
		BasicConfigurator.configure();
	    logger.info("Entering application.");
		cluster = Cluster.builder()
		         .addContactPoint(node)
		         // .withSSL() // Uncomment if using client to node encryption
		         .build();
		   Metadata metadata = cluster.getMetadata();
		   System.out.printf("Connected to cluster: %s\n", 
		         metadata.getClusterName());
		   for ( Host host : metadata.getAllHosts() ) {
		      System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
		         host.getDatacenter(), host.getAddress(), host.getRack());
		   }
		   
		   session = cluster.connect(); //connection session
	}
	
	public Session getSession() {
		   return this.session;
		}
		
	public void loadBookInfo(Ebook info, Session ses){
		
		PreparedStatement statement = null;
		String textId = info.getBookId();
			//System.out.println("text id = "+ textId);
		String bookTitle = info.getTitle();
			//System.out.println("book title = "+ bookTitle);
		String bookAuthor =info.getAuthor();
			//System.out.println("book author = "+ bookAuthor);
		String bookReleasedDate = info.getReleasedDate();
			//System.out.println("Released on = "+ bookReleasedDate);
		String bookLanguage = info.getLanguage();
			//System.out.println("book lang = "+ bookLanguage);
		//connect("127.0.0.1");
		statement = ses.prepare("INSERT INTO bkarki.books(textid, title, author, releasedate, language)" +
				      "VALUES (?, ?, ?, ?, ?);");
		
		BoundStatement boundStatement = new BoundStatement(statement);
		ses.execute(boundStatement.bind(textId, bookTitle,bookAuthor,bookReleasedDate,bookLanguage));
		//close();
	} 
	 public ByteBuffer readFile(String path){
		  
		  FileInputStream fis = null;
		  FileChannel channel = null;
		  File file = new File (path);
		  int size = (int) file.length();
		  try {
		  fis = new FileInputStream(file);
		  channel = fis.getChannel();
		  } catch (FileNotFoundException e1) {
		   e1.printStackTrace();
		  } catch (NullPointerException npe){
		   npe.printStackTrace();
		  }
		          //allocate space for file
		  ByteBuffer buf = ByteBuffer.allocate(size); 
		  
		  try {
		   if (channel.isOpen()){   
			   channel.read(buf);
		   }
		  } catch (IOException e) {  
		   e.printStackTrace();
		  }
		  finally{
			  
		   try{   
		    channel.close();
		    fis.close();
		   }
		   catch(Exception e){  
		    e.printStackTrace();
		   }
		  }
		  System.out.println(":-) Read File worked!!! (-:");
		  return buf;
		 }
	public void loadBooks(Text t, Session ses ) {
		ByteBuffer buf = readFile(t.getBookText());
		String txt = new String(buf.array(), Charset.forName("UTF-8"));
		//System.out.println(string);
	
		PreparedStatement statement = null;
		statement = ses.prepare("INSERT INTO bkarki.bookrepo " +  "(textid, text) " +
			      "VALUES (?, ?);");
		
		BoundStatement boundStatement = new BoundStatement(statement);
		boundStatement.enableTracing();
		boundStatement.setString("textid", t.getBookId());
		boundStatement.setString("text", txt);
		ses.execute(boundStatement);

	}
	

	public void queryBooks(Session ses){
		//connect("127.0.0.1");
		ResultSet resultSet = session.execute("SELECT * FROM bkarki.books;");
		
			for (Row row : resultSet) {
			    System.out.println(String.format("%-8s\t%-72s\t%-40s\t%-16s\t%-8s", row.getString("textid"),row.getString("title"),
			    row.getString("author"),row.getString("releasedate"),row.getString("language")));
			    System.out.println("------------+------------------------------------------------------------------------------+----------------------------------------------+---------------------------+------------|");
			} 
			
			System.out.println();
			//close();	
	}
	
	public void queryBookrepo(Session ses){
		//connect("127.0.0.1");
	  //String q = "363";
		ResultSet resultSet = ses.execute("SELECT * FROM bkarki.bookrepo limit 1"); //where textid= '" + q +"' ;"); // select by book number

		for (Row row : resultSet) {
		    System.out.println(String.format("%-8s\t%-100s", row.getString("textid"),row.getString("text")));
		} 
		
		System.out.println();
	}
		
	public void close() {
		   cluster.shutdown();
		   logger.info("Exiting application.");
		}
}
