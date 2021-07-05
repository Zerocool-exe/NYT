package myproject.nyt.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import myproject.nyt.mutable.GetSearchArticles;
import myproject.nyt.mutable.GetArticles;

public class ViewModel extends AndroidViewModel
{
    private GetArticles popularArticles;
    private GetSearchArticles searchArticles;

    public ViewModel(@NonNull Application application) {
        super(application);
        popularArticles = new GetArticles(application);
        searchArticles = new GetSearchArticles(application);
    }
    public LiveData<List<ArticleMdl>> GetAllArticles(String api_url) {
        return popularArticles.getMutableLiveData(api_url);
    }
    public LiveData<List<SearchMdl.DocData>> GetSearchedArticles(String query) {
        return searchArticles.getMutableLiveData(query);
    }
}
