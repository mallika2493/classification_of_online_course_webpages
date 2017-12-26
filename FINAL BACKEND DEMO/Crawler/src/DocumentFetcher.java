

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class DocumentFetcher
{
	Document doc;
	
	public Document getDocument(String url) throws Exception
	{
		doc =  Jsoup
		        .connect(url)
		        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")     
		        .timeout(0).get();
		return doc;
	}
}
