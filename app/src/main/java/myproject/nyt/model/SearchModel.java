package myproject.nyt.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import myproject.nyt.mutable.GetSearchArticles;
import myproject.nyt.ui.GetArticles;
import myproject.nyt.util.Constants;

public class SearchModel extends AndroidViewModel
{
    private GetSearchArticles searchArticles;
    public SearchModel(@NonNull Application application) {
        super(application);
        searchArticles = new GetSearchArticles(application);
    }
    public LiveData<List<SearchMdl.DocData>> GetSearchedArticles(String query) {
        return searchArticles.getMutableLiveData(query);
    }
}
