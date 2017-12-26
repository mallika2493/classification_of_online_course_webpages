
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ContentFeaturizer
{
	String FeatureString = ""; 
	HashMap<String, String> Details = new HashMap<String, String>();
	/*course name
	 provider
	free
	duration
	certification
	instructor
	description
	URL keyword analysis  */
	
/*public static void main(String args[]) throws Exception
	{
		ContentFeaturizer c = new ContentFeaturizer();
		DocumentFetcher d = new DocumentFetcher();
		//c.getTextRedHoop("https://redhoop.com/pluralsight-python-fundamentals-8013-2.html");
		//c.getTextFreeVideoLectures("http://freevideolectures.com/Course/3157/Introduction-to-Finance");
		c.getTextMIT(d.getDocument("http://ocw.mit.edu/courses/urban-studies-and-planning/11-122-environment-and-society-fall-2002/"),
				"http://ocw.mit.edu/courses/urban-studies-and-planning/11-122-environment-and-society-fall-2002/");
		//c.getTextUMich("http://open.umich.edu/education/dent");
	}*/
	
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
	
   public String getTextRedHoop(Document doc, String url)
   {
	  try
	  {
		Elements tables = doc.getElementsByTag("table");
		if(tables.isEmpty())
			{
			initializeDictionary();
			//System.out.println(Details.toString());
			return returnContentFeatureString();
			}
		else
		{
			initializeDictionary();
			Details.put("Course Name",doc.title());
			Element table = tables.get(0);		
		Elements rows = table.getElementsByTag("tr");
		Elements innertags;
		for (Element row : rows)
		{
			innertags = row.children();
			if(!innertags.get(0).text().isEmpty())
			Details.put(innertags.get(0).text(), innertags.get(innertags.size()-1).text().isEmpty() ? "N/A" : innertags.get(innertags.size()-1).text());
			if(innertags.text().contains("School"))
				break;
		}
		Details.put("Description", Details.get("Description").equalsIgnoreCase("N/A")? "N/A" : 
																								Details.get("Description").indexOf("Hide Details") == -1 ?
																											Details.get("Description") : 
																											Details.get("Description").substring(26));
		Details.put("Certificate", Details.get("Certificate").equalsIgnoreCase("N/A") ? 
				"N/A" :
					Details.get("Certificate").indexOf("Yes") > -1 ? 
								Details.get("Certificate").substring(0,3)
								:Details.get("Certificate").substring(0,2));
		
			//System.out.println(Details.toString());
			}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	 return returnContentFeatureString();
}



public String getTextFreeVideoLectures(Document doc, String url)
{
	   
	   try {
		   if(url.indexOf("/Course/") == -1)
		   {
			   initializeDictionary();
			  // System.out.println(Details.toString());
			   return returnContentFeatureString();
		   }
		   else
{	   

		String info[] = doc.title().split(",");
		Details.put("Course Name", info[0]);
		if(info.length > 1)
		Details.put("School", info[1]);
		else
			Details.put("Instructor", "N/A");
		if(info.length > 2)
		Details.put("Instructor", info[2]);
		else
			Details.put("Instructor", "N/A");	
		Details.put("Price", "free");
		Details.put("Duration", "any time");
		Details.put("Certificate", "No");
		Details.put("Level", "Any");
		
		Element divcontent = doc.getElementById("content");
		Elements paracontent = divcontent.getElementsByTag("p");
		Details.put("Description", paracontent.get(1).text());
		
		//System.out.println(Details.toString());
		
		
}			}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	 return returnContentFeatureString();
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
		}
	   }
	   catch(Exception e)
		{
			e.printStackTrace();
		}
		 return returnContentFeatureString();
}

public String getTextUMich(Document doc, String url)
{
	try
	{
		initializeDictionary();
		Elements metas = doc.getElementsByTag("meta");
		Elements title, description; 
		for (Element meta : metas)
		{
			title = meta.getElementsByAttributeValue("property", "dc:title");
			if (title.size() == 1)
			{
				Details.put("Course Name", title.attr("content"));
			}
			
			description = meta.getElementsByAttributeValue("name", "dcterms.description");
			if (description.size() == 1)
			{
				Details.put("Description", description.attr("content"));
			}
		}
		Elements paras = doc.getElementsByTag("p");
		String info = "";
		String[] temp;
		for (Element para : paras)
		{
			if(para.getElementsByTag("strong").size() == 1)
				info = para.text();
			if(info.indexOf("Instructor") > -1)
			{
				temp = info.split(":");
				Details.put("Instructor", temp[1]);
			}
			
			if(info.indexOf("Course Level") > -1)
			{
				temp = info.split(":");
				Details.put("Level", temp[1]);
			}
			
			if(info.indexOf("Structure") > -1)
			{
				temp = info.split(":");
				Details.put("Duration", temp[1]);
			}
		}
		if(!Details.get("Instructor").equalsIgnoreCase("N/A"))
		{	
		Details.put("School", "University of Michigan, Ann Arbor");
		Details.put("Price", "Free");
		Details.put("Certificate", "No");
		}
		else
		{
			initializeDictionary();
		}
		//System.out.println(Details);
		
	}
	catch (Exception e){}
	return returnContentFeatureString();
}






}


