package myproject.nyt.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import myproject.nyt.R;
import myproject.nyt.adp.ArticleAdp;
import myproject.nyt.adp.SearchArticleAdp;
import myproject.nyt.model.ArticleMdl;
import myproject.nyt.model.ConnectionModel;
import myproject.nyt.model.SearchMdl;
import myproject.nyt.model.SearchModel;
import myproject.nyt.model.SearchWrapper;
import myproject.nyt.model.ViewModel;
import myproject.nyt.util.ConnectionLiveData;

public class SearchArticle extends AppCompatActivity
{
    ImageView imgBack,img;
    Button btnSearch;
    String str_query="";
    EditText edtSearch;
    RecyclerView listSearch;
    SearchArticleAdp searchArticleAdp=null;
    SearchModel searchModel;
    ConnectionLiveData connectionLiveData=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_articles);

        connectionLiveData = new ConnectionLiveData(this);
        searchModel = ViewModelProviders.of(this).get(SearchModel.class);

        imgBack = findViewById(R.id.imgBack);
        img = findViewById(R.id.img);
        btnSearch = findViewById(R.id.btnSearch);
        edtSearch = findViewById(R.id.edtSearch);
        listSearch = findViewById(R.id.listSearch);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchArticle.this,Mainpage.class));
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                str_query = edtSearch.getText().toString().trim();
                if(str_query.equals(""))
                {
                    Toast.makeText(SearchArticle.this, "Enter search query.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    connectionLiveData.observe(SearchArticle.this, new Observer<ConnectionModel>()
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
                                        //Toast.makeText(SearchArticle.this, String.format("Wifi turned ON"),         Toast.LENGTH_SHORT).show();
                                        searchModel.GetSearchedArticles(str_query).observe(SearchArticle.this, new Observer<List<SearchMdl.DocData>>() {
                                            @Override
                                            public void onChanged(List<SearchMdl.DocData> articleList) {
                                                System.out.println("articleList=" + articleList);
                                                prepareRecyclerView(articleList);
                                            }
                                        });
                                        break;
                                    }
                                    case 0:
                                    {
                                        img.setVisibility(View.GONE);
                                        //Toast.makeText(SearchArticle.this, String.format("Mobile data turned ON"), Toast.LENGTH_SHORT).show();
                                        searchModel.GetSearchedArticles(str_query).observe(SearchArticle.this, articleList -> {
                                            prepareRecyclerView(articleList);
                                        });
                                        break;
                                    }
                                }
                            } else {
                                img.setVisibility(View.VISIBLE);
                                Toast.makeText(SearchArticle.this, String.format("Connection turned OFF"), Toast.LENGTH_SHORT).show();
                                prepareRecyclerView(null);
                            }
                        }
                    });
                }
            }
        });
    }
    private void prepareRecyclerView(List<SearchMdl.DocData> articleList) {
        searchArticleAdp = new SearchArticleAdp(articleList);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            listSearch.setLayoutManager(new LinearLayoutManager(this));
        } else {
            listSearch.setLayoutManager(new GridLayoutManager(this, 2));
        }
        listSearch.setItemAnimator(new DefaultItemAnimator());
        listSearch.setAdapter(searchArticleAdp);
        searchArticleAdp.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(SearchArticle.this,Mainpage.class));
    }
}
