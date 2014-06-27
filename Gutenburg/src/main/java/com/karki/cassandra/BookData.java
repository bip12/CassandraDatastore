package com.karki.cassandra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.datastax.driver.core.Session;

public class BookData {
	
	private Ebook bk = null;
	private Text txt = null;
	
	public void readFile(Session s){
		Session sn = s;
		String title = null,author = null, releaseDate = null, lang = null, textId = null; 
		SimpleClient client = new SimpleClient();
		final String path ="C://Project2Data/bookIndex.txt";
		File file = new File(path);
		String filename = file.getName();
		System.out.println("====================================================================================");
		System.out.println("Ebook # =" + filename);
		
		try{
			BufferedReader book = new BufferedReader(new FileReader(file));
			String row = book.readLine(); 
			String[] temp = row.split(";");
			System.out.println(temp.length);
		
			while (row != null){
				String[] cell = row.split(";");
				
					String fileName = cell[0];
					String bookLink = "C://Project2Data/EBooks/"+ fileName;
					//System.out.println("Book ID = "+ fileName);
								 
					title = cell[1];
					//System.out.println("Title = "+ title);
									 	
					author = cell[2];
					//System.out.println("author = "+ author);
					
					releaseDate = cell[3];
					//System.out.println("released date = "+ releaseDate);
				
					textId = cell[4];
					//System.out.println("TextID = "+ textId);
													 	
					lang = cell[5];
					//System.out.println("language = "+ lang);
					
					bk = new Ebook(textId, title, author, releaseDate, lang);
					client.loadBookInfo(bk, sn);
					
				 	txt = new Text (textId, bookLink);
				 	client.loadBooks(txt, sn);
					
				row = book.readLine(); // Read next line of data.
			}
			book.close();
		}
			catch (IOException ex){
				System.out.println("file error");
			}
	}//read
}
