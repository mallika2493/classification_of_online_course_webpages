import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public class BasicCrawler extends WebCrawler {

        private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
                        + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        private String[] myCrawlDomains;
       static BufferedWriter br;

       
       public static BufferedWriter getBufferedWriter()
       {
    	   return MultipleCrawlerController.br;
       }
       
       
        @Override
        public void onStart() {
                myCrawlDomains = (String[]) myController.getCustomData();
                br = getBufferedWriter();
        }

        @Override
        public boolean shouldVisit(WebURL url) {
                String href = url.getURL().toLowerCase();
                if (FILTERS.matcher(href).matches() || 
                	href.indexOf("css", 0) != -1 || 
                	href.indexOf("search", 0) != -1 ||
                	href.indexOf("accounts" , 0) != -1 || 
                	href.indexOf("blog" , 0) != -1 ||
                	href.indexOf("rss/" , 0) != -1 ||
                	href.indexOf("login" , 0) != -1)
                	 
                {
                        return false;
                }
               for (String crawlDomain : myCrawlDomains) {
                        if (href.startsWith(crawlDomain)) {
                                return true;
                        }
                }
                return false;
        }

        @Override
        public void visit(Page page)
        {
   
                String url = page.getWebURL().getURL();
                System.out.println(url);
                try {br.write(url+"\n");} catch (IOException e){}

        }
}
