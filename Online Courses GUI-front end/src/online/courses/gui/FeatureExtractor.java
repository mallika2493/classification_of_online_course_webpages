package online.courses.gui;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author meghana
 */
class FeatureExtractor {
        String FeatureString = "";
	ContentFeaturizer content_featurizer;
	DocumentFetcher document_fetcher;
	Document doc;
	
	public FeatureExtractor() throws Exception
	{
		document_fetcher = new DocumentFetcher();
		content_featurizer = new ContentFeaturizer();
	}
	
	/*public static void main(String args[]) throws Exception
	{
		FeatureExtractor f = new FeatureExtractor();
		f.returnFeatureString("http://open.umich.edu/");
	}*/
	
	
	public String returnFeatureString(String url)
 {
	try
	{
		doc = document_fetcher.getDocument(url);
		FeatureString = "";
		FeatureString += url + "\t";	//can't be null
		String title = doc.title();
		String temp[] = url.split("/");
		String domain = "";
		
		if(temp.length > 1) 
                    domain = temp[0]+"//"+temp[2]; else domain = url;
		//System.out.println(domain);
		FeatureString += title + "\t";	//can't be null
		//System.out.println("title : " + title);
 
		// get all page features
		
		Elements h1 = doc.select("h1");
		Elements h2 = doc.select("h2");
		Elements metas = doc.getElementsByTag("meta");
		Elements paras = doc.getElementsByTag("p");
                String headings = "";
                 String paratext = "";
                 String description_text = "";
        
		for(Element meta : metas)
		{
			Elements descript = meta.getElementsByAttributeValue("name", "description");
			if(descript.size() == 0)
				descript = meta.getElementsByAttributeValue("name", "Description");
			if(descript.size() == 1)
			{
				description_text = ((descript.attr("content").isEmpty() ? "N/A\t" : descript.attr("content")+"\t"));
			if(description_text.indexOf('\n') == 0)
				description_text = description_text.substring(1);
			if(description_text.indexOf('\n') > 0)
			   {
				description_text = description_text.replace('\n', ' ');
			   }
			//System.out.println("DT: "+description_text);
			FeatureString += description_text;
			}

			/*Elements keywords = meta.getElementsByAttributeValue("name", "keywords");
			if(keywords.size() == 0)
				keywords = meta.getElementsByAttributeValue("name", "Keywords");
			if(keywords.size() != 0)
			{
				//System.out.println("CONTENT : "+ (keywords.attr("content").isEmpty() ? "N/A\t" : keywords.attr("content"))+"\t");
			FeatureString += ((keywords.attr("content").isEmpty() ? "N/A\t" : keywords.attr("content")+"\t"));
			}*/

		}
	
		for (Element header : h1)
		{
			//System.out.println("Text of header: " + header.text());
			headings += header.text() + " ";
		}
		for (Element header : h2)
		{
			//System.out.println("Text of header: " + header.text());
			headings += header.text() + " ";
		}

		//FeatureString += (headings.isEmpty()) ? "N/A\t" : headings + "\t";
		
		for (Element para : paras)
		{
			//System.out.println("Text of header: " + header.text());
			paratext += para.text() + " ";
		}
		
		//FeatureString += (paratext.isEmpty()) ? "N/A\t" : paratext + "\t";
		
		
		
		String ContentFeatures = ""; 
		
				
	       ContentFeatures = content_featurizer.getTextMIT(doc,url);
			
		//System.out.println("CONTENT FEATURES: ");		
		FeatureString += ContentFeatures;
		
		cleanFeatureString();
		
 
	} catch (Exception e) {
		e.printStackTrace();return "";
	}
		//System.out.println(FeatureString);
		return FeatureString;
  }

	public void cleanFeatureString()
	{
		FeatureString = FeatureString.replace("\"", " ");
		FeatureString = FeatureString.replace(",", ";");
		FeatureString = FeatureString.replace("\\", " ");
		FeatureString = FeatureString.replace("'", " ");
		FeatureString = FeatureString.replace("\t", ",");
		FeatureString = FeatureString.replace("\n", ",");
		//System.out.println(FeatureString);
		
	}
    
}
