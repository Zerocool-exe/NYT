package myproject.nyt.interfaces;

import myproject.nyt.model.ArticleMdl;
import myproject.nyt.model.ArticleWrapper;
import myproject.nyt.model.SearchWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiService
{
    @GET("viewed/7.json?")
    public Call<ArticleWrapper> getPopularArticle(@Query("api-key") String apikey);
    @GET("shared/7.json?")
    public Call<ArticleWrapper> getSharedArticle(@Query("api-key") String apikey);
    @GET("emailed/7.json?")
    public Call<ArticleWrapper> getEmailedArticle(@Query("api-key") String apikey);
    @GET("articlesearch.json?")
    public Call<SearchWrapper> getSearchedArticle(@Query("q") String query,@Query("api-key") String apikey);



}
