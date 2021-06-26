package myproject.nyt.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SearchArticle extends AppCompatActivity
{
    TextView txtPageTitle;
    EditText edtSearch;
    Button btnSearch;
    ImageView imgBack;
    ListView listArticles;
    public static String str_query="";

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
        setContentView(R.layout.search_articles);

        config = new Config(this);
        jsonParser = new JSONParser(this);

        txtPageTitle = findViewById(R.id.txtPageTitle);         txtPageTitle.setText(R.string.txt_search_article);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        imgBack = findViewById(R.id.imgBack);
        listArticles = findViewById(R.id.listSearchArticles);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchArticle.this,Mainpage.class));
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_query = edtSearch.getText().toString().trim();
                if(str_query.equals(""))
                {
                    Toast.makeText(SearchArticle.this, R.string.query_null, Toast.LENGTH_SHORT).show();
                    listArticles.setAdapter(null);
                }
                else
                {
                    if(config.isNetworkAvailable(SearchArticle.this))
                    {
                        new GetArticles().execute();
                    }
                    else
                    {
                        Toast.makeText(SearchArticle.this, Constants.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private class GetArticles extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchArticle.this);
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
                params.add(new BasicNameValuePair("q", str_query));
                params.add(new BasicNameValuePair("api-key", API_KEY));

                JSONObject json = (JSONObject) jsonParser.makeHttpRequest(config.GET_ARTICLES, "GET", params);

                articleMdls = new ArrayList<>();
                articleMdls.clear();
                if(json.optString("status").equals("OK"))
                {
                    jsonObject = json.optJSONObject("response");
                    if (jsonObject.length()>0)
                    {
                        jsonArray = jsonObject.optJSONArray("docs");
                        if(jsonArray.length()>0)
                        {
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                jsonObject1 = jsonArray.optJSONObject(i);
                                articleMdl = new ArticleMdl(jsonObject1.optString("abstract"),jsonObject1.optString("pub_date"));
                                articleMdls.add(articleMdl);
                            }
                            articleAdp = new ArticleAdp(SearchArticle.this,articleMdls);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listArticles.setAdapter(articleAdp);
                                }
                            });
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SearchArticle.this, NO_DATA, Toast.LENGTH_SHORT).show();
                                    listArticles.setAdapter(null);
                                }
                            });
                        }
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SearchArticle.this, NO_DATA, Toast.LENGTH_SHORT).show();
                                listArticles.setAdapter(null);
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
        startActivity(new Intent(SearchArticle.this,Mainpage.class));
    }
}
