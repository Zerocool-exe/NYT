package myproject.nyt.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import myproject.nyt.R;
import myproject.nyt.adp.ArticleAdp;
import myproject.nyt.model.ArticleMdl;
import myproject.nyt.util.Config;
import myproject.nyt.util.Constants;
import myproject.nyt.util.JSONParser;

import static myproject.nyt.util.Constants.API_KEY;
import static myproject.nyt.util.Constants.NO_DATA;

public class PopularArticle extends AppCompatActivity
{
    ImageView imgBack;
    TextView txtPageTitle;
    ListView listPopular;
    public static String str_query="",api_url="";

    Config config=null;
    ArticleMdl articleMdl=null;
    ArticleAdp articleAdp=null;
    ArrayList<ArticleMdl> articleMdls=null;
    JSONParser jsonParser=null;
    JSONArray jsonArray=null,jsonArray1=null;
    JSONObject jsonObject=null,jsonObject1=null;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_articles);

        config = new Config(this);
        jsonParser = new JSONParser(this);

        txtPageTitle = findViewById(R.id.txtPageTitle);
        imgBack = findViewById(R.id.imgBack);
        listPopular = findViewById(R.id.listPopular);

        str_query = getIntent().getExtras().getString("type");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PopularArticle.this,Mainpage.class));
            }
        });
        if(str_query.equals("MV"))
        {
            txtPageTitle.setText(R.string.txt_most_viewed);
            api_url = config.MOST_VIEWED;
        }
        else if(str_query.equals("MS"))
        {
            txtPageTitle.setText(R.string.txt_most_shared);
            api_url = config.MOST_SHARED;
        }
        else if(str_query.equals("ME"))
        {
            txtPageTitle.setText(R.string.txt_most_emailed);
            api_url = config.MOST_EMAILED;
        }
        if(config.isNetworkAvailable(PopularArticle.this))
        {
            new GetArticles().execute();
        }
        else
        {
            Toast.makeText(PopularArticle.this, Constants.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        }
    }
    private class GetArticles extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(PopularArticle.this);
            pDialog.setMessage("Retrieving articles...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String[] param)
        {
            try
            {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("api-key", API_KEY));

                JSONObject json = (JSONObject) jsonParser.makeHttpRequest(api_url,"GET", params);

                articleMdls = new ArrayList<>();
                articleMdls.clear();
                if(json.optString("status").equals("OK"))
                {
                    jsonArray = json.optJSONArray("results");
                    System.out.println("results="+json.optJSONArray("results"));
                    if(jsonArray.length()>0)
                    {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            jsonObject1 = jsonArray.optJSONObject(i);
                            articleMdl = new ArticleMdl(jsonObject1.optString("title"),jsonObject1.optString("published_date"));
                            articleMdls.add(articleMdl);
                        }
                        articleAdp = new ArticleAdp(PopularArticle.this,articleMdls);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listPopular.setAdapter(articleAdp);
                            }
                        });
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PopularArticle.this, NO_DATA, Toast.LENGTH_SHORT).show();
                                listPopular.setAdapter(null);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();
        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(PopularArticle.this,Mainpage.class));
    }
}
