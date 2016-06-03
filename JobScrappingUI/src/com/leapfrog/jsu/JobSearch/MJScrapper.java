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
public class MJScrapper extends Scrapper {

    @Override
    public void scrap(String key) {
       
        String link = "http://www.jobsnepal.com/simple-job-search";
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

            String regex = "<a class=\"job-item\"(.*?) href=\"(.*?)\" >\\n(.*?)</a>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(builder.toString());

            while (matcher.find()) {

                System.out.println("job title: " + matcher.group(3).trim());
                System.out.println("job link " + matcher.group(2));

                FileWriter writer = new FileWriter(new File("d:/job.txt"));
                writer.write("job title: " + matcher.group(3).trim());
                writer.write("link " + matcher.group(2));
                writer.close();

            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
}
