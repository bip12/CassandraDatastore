package com.karki.cassandra;

import com.datastax.driver.core.Session;

public class Main {
	
	public static void main(String[] args) {
		long st = System.currentTimeMillis();
		SimpleClient c = new SimpleClient();
		c.connect("127.0.0.1");
		Session sesn = c.getSession();
		
	//******************************************
		BookData test = new BookData();
		test.readFile(sesn); //turn off during query run
	//******************************************
		//c.queryBooks(sesn); //turn off during test.readfile run
		//c.queryBookrepo(sesn);
	//******************************************
		c.close(); 
		long et = System.currentTimeMillis();
		System.out.println("Elapsed time : "+ (et-st)+ " msec");
		}
}
