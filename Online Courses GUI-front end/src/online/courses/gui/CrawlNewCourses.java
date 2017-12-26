package online.courses.gui;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
public class CrawlNewCourses {
    static File f;
    static BufferedWriter br;
    DocumentFetcher document_fetcher;
    Document doc;
    
   public void doWork() 
	{
		try{
		CrawlNewCourses mcc = new CrawlNewCourses();
		mcc.start();
		
		String url = "", fs = "";
		
		BufferedReader br = new BufferedReader(new FileReader("NewCoursesURLS.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("NewCoursesFeatures.csv")));
		
		FeatureExtractor f = new FeatureExtractor();
		bw.write("URL,TITLE,METADESCRIPTION,COURSENAME,PROVIDER,PRICE,DURATION,CERTIFICATE,INSTRUCTOR,DESCRIPTION,LEVEL,ISVALID?\n");
		while ((url = br.readLine()) != null)
		{
                        if(url.equals
        ("http://ocw.mit.edu/courses/mathematics/18-821-project-laboratory-in-mathematics-spring-2013")
                                   || url.equals("http://ocw.mit.edu/courses/physics/8-851-effective-field-theory-spring-2013") 
                                    || url.equals("http://ocw.mit.edu/courses/special-programs/sp-2322-unicorns-and-rainbows-a-seminar-fall-2014"))
                            continue;
			fs = (f.returnFeatureString(url));
			if(!fs.isEmpty()) bw.write(fs+"?\n");
			System.out.println(fs);
		}
		
		br.close();
		bw.close();
                }catch(Exception e){}
	}
    
     public void start() throws Exception
        {
         document_fetcher = new DocumentFetcher();
         
    	 f = new File("NewCoursesURLS.txt");
    	 br = new BufferedWriter(new FileWriter(f));
         try
	{
		doc = document_fetcher.getDocument("http://ocw.mit.edu/courses/new-courses/");
                Elements tables = doc.getElementsByClass("courseList");
                Elements eachtable,hrefs;
                String children;
                Elements headbody,td;
                Element body,href;
                int i = 0;
                for (Element table : tables)
                {
                   headbody = table.children();
                   body = headbody.get(1);
                   td= body.getElementsByTag("tr");
                   for (Element t : td)
                   
                   {
                       
                       String url = (t.getElementsByTag("a").get(0).attr("href"));
                       if(!url.isEmpty())
                       {
                           i++;
                       System.out.println("http://ocw.mit.edu"+t.getElementsByTag("a").get(0).attr("href")+"\n");
                           br.write("http://ocw.mit.edu"+t.getElementsByTag("a").get(0).attr("href")+"\n");
                       }
                   }
                   //hrefs = body.getElementsByTag("a");
                  // System.out.println(hrefs.size());
                  // i++;
                   //br.write("http://ocw.mit.edu"+children+"\n");
                            
                }
                  
               
                System.out.println(i);
                br.close();
                
                //System.out.println("list"+ c.get(1));
        }
         
         catch (Exception e) {
		e.printStackTrace();
                
	}
		
		
         
         
         
         
         
         
         
         
         
         
         
         
         
         /*CrawlConfig config1 = new CrawlConfig();
         CrawlConfig config2 = new CrawlConfig();

                /*
                 * The two crawlers should have different storage folders for their
                 * intermediate data
                 */
              /* config1.setCrawlStorageFolder(crawlStorageFolder + "/crawler1");
              //config2.setCrawlStorageFolder(crawlStorageFolder + "/crawler2");

                config1.setPolitenessDelay(2000);
               //config2.setPolitenessDelay(3000);

               config1.setMaxPagesToFetch(50000);
             //config2.setMaxPagesToFetch(50000);
             
             config1.setMaxDepthOfCrawling(2);
             //config2.setMaxDepthOfCrawling(5);
             
            config1.setIncludeHttpsPages(true);
            // config2.setIncludeHttpsPages(true);

                /*
                 * We will use different PageFetchers for the two crawlers.
                 */
              /*  PageFetcher pageFetcher1 = new PageFetcher(config1);
               PageFetcher pageFetcher2 = new PageFetcher(config2);
                /*
                 * We will use the same RobotstxtServer for both of the crawlers.
                 */
                /*RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher1);

                CrawlController controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer);
                CrawlController controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer);

              String[] crawler1Domains = new String[] {"http://ocw.mit.edu/courses" };
               String[] crawler2Domains = new String[] {"https://redhoop.com/"};
               //, "https://redhoop.com/" 
               //http://open.umich.edu/education

                controller1.setCustomData(crawler1Domains);
               controller2.setCustomData(crawler2Domains);
                
               controller1.addSeed("http://ocw.mit.edu/courses/new-courses/");
               controller1.addSeed("https://redhoop.com/online-course-providers.html");
               
             controller2.addSeed("http://freevideolectures.com/");
             controller2.addSeed("http://open.umich.edu/education/");
               controller2.addSeed("https://redhoop.com/online-course-providers.html");
               

                /*
                 * The first crawler will have 7 concurrent threads and the second
                 * crawler will have 7 threads.
                 */
            /*controller1.startNonBlocking(BasicCrawler.class, 15);
             controller2.startNonBlocking(BasicCrawler.class, 15);

            controller1.waitUntilFinish();
                System.out.println("Crawler 1 is finished.");
                

              controller2.waitUntilFinish();
              System.out.println("Crawler 2 is finished.");
               
             
                  */
         
         
        }
    
}
