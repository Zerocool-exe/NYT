package myproject.nyt.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleWrapper
{
    @SerializedName("status")
    private String mStatus;
    @SerializedName("results")
    private List<ArticleMdl> dataResults;

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public List<ArticleMdl> getDataResults() {
        return dataResults;
    }

    public void setDataResults(List<ArticleMdl> dataResults) {
        this.dataResults = dataResults;
    }
}
