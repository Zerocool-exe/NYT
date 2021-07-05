package myproject.nyt.ui;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import myproject.nyt.interfaces.RestApiService;
import myproject.nyt.model.ArticleMdl;
import myproject.nyt.model.ArticleWrapper;
import myproject.nyt.util.Constants;
import myproject.nyt.util.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static myproject.nyt.util.Constants.POPULAR_HOST;

public class GetArticles
{
    private ArrayList<ArticleMdl> popularArticles = new ArrayList<>();
    private MutableLiveData<List<ArticleMdl>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public GetArticles(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<ArticleMdl>> getMutableLiveData(String api_url)
    {
        RestApiService apiService = RetrofitInstance.getApiService(POPULAR_HOST);
        Call<ArticleWrapper> call;
        if(api_url.equals("MV"))
        {
            call = apiService.getPopularArticle(Constants.API_KEY);
        }
        else if(api_url.equals("MS"))
        {
            call = apiService.getSharedArticle(Constants.API_KEY);
        }
        else if(api_url.equals("ME"))
        {
            call = apiService.getEmailedArticle(Constants.API_KEY);
        }
        else
        {
            call = apiService.getPopularArticle(Constants.API_KEY);
        }

        call.enqueue(new Callback<ArticleWrapper>() {
            @Override
            public void onResponse(Call<ArticleWrapper> call, Response<ArticleWrapper> response) {
                ArticleWrapper mArticleWrapper = response.body();
                if (mArticleWrapper != null && mArticleWrapper.getDataResults() != null) {
                    popularArticles = (ArrayList<ArticleMdl>) mArticleWrapper.getDataResults();
                    mutableLiveData.setValue(popularArticles);
                }
            }
            @Override
            public void onFailure(Call<ArticleWrapper> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }
}
