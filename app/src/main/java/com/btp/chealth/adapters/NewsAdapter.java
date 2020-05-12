package com.btp.chealth.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btp.chealth.R;
import com.btp.chealth.data.Article;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private ArrayList<Article> dataset;

    public NewsAdapter(ArrayList<Article> dataset) {
        this.dataset = dataset;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Article article = dataset.get(position);
        Glide.with(holder.image.getContext()).load(article.getUrlToImage()).into(holder.image);
        holder.openFullArticle.setOnClickListener(view -> {
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
            holder.openFullArticle.getContext().startActivity(browser);
        });
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.articleImage)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.openFullArticle)
        Button openFullArticle;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
