package com.example.mohamed.newsakhbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String API_KEY = "153db0449f0047c78853bffd45cf9264";
    String News_Source = "football-italia";
    ListView lvnews;
    ProgressBar loader;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String Key_Author = "author";
    static final String Key_Title = "title";
    static final String Key_Description = "description";
    static final String Key_Url = "url";
    static final String Key_UrlToImage = "urlToImage";
    static final String Key_PublishedAt = "publishedAt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvnews = (ListView) findViewById(R.id.listNews);
        loader = (ProgressBar) findViewById(R.id.loader);
        lvnews.setEmptyView(loader);

        if(Function.isNetworkAvailable(getApplicationContext()))
        {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class DownloadNews extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String xml = "";
            String urlparameters ="";
            //https://newsapi.org/v1/articles?source="+NEWS_SOURCE+"&sortBy=top&apiKey=
            // "https://newsapi.org/v1/articles?source="+NEWS_SOURCE+"&sortBy=top&apiKey="+API_KEY, urlParameters
            xml = Function.executeGet("https://newsapi.org/v2/top-headlines?sources="+News_Source+"&sortBy=top&apiKey="+API_KEY, urlparameters);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {
            if (xml.length()>10){
                try {
                    JSONObject jsonresponse =  new JSONObject(xml);
                    JSONArray jsonArray = jsonresponse.optJSONArray("articles");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String,String>map = new HashMap<String, String>();
                        map.put(Key_Author,jsonObject.optString(Key_Author).toString());
                        map.put(Key_Title,jsonObject.optString(Key_Title).toString());
                        map.put(Key_Description,jsonObject.optString(Key_Description).toString());
                        map.put(Key_Url,jsonObject.optString(Key_Url).toString());
                        map.put(Key_UrlToImage,jsonObject.optString(Key_UrlToImage).toString());
                        map.put(Key_PublishedAt,jsonObject.optString(Key_PublishedAt).toString());
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "unExpected Error", Toast.LENGTH_LONG).show();
                }
                ListNewsAdapter adapter = new ListNewsAdapter(MainActivity.this,dataList);
                lvnews.setAdapter(adapter);

                lvnews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int positon, long id) {
                        Intent i = new Intent(MainActivity.this,Details.class);
                        i.putExtra("url",dataList.get(+positon).get(Key_Url));
                        startActivity(i);
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "No News Found", Toast.LENGTH_SHORT).show();
            }

        }

    }


}
