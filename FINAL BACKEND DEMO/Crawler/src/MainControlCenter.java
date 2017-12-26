import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class MainControlCenter
{
	public static void main(String args[]) throws Exception
	{
		
		//MultipleCrawlerController mcc = new MultipleCrawlerController();
		//mcc.start();
		
		String url = "", fs = "";
		
		BufferedReader br = new BufferedReader(new FileReader("UmichURLs.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("UmichDemo.csv")));
		
		FeatureExtractor f = new FeatureExtractor();
		bw.write("URL,TITLE,METADESCRIPTION,KEYWORDS,SOCIALTAGS,COURSENAME,PROVIDER,PRICE,DURATION,CERTIFICATE,INSTRUCTOR,DESCRIPTION,LEVEL,ISVALID?\n");
		while ((url = br.readLine()) != null)
		{
			fs = (f.returnFeatureString(url));
			if(!fs.isEmpty()) bw.write(fs+"?\n");
			System.out.println(fs);
		}
		
		br.close();
		bw.close();
	}
		
}
