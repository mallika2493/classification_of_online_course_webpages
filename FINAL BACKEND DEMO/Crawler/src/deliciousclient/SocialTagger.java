/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package deliciousclient;

import org.netbeans.saas.delicious.DeliciousService;

import XMLParser.XMLParser;

public class SocialTagger {

    /**
     * @param args the command line arguments
     */
	

    public static String getSocialTags(String url) {
        //Returns posts matching the arguments. If no date or url is given, 
        //most recent date will be used.
        
        try {
             String tag = null;
             String dt = null;
             //String url = "http://www.udacity.com";
             //TODO : FOR EACH URL GET POPULAR TAGS AND MATCH WITH OUR KEYWORDS
             String result = DeliciousService.getDeliciousPostsGetResource(tag, dt, url);
            // System.out.println("XML returned: "+result);
             XMLParser myparser = new XMLParser();
             String tags = myparser.getPopularTags(result);
            // System.out.println("Tags : "+tags);
             return tags;
            
        } catch (java.io.IOException ex) {
             //java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
             ex.printStackTrace();
        }
        return "";

    }

}
