package myproject.nyt.util;

/**
 * Created by User on 3/4/2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class JSONParser
{
    Context ctx;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    Config config=null;

    // constructor
    public JSONParser(Context ctx) {
        this.ctx=ctx;
        this.config=new Config(ctx);
    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,List<NameValuePair> params)
    {
        // Making HTTP request
        HttpParams parameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parameters, 3000);
        HttpConnectionParams.setSoTimeout(parameters, 5000);
        DefaultHttpClient httpClient = new DefaultHttpClient(parameters);
        try
        {
            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                //DefaultHttpClient httpClient = new DefaultHttpClient(parameters);
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method == "GET"){
                // request method is GET

                //DefaultHttpClient httpClient = new DefaultHttpClient(parameters);
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            //System.out.println("IOException exception=="+e.toString());
            ((Activity)ctx).runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(ctx, "Slow internet connection. Please check your internet connection.", Toast.LENGTH_LONG).show();
                    //Snackbar.make(((Activity) ctx),"Slow internet connection.",Snackbar.LENGTH_SHORT);
                    //json = "{\"success\":\"-1\",\"message\":\"Slow internet connection\"}";
                }
            });
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        /*if(hasConnection(url))
        {

        }
        else
        {
            ((Activity)ctx).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx, "Connection timeout. Please try again without internet connection.", Toast.LENGTH_SHORT).show();
                }
            });
        }*/
        // return JSON String
        return jObj;

    }
    public String parse(String s) throws ParseException {
        return s.toString();
    }
    public boolean hasConnection(String url)
    {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");

            con.setConnectTimeout(3000); //set timeout to 3 seconds

            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (SocketTimeoutException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
