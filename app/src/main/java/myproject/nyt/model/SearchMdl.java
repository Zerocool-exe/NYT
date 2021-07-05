package myproject.nyt.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchMdl
{
    @SerializedName("docs")
    private List<DocData> docResults;

    public class DocData
    {
        @SerializedName("abstract")
        private String mAbstract;
        @SerializedName("pub_date")
        private String pub_date;

        public String getmAbstract() {
            return mAbstract;
        }

        public void setmAbstract(String mAbstract) {
            this.mAbstract = mAbstract;
        }

        public String getPub_date() {
            return pub_date;
        }

        public void setPub_date(String pub_date) {
            this.pub_date = pub_date;
        }
    }

    public List<DocData> getDocResults() {
        return docResults;
    }

    public void setDocResults(List<DocData> docResults) {
        this.docResults = docResults;
    }
}
