package com.btp.chealth.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btp.chealth.R;
import com.btp.chealth.adapters.NewsAdapter;
import com.btp.chealth.data.Article;
import com.btp.chealth.viewmodels.NewsViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragment extends Fragment {

    @BindView(R.id.articleList)
    RecyclerView articleList;

    private ArrayList<Article> articles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsViewModel newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        articleList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        articles = new ArrayList<>();
        NewsAdapter newsAdapter = new NewsAdapter(articles);
        articleList.setAdapter(newsAdapter);

        newsViewModel.getNewsList().observe(getViewLifecycleOwner(), articleList -> {
            if(articles != null) {
                articles.clear();
                articles.addAll(articleList);
                newsAdapter.notifyDataSetChanged();
            }
        });
    }
}
