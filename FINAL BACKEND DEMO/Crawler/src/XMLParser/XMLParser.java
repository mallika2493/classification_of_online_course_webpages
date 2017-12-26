package XMLParser;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLParser{

    public String getPopularTags(String input)
    {
    	String popular_tag_keywords = "", keyword = "";
    try {
    		
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;
            StringReader sr = new StringReader(input);
            InputSource is = new InputSource(sr);
            doc = docBuilder.parse(is);

            // normalize text representation
           Node node;
           int  numtags;
           Element root = doc.getDocumentElement();
        //   System.out.println ("Root element of the doc is " +  doc.getDocumentElement().getNodeName());
           NodeList tags = root.getChildNodes();
           for(int i = 0; i < tags.getLength(); i++)
           {	  	
        	   		node = tags.item(i);
        	   		if(node.getNodeName() == "popular")
           			{
        	   			numtags = node.getAttributes().getLength();
        	   			for(int j = 0; j < numtags; j++)
        	   			{
        	   				keyword = node.getAttributes().item(j).getNodeValue();
        	   				//System.out.println(keyword);
        	   				popular_tag_keywords += keyword+",";
        	   			}
           			}
           		
        	   		
           }
           
          // System.out.println(popular_tag_keywords);
    }
            catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
    return popular_tag_keywords;
        //System.exit (0);

    }//end of function

}