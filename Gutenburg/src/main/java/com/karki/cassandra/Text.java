package com.karki.cassandra;

public class Text {
	
	private String book_id;
	private String book_text;
	
	 public Text(){
	    	
	    }
	 public Text(String id, String bt){
	    	this.book_id = id;
	    	this.book_text = bt;
	    }   
	    
	 public String getBookId() {
	        return book_id;
	    }
	 public void setBookId(String bookId) {
	        this.book_id = bookId;
	    }
	 public String getBookText() {
        return book_text;
	 }
	 public void setBookText(String bText) {
        this.book_text = bText;
	 }

}
