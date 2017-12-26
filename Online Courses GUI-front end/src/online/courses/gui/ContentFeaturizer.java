package online.courses.gui;


import java.util.HashMap;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author meghana
 */
class ContentFeaturizer {
    String FeatureString = ""; 
	HashMap<String, String> Details = new HashMap<String, String>();
        public String returnContentFeatureString()
	{
		String ContentFeatureString = 
		Details.get("Course Name") + "\t" +		
		Details.get("School")+"\t"+
		Details.get("Price")+"\t"+
		Details.get("Duration")+"\t"+
		Details.get("Certificate")+"\t"+
		Details.get("Instructor")+"\t"+
		Details.get("Description")+"\t"+
		Details.get("Level")+"\t";
		
		return ContentFeatureString;
		
	}
		
	public void initializeDictionary()
	{
		Details.put("Course Name", "N/A");
		Details.put("School", "N/A");
		Details.put("Price", "N/A");
		Details.put("Duration", "N/A");
		Details.put("Certificate", "N/A");
		Details.put("Instructor", "N/A");
		Details.put("Description", "N/A");
		Details.put("Level", "N/A");
		
	}
        public String getTextMIT(Document doc, String url)
{
	   
	   try {
		    initializeDictionary();
		String title_info[] = doc.title().split("[|]");
		Elements metas = doc.getElementsByTag("meta");
		Elements authors, description;
		String author_names = "", description_text = "";
		for(Element meta : metas)
		{
			authors = meta.getElementsByAttributeValue("name", "Author");
			if (authors.size() > 0)
			{
				for (Element author : authors)
					author_names += author.attr("content").isEmpty() ? "" : author.attr("content")+" and ";
			}
			
			description = meta.getElementsByAttributeValue("name", "Description");
			if (description.size() == 1)
			{
				description_text = description.get(0).attr("content").isEmpty() ? "" : description.get(0).attr("content");
				if(description_text.indexOf('\n') == 0)
					description_text = description_text.substring(1);
				if(description_text.indexOf('\n') > 0)
				{
					description_text = description_text.replace('\n', ' ');
				}
			}
		}
		
		if(!author_names.isEmpty())
			Details.put("Instructor", author_names.substring(0, author_names.length()-4));
		if(!description_text.isEmpty())
			Details.put("Description", description_text);
		
		Elements levels = doc.getElementsByAttributeValue("itemprop", "typicalAgeRange");
		if (levels.size() != 0)
			Details.put("Level", levels.get(0).text());
		
		//System.out.println(Details.toString());
		
		if(!Details.get("Instructor").equalsIgnoreCase("N/A"))
		{
			Details.put("Course Name", title_info[0]);	
			Details.put("School", "MIT Open CourseWare");
			Details.put("Duration", "any time");
			Details.put("Certificate", "No");
                        Details.put("Price", "Free");
		}
	   }
	   catch(Exception e)
		{
			e.printStackTrace();
		}
		 return returnContentFeatureString();
}

	
}
