package guten;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Guten{
	
	public void listFiles(String directoryName){
        File directory = new File(directoryName);
        File[] fList = directory.listFiles(); 
        for (int i = 0; i < fList.length; i++) 
        {
        	 this.readBook(fList[i]);  //pass all the files 
        }	       
	}

	public void readBook(File fList){
		String fileNum = null, title = null,author = null, releaseDate = null, lang = null, aName = null, textId = null, translator = null; 
		File file = fList;
		String filename = file.getName();
		int i = filename.indexOf(".");
		fileNum = filename.substring(0, i);
		System.out.println("====================================================================================");
		System.out.println("Ebook # =" + fileNum);
		try{
		BufferedReader book = new BufferedReader(new FileReader(file));
		String row = book.readLine(); 
			while (row != null){
				
				if (row.startsWith("Title:") && row.length() != 6){
					title = row.substring(7);
					System.out.println("Book Title = "+ title);
				}
				if (row.startsWith("Author:") && row.length() != 7){
					author = row.substring(8);
					System.out.println("Author name = "+ author);
				}
				if (row.startsWith("Release Date:") && !row.contains("[Etext #") && !row.contains("[EBook #")){
					releaseDate = row.substring(14);
					System.out.println("Released Date = "+ releaseDate);
				}
				if (row.startsWith("Release Date:") && (row.contains("[Etext #") || row.contains("[EBook #")) && row.contains("]") && (row.indexOf(":") <= row.indexOf("[")-2
						&& row.indexOf("]") == row.length()-1)){
					int idx = row.indexOf("#");
					int t = row.indexOf("]");
					releaseDate = row.substring(14, row.indexOf("["));
					System.out.println("Released Date = "+ releaseDate);
					textId = row.substring(idx+1,t);
					System.out.println("TextID = "+ textId);
				}
				if (!row.startsWith("Release Date:") && (row.contains("[Etext #") || row.contains("[EBook #")) && row.contains("]") && (row.indexOf(":") <= row.indexOf("[")-2
						&& row.indexOf("]") == row.length()-1)){
					int idx = row.indexOf("#");
					int t = row.indexOf("]");
					textId = row.substring(idx+1,t);
					System.out.println("TextID = "+ textId);
				}
				if (row.startsWith("Release Date:") && row.contains("[Etext #") && !row.contains("]") && (row.indexOf(":") <= row.indexOf("[")-2)){
					int idx = row.indexOf("#");
					int t = row.length()-1;
					releaseDate = row.substring(14, row.indexOf("["));
					System.out.println("Released Date = "+ releaseDate);
					textId = row.substring(idx+1,t);
					System.out.println("TextID = "+ textId);
				}
				if (row.startsWith("Language:")&& row.length() != 9){
					lang = row.substring(10);
					System.out.println("Language = "+ lang);
				}	
				if (row.contains("donations.")){
					book.readLine(); // skip two line because next line is empty
					book.readLine();
					title = book.readLine();
					System.out.println("Book Title = "+ title);
					book.readLine(); // skip another line
					aName = book.readLine();
				}
				row = book.readLine(); // Read next line of data.
		}
			if (aName != null && aName.startsWith("by ")){
				author = aName.substring(3);
				System.out.println("Author name = "+ author);
			}
			if (aName != null && aName.contains("Translanted by")){
				translator = aName.substring(15);
				System.out.println("Translated by = "+ translator);
			}
		String data = fileNum +" ; "+ title +" ; " + author +" ; "+ releaseDate +" ; "+ textId +" ; "+ lang +" ; "+ translator+"\n";
		System.out.println (data);
		this.writeBookIndex(data);
		book.close();
	}
    catch (IOException ex){
		System.out.println("file error");
		}
	}//read
	
public void writeBookIndex(String s){
	String path = "C://Project2Data/bookIndex.txt";	
	String data = s;
		try{
			File file = new File(path);
			//String data = "hello world\n";
			FileWriter fileWriter = new FileWriter(file,true);
    		if(!file.exists()){ //if file doesnt exists, then create it
    			file.createNewFile();
    		}
    		BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
    		fileWriter.append(data);
    		bufferFileWriter.close();
	        //System.out.println("Done");
    	}catch(IOException e){
    		e.printStackTrace();
    	}
	}//writeBookIndex()
	public void listEbook(){
		 
		Gutenburg listAllFiles = new Gutenburg(); 
        //Windows directory 
        final String directoryWindows ="C://Project2Data/EBooks/";
 
        listAllFiles.listFiles(directoryWindows);
    }
	public static void main (String[] args){
			Gutenburg test = new Gutenburg();
			test.listEbook();
		}	
	
}
