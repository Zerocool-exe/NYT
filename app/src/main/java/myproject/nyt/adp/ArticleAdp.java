package myproject.nyt.adp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import myproject.nyt.R;
import myproject.nyt.model.ArticleMdl;
import myproject.nyt.model.SearchMdl;
import myproject.nyt.util.Config;

public class ArticleAdp extends RecyclerView.Adapter<BaseViewHolder>
{
    private static final String TAG = "ArticleAdapter";
    private List<ArticleMdl> articleMdls;
    public ArticleAdp(List<ArticleMdl> articleMdlList)
    {
        articleMdls = articleMdlList;
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
        if (articleMdls != null && articleMdls.size() > 0) {
            return articleMdls.size();
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
            final ArticleMdl articleMdl = articleMdls.get(position);
            // System.out.println("medialist=="+articleMdl.getMediaDataList().get(0).getMedia_metaDataList().get(2).getUrl());

            if (articleMdl.getTitle() != null) {
                txtTitle.setText(articleMdl.getTitle());
            }
            if (articleMdl.getPub_date() != null) {
                txtPubDate.setText(articleMdl.getPub_date());
            }
        }
    }
}