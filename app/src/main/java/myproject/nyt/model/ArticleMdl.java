package myproject.nyt.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleMdl
{
    @SerializedName("title")
    private String title;
    @SerializedName("published_date")
    private String pub_date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }
}
