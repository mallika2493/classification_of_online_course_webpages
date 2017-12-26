package online.courses.gui;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.relique.jdbc.csv.CsvDriver;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author meghana
 */
public class Csv_driver {
    String labels[];
    String info[][];
    String query;
    
    public Csv_driver(String query)
    {
        this.query = query;
    }
 /*   public static void main(String args[])throws Exception
    {
        
        
    }*/
    public int executeQuery(String query) throws Exception
  {
   
	  String CSV_PATH="C:/Users/Dell/Documents/NetBeansProjects/Online Courses GUI";
	  //String CSV_NAME="RedhoopTRAINCSV.csv";
    	// Load the driver.
      Class.forName("org.relique.jdbc.csv.CsvDriver");
 
      // Create a connection. The first command line parameter is
      // the directory containing the .csv files.
      // A single connection is thread-safe for use by several threads.
      Connection conn;
	try {
		conn = DriverManager.getConnection("jdbc:relique:csv:"+CSV_PATH);
		System.out.println(conn.toString());
	
      // Create a Statement object to execute the query with.
      // A Statement is not thread-safe.
      Statement stmt = conn.createStatement();
      System.out.println(stmt.toString());
      
      
       // Select the ID and NAME columns from sample.csv
            System.out.println(query);
            
      ResultSet results = stmt.executeQuery(query);
      int k = 0;
   //   System.out.println("Results:" + results.getFetchSize());
      
      info = new String[11000][9];
      labels = new String[11000];
      while(results.next())
      {
    	  
    	 info[k][0] = results.getString("COURSENAME");
          System.out.println(info[k][0]);
         info[k][1] = results.getString("PROVIDER");
          info[k][2] = results.getString("PRICE");
          info[k][3] = results.getString("DURATION");
          info[k][4] = results.getString("INSTRUCTOR");
          info[k][5] = results.getString("LEVEL");
          info[k][6] = results.getString("URL");
          info[k][7] = results.getString("CERTIFICATE");
          info[k][8] = results.getString("DESCRIPTION");
          labels[k] = info[k][0];
          k++;
      }
      if(info[0][1] == null)
          return -1;
          
      // Dump out the results to a CSV file with the same format
      // using CsvJdbc helper function
      //boolean append = true;
    //  CsvDriver.writeToCsv(results, System.out, append);
  
      // Clean up
      results.close();
      stmt.close();
      conn.close();
	}
      catch (SQLException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
        return 0;
  }
    
    public String[] returnLabels()
    {
        try{
        //executeQuery("SELECT * FROM RedhoopTRAINCSV WHERE PROVIDER LIKE '%Udemy%'");
        int flag = executeQuery(query);
        if(flag == -1)
            return null;
         return labels;
        }
        catch (Exception e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
        return null;
    }
    
     /*public void SetQuery(String query){
        this.query=query;
       */ 
        
    }

