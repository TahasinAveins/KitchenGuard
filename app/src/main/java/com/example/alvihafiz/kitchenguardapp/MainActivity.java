package com.example.alvihafiz.kitchenguardapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String url_address = "http://www.iotkingdom.tk/login.php";
    String[] username;
    String[] userdetail;
    ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listitem);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();

        CustomListView customListView = new CustomListView(this,username,userdetail);
        listView.setAdapter(customListView);
    }

    private void collectData(){

        try {
            URL url = new URL(url_address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            is = new BufferedInputStream(connection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine())!= null)
            {
                sb.append(line+"\n");
            }
            is.close();
            result= sb.toString();

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null ;
            username = new String[ja.length()];
            userdetail = new String[ja.length()];

            for (i = 0; i<=ja.length();i++)
            {
                jo = ja.getJSONObject(i);
                username[i]= jo.getString("GAS");
                userdetail[i]= jo.getString("TIME");
            }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
