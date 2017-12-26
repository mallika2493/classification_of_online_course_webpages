import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MultipleCrawlerController {

	static File f;
	static BufferedWriter br;
	
        public void start() throws Exception
        {

    			f = new File("urlsRedhoop.txt");
    			br = new BufferedWriter(new FileWriter(f));
                String crawlStorageFolder = "C:/Users/meghana/workspace/Crawler";
                CrawlConfig config1 = new CrawlConfig();
               CrawlConfig config2 = new CrawlConfig();

                /*
                 * The two crawlers should have different storage folders for their
                 * intermediate data
                 */
               config1.setCrawlStorageFolder(crawlStorageFolder + "/crawler1");
              config2.setCrawlStorageFolder(crawlStorageFolder + "/crawler2");

               // config1.setPolitenessDelay(2000);
               config2.setPolitenessDelay(3000);

               // config1.setMaxPagesToFetch(50000);
             config2.setMaxPagesToFetch(50000);
             
             //config1.setMaxDepthOfCrawling(2);
             config2.setMaxDepthOfCrawling(5);
             
            // config1.setIncludeHttpsPages(true);
             config2.setIncludeHttpsPages(true);

                /*
                 * We will use different PageFetchers for the two crawlers.
                 */
                PageFetcher pageFetcher1 = new PageFetcher(config1);
                PageFetcher pageFetcher2 = new PageFetcher(config2);
                /*
                 * We will use the same RobotstxtServer for both of the crawlers.
                 */
                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher1);

                CrawlController controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer);
                CrawlController controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer);

               //String[] crawler1Domains = new String[] {"http://freevideolectures.com/", "http://ocw.mit.edu/courses" };
               String[] crawler2Domains = new String[] {"https://redhoop.com/"};
               //, "https://redhoop.com/" 
               //http://open.umich.edu/education

               // controller1.setCustomData(crawler1Domains);
               controller2.setCustomData(crawler2Domains);
                
               //controller1.addSeed("http://ocw.mit.edu/courses/find-by-department/");
               //controller1.addSeed("https://redhoop.com/online-course-providers.html");
               
             //controller2.addSeed("http://freevideolectures.com/");
            // controller2.addSeed("http://open.umich.edu/education/");
               controller2.addSeed("https://redhoop.com/online-course-providers.html");
               

                /*
                 * The first crawler will have 7 concurrent threads and the second
                 * crawler will have 7 threads.
                 */
          //      controller1.startNonBlocking(BasicCrawler.class, 15);
              controller2.startNonBlocking(BasicCrawler.class, 15);

        //        controller1.waitUntilFinish();
        //        System.out.println("Crawler 1 is finished.");
                
//
              controller2.waitUntilFinish();
              System.out.println("Crawler 2 is finished.");
               
               br.close();
        }
}
