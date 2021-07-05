package myproject.nyt.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import myproject.nyt.ui.GetArticles;

public class ViewModel extends AndroidViewModel
{
    private GetArticles popularArticles;
    public ViewModel(@NonNull Application application) {
        super(application);
        popularArticles = new GetArticles(application);
    }
    public LiveData<List<ArticleMdl>> GetAllArticles(String api_url) {
        return popularArticles.getMutableLiveData(api_url);
    }
}
