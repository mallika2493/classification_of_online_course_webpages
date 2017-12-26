/*
 *  DeliciousService
 *
 * Created on 27 April, 2008, 6:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.saas.delicious;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.delicious.DeliciousAuthenticator;

public class DeliciousService {

    /** Creates a new instance of DeliciousService */
    public DeliciousService() {
    }

    /**
     * Retrieves representation of an instance of deliciousclient.DeliciousPostsGetResource
     * @param tag resource URI parameter
     * @param dt resource URI parameter
     * @param url resource URI parameter
     * @return an instance of java.lang.String
     */
    public static String getDeliciousPostsGetResource(String tag, String dt, String url) throws IOException {
        String result = null;

        String[][] queryParams = new String[][]{{"tag", tag}, {"dt", dt}, {"url", url}};
        RestConnection conn = new RestConnection("https://api.del.icio.us/v1/posts/suggest", queryParams);
        conn.setAuthenticator(new DeliciousAuthenticator());

        result = conn.get(null);
        return result;
    }
}
