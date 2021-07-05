package myproject.nyt.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchWrapper
{
    @SerializedName("status")
    private String mStatus;
    @SerializedName("response")
    private SearchMdl responseResults;

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public SearchMdl getResponseResults() {
        return responseResults;
    }

    public void setResponseResults(SearchMdl responseResults) {
        this.responseResults = responseResults;
    }
}
