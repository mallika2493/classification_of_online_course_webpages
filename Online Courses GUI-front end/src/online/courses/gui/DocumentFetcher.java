package online.courses.gui;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author meghana
 */
class DocumentFetcher {
    Document doc;
    String url = "http://ocw.mit.edu/courses/new-courses/";
	
	public Document getDocument(String url) throws Exception
	{
		doc =  Jsoup
		        .connect(url)
		        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")     
		        .timeout(0).get();
		return doc;
	}
	    
}
