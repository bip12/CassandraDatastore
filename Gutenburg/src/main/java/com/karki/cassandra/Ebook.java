package com.karki.cassandra;

public class Ebook {
	private String book_id;
    private String title;
    private String author;
    private String released_date;
    private String language;
    
    public Ebook(){
    	
    }
    public Ebook(String id, String t, String a, String rd, String l){
    	this.book_id = id;
    	this.title = t;
    	this.author = a;
    	this.released_date = rd;
    	this.language = l;
    }
    
    public String getBookId() {
        return book_id;
    }
    public void setBookId(String bookId) {
        this.book_id = bookId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String bookTitle) {
        this.title = bookTitle;
    }
    
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String bookAuthor) {
        this.author = bookAuthor;
    }
    
    public String getReleasedDate() {
        return released_date;
    }
    public void setReleasedDate(String bookReleasedDate) {
        this.released_date = bookReleasedDate;
    }
    
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String bookLanguage) {
        this.language = bookLanguage;
    }

}
