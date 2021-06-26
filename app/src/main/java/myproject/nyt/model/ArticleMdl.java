package myproject.nyt.model;

public class ArticleMdl
{
    String title,pub_date;

    public ArticleMdl(String title, String pub_date) {
        this.title = title;
        this.pub_date = pub_date;
    }

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
