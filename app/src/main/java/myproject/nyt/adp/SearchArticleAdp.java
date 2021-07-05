package myproject.nyt.adp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import myproject.nyt.R;
import myproject.nyt.model.ArticleMdl;
import myproject.nyt.model.SearchMdl;
import myproject.nyt.model.SearchWrapper;

public class SearchArticleAdp extends RecyclerView.Adapter<BaseViewHolder>
{
    private static final String TAG = "SearchArticleAdapter";
    private List<SearchMdl.DocData> searchMdls;
    public SearchArticleAdp(List<SearchMdl.DocData> searchMdlList)
    {
        searchMdls = searchMdlList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_articles, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (searchMdls != null && searchMdls.size() > 0) {
            return searchMdls.size();
        } else {
            return 0;
        }
    }
    public class ViewHolder extends BaseViewHolder
    {
        TextView txtTitle,txtPubDate;
        public ViewHolder(View itemView)
        {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtArticleTitle);
            txtPubDate = itemView.findViewById(R.id.txtPublishDate);
        }
        protected void clear() {
            txtTitle.setText("");
            txtPubDate.setText("");
        }
        public void onBind(int position) {
            super.onBind(position);
            //final SearchMdl searchMdl = searchMdls.get(position);
            // System.out.println("medialist=="+articleMdl.getMediaDataList().get(0).getMedia_metaDataList().get(2).getUrl());

            if (searchMdls != null) {
                txtTitle.setText(searchMdls.get(position).getmAbstract());
                txtPubDate.setText(searchMdls.get(position).getPub_date());
            }
        }
    }
}
