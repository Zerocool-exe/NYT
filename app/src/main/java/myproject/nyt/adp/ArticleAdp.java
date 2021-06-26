package myproject.nyt.adp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import myproject.nyt.R;
import myproject.nyt.model.ArticleMdl;
import myproject.nyt.util.Config;

public class ArticleAdp extends BaseAdapter {
    private LayoutInflater inflater;
    Context ctx;
    Config config = null;
    ArrayList<ArticleMdl> dataArticles = null;
    ArrayList<ArticleMdl> dataArticlesList = null;

    public ArticleAdp(Context context, ArrayList<ArticleMdl> dataArticles) {
        inflater = LayoutInflater.from(context);
        this.ctx = context;
        this.config = new Config(ctx);
        this.dataArticles = new ArrayList<>();
        this.dataArticles.clear();
        this.dataArticles = dataArticles;
        this.dataArticlesList = new ArrayList<ArticleMdl>();
        this.dataArticlesList.addAll(dataArticles);
    }

    @Override
    public int getCount() {
        return dataArticles.size();
    }

    @Override
    public Object getItem(int position) {
        return dataArticles.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_articles, parent, false);
            holder.txtArticleTitle = convertView.findViewById(R.id.txtArticleTitle);
            holder.txtPublishDate = convertView.findViewById(R.id.txtPublishDate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtArticleTitle.setText(dataArticles.get(position).getTitle());
        holder.txtPublishDate.setText(dataArticles.get(position).getPub_date());

        return convertView;
    }

    class ViewHolder {
        TextView txtArticleTitle, txtPublishDate;
    }
}