package com.example.mohamed.newsakhbar;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mohamed on 6/28/2018.
 */

public class Function {
    public static boolean isNetworkAvailable(Context context){
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;

    }
    public static String executeGet(String targetUrl , String urlParameters){
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("content-type","application/json;  charset=utf-8");
            connection.setRequestProperty("Content-Language","en-US");
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(false);

            InputStream is;
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)
                is = connection.getErrorStream();
            else
                is = connection.getInputStream();


                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = rd.readLine())!=null){
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();


        } catch (IOException e) {

        return null;

        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }


}
