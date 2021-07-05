package myproject.nyt.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import myproject.nyt.R;
import myproject.nyt.adp.ArticleAdp;
import myproject.nyt.model.ArticleMdl;
import myproject.nyt.model.ArticleWrapper;
import myproject.nyt.model.ConnectionModel;
import myproject.nyt.model.ViewModel;
import myproject.nyt.util.Config;
import myproject.nyt.util.ConnectionLiveData;
import myproject.nyt.util.Constants;
import myproject.nyt.util.JSONParser;

import static myproject.nyt.util.Constants.API_KEY;
import static myproject.nyt.util.Constants.NO_DATA;

public class PopularArticle extends AppCompatActivity
{
    ImageView imgBack;
    TextView txtPageTitle;
    ImageView img;
    RecyclerView listPopular;
    public static String str_query="",api_url="";

    Config config=null;
    ArticleAdp articleAdp=null;
    ViewModel mainViewModel;
    ConnectionLiveData connectionLiveData=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_articles);

        connectionLiveData = new ConnectionLiveData(this);
        config = new Config(this);

        txtPageTitle = findViewById(R.id.txtPageTitle);
        imgBack = findViewById(R.id.imgBack);
        img = findViewById(R.id.img);
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
        mainViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        connectionLiveData.observe(this, new Observer<ConnectionModel>()
        {
            @Override
            public void onChanged(@Nullable ConnectionModel connection)
            {
                if (connection.getIsConnected())
                {
                    switch (connection.getType())
                    {
                        case 1:
                        {
                            img.setVisibility(View.GONE);
                            Toast.makeText(PopularArticle.this, String.format("Wifi turned ON"),         Toast.LENGTH_SHORT).show();
                            mainViewModel.GetAllArticles(str_query).observe(PopularArticle.this, articleList -> {
                                prepareRecyclerView(articleList);
                            });
                            break;
                        }
                        case 0:
                        {
                            img.setVisibility(View.GONE);
                            Toast.makeText(PopularArticle.this, String.format("Mobile data turned ON"), Toast.LENGTH_SHORT).show();
                            mainViewModel.GetAllArticles(str_query).observe(PopularArticle.this, articleList -> {
                                prepareRecyclerView(articleList);
                            });
                            break;
                        }
                    }
                } else {
                    img.setVisibility(View.VISIBLE);
                    Toast.makeText(PopularArticle.this, String.format("Connection turned OFF"), Toast.LENGTH_SHORT).show();
                    prepareRecyclerView(null);
                }
            }
        });
    }
    private void prepareRecyclerView(List<ArticleMdl> articleList) {
        articleAdp = new ArticleAdp(articleList);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            listPopular.setLayoutManager(new LinearLayoutManager(this));
        } else {
            listPopular.setLayoutManager(new GridLayoutManager(this, 2));
        }
        listPopular.setItemAnimator(new DefaultItemAnimator());
        listPopular.setAdapter(articleAdp);
        articleAdp.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(PopularArticle.this,Mainpage.class));
    }
}
