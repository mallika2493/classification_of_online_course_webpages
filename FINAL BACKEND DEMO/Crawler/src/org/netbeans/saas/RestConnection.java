/*
 * RestConnection.java
 *
 * Created on 27 April, 2008, 6:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.saas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * RestConnection
 *
 * @author amit
 */
public class RestConnection {

    private HttpURLConnection conn;
    private String date;

    public RestConnection(String baseUrl) {
        this(baseUrl, null);
    }
    
    /** Creates a new instance of RestConnection */
    public RestConnection(String baseUrl, String[][] params) {
        this(baseUrl, null, params);
    }

    /** Creates a new instance of RestConnection */
    public RestConnection(String baseUrl, String[][] pathParams, String[][] params) {
        try {
            String urlStr = baseUrl;
            if(pathParams != null && pathParams.length > 0)
                urlStr = replaceTemplateParameters(baseUrl, pathParams);
            URL url = new URL(encodeUrl(urlStr, params));
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.setAllowUserInteraction(true);

            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            date = format.format(new Date());
            conn.setRequestProperty("Date", date);
        } catch (Exception ex) {
            Logger.getLogger(RestConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAuthenticator(Authenticator authenticator) {
        Authenticator.setDefault(authenticator);
    }

    public String getDate() {
        return date;
    }

    public String get() throws IOException {
        return get(null);
    }

    public String get(String[][] headers) throws IOException {
        conn.setRequestMethod("GET");

        return connect(headers, null);
    }
    
    public String put(byte[] data) throws IOException {
        return put(null, data);
    }
    
    public String put(String[][] headers, byte[] data) throws IOException {
        conn.setRequestMethod("PUT");
    
        return connect(headers, data);
    }

    public String post(byte[] data) throws IOException {
        return post(null, data);
    }
    
    public String post(String[][] headers, byte[] data) throws IOException {
        conn.setRequestMethod("POST");
        
        return connect(headers, data);
    }
    
    public String delete() throws IOException {
        return delete(null);
    }
    
    public String delete(String[][] headers) throws IOException {
        conn.setRequestMethod("DELETE");
        
        return connect(headers, null);
    }
    
    /**
     * @param baseUrl
     * @param params
     * @return response
     */
    @SuppressWarnings("finally")
	private String connect(String[][] headers,
            byte[] data) throws IOException {
        String result = null;
        try {
            // Send data
            setHeaders(headers);

            String method = conn.getRequestMethod();
            if (method.equals("PUT") || method.equals("POST")) {
                if (data != null) {
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    os.write(data);
                    os.flush();
                }
            }

           // System.out.println("response = " + conn.getResponseMessage());
            // Get the response
            StringBuilder sb = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();

            rd.close();
        } catch (Exception e) {
            String errMsg = "Cannot connect to :"+conn.getURL();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line;
                StringBuffer buf = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    buf.append(line);
                    System.out.print(line);
                }
                errMsg = buf.toString(); 
            } finally {
                throw new IOException(errMsg);
            }
        }
        return result;
    }

    private String replaceTemplateParameters(String baseUrl, String[][] pathParams) {
        String url = baseUrl;
        if (pathParams != null) {
            for (int i = 0; i < pathParams.length; i++) {
                String key = pathParams[i][0];
                String value = pathParams[i][1];
                if (value == null) {
                    value = "";
                }
                url = url.replace(key, value);
            }
        }
        return url;
    }

    private String encodeUrl(String baseUrl, String[][] params) {
        String p = "";

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                String key = params[i][0];
                String value = params[i][1];
                
                if (value != null) {
                    try {
                        p += key + "=" + URLEncoder.encode(value, "UTF-8") + "&";
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(RestConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (p.length() > 0) {
                p = "?" + p.substring(0, p.length() - 1);
            }
        }

        return baseUrl + p;
    }

    private void setHeaders(String[][] headers) {
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                conn.setRequestProperty(headers[i][0], headers[i][1]);
            }
        }
    }
}
