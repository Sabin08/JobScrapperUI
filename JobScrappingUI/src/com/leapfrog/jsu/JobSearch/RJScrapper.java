/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.jsu.JobSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sabin
 */
public class RJScrapper extends Scrapper {

    @Override
    public void scrap(String key) {
         String link = "http://www.ramrojob.com/";
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
           
            
            byte[] data =("Keywords=" + key).getBytes();
            os.write(data);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            reader.close();

            String regex = "<a href=\"(.*?)\" title=\"(.*?)\">\\n(.*?)</a>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(builder.toString());
            StringBuilder container=new StringBuilder();
            while (matcher.find()) {

                 String title=matcher.group(3).trim();
                String weblink= matcher.group(2);
                System.out.println("job title: " + title);
                System.out.println("job link " + weblink);
                
                container.append("job title: " + title + "\r\n");
                container.append("job link " + weblink+ "\r\n");
            }
            
             FileWriter writer = new FileWriter(new File("d:/job.txt"));
                
                writer.write(container.toString());
                writer.close();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
}
