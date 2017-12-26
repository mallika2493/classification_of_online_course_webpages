/*
 * DeliciousAuthenticator.java
 *
 * Created on 27 April, 2008, 6:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.saas.delicious;

import java.io.IOException;
import java.net.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author amit
 */
public class DeliciousAuthenticator extends Authenticator {
    private String username;
    
    private String password;
    
    public DeliciousAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public DeliciousAuthenticator() {
        try {
            Properties props = new Properties();
            props.load(DeliciousAuthenticator.class.getResourceAsStream(
                    "profile.properties"));
            username = props.getProperty("username");
            password = props.getProperty("password");
        } catch (IOException ex) {
            Logger.getLogger(DeliciousAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password.toCharArray());
    }
}
