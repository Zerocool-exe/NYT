package myproject.nyt.mutable;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import myproject.nyt.interfaces.RestApiService;
import myproject.nyt.model.ArticleMdl;
import myproject.nyt.model.ArticleWrapper;
import myproject.nyt.model.SearchMdl;
import myproject.nyt.model.SearchWrapper;
import myproject.nyt.util.Constants;
import myproject.nyt.util.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static myproject.nyt.util.Constants.SEARCH_HOST;

public class GetSearchArticles
{
    private List<SearchMdl.DocData> searchArticles = new ArrayList<>();
    private MutableLiveData<List<SearchMdl.DocData>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public GetSearchArticles(Application application) {
        this.application = application;
    }
    public MutableLiveData<List<SearchMdl.DocData>> getMutableLiveData(String query)
    {
        RestApiService apiService = RetrofitInstance.getApiService(SEARCH_HOST);
        Call<SearchWrapper> call = apiService.getSearchedArticle(query,Constants.API_KEY);
        call.enqueue(new Callback<SearchWrapper>() {
            @Override
            public void onResponse(Call<SearchWrapper> call, Response<SearchWrapper> response) {
                System.out.println("searchArticles="+response.toString());
                SearchWrapper mArticleWrapper = response.body();
                SearchMdl searchMdl = mArticleWrapper.getResponseResults();
                if (mArticleWrapper != null && mArticleWrapper.getResponseResults() !=null)
                {
                    searchArticles = searchMdl.getDocResults();
                    mutableLiveData.setValue(searchArticles);
                }
                else
                {
                    //System.out.println("searchArticles="+searchArticles.toString());
                }
            }
            @Override
            public void onFailure(Call<SearchWrapper> call, Throwable t) {
                System.out.println("searchArticles fail="+call.request());
                System.out.println("searchArticles fail="+t.getMessage());
            }
        });
        return mutableLiveData;
    }

}
