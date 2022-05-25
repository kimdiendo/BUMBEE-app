package com.example.bumbee.activities.Reading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bumbee.R;
import com.example.bumbee.activities.utilities.PreferenceManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class news extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //News API key: 1cb319e41b27436bba5853514331db98
            setContentView(R.layout.activity_news);
            RecyclerView news = findViewById(R.id.idNews);
            RecyclerView category = findViewById(R.id.idCategory);
            articlesArrayList = new ArrayList<>();
            categoryModelArrayList = new ArrayList<>();
            newsAdapter = new NewsAdapter(articlesArrayList, this);
            categoryAdapter = new CategoryAdapter(categoryModelArrayList, this, this);
            news.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            news.setAdapter(newsAdapter);
            category.setAdapter(categoryAdapter);
            getCategories();
            getNews("ALL");
            newsAdapter.notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void getCategories()
    {
        categoryModelArrayList.add(new CategoryModel("ALL","https://images.unsplash.com/photo-1610926950565-29e4728c070b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80"));
        categoryModelArrayList.add(new CategoryModel("Technology","https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
        categoryModelArrayList.add(new CategoryModel("Science","https://images.unsplash.com/photo-1518152006812-edab29b069ac?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
        categoryModelArrayList.add(new CategoryModel("Sports","https://images.unsplash.com/photo-1579952363873-27f3bade9f55?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80"));
        categoryModelArrayList.add(new CategoryModel("General","https://images.unsplash.com/photo-1432821596592-e2c18b78144f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
        categoryModelArrayList.add(new CategoryModel("Business","https://images.unsplash.com/photo-1600880292203-757bb62b4baf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
        categoryModelArrayList.add(new CategoryModel("Entertainment","https://images.unsplash.com/photo-1603190287605-e6ade32fa852?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
        categoryModelArrayList.add(new CategoryModel("Health","https://images.unsplash.com/photo-1505576399279-565b52d4ac71?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryAdapter.notifyDataSetChanged();
    }
    private void getNews(String category)
    {
        articlesArrayList.clear();
        String categoryURL ="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=1cb319e41b27436bba5853514331db98";
        String URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=1cb319e41b27436bba5853514331db98";
        String BASE_URL="https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPi retrofitAPi = retrofit.create(RetrofitAPi.class);
        Call<NewsModel> call;
        if(category.equals("ALL"))
        {
            call = retrofitAPi.getAllNews(URL);
        }else
        {
            call = retrofitAPi.getNewsByCategory(categoryURL);
        }
        call.enqueue(new Callback<NewsModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<NewsModel> call, @NonNull Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                ArrayList<Articles> articles = Objects.requireNonNull(newsModel).getArticles();
                for(int i = 0 ; i < articles.size();i++)
                {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(), articles.get(i).getUrl(),articles.get(i).getContent(),
                            articles.get(i).getUrltoimage()));
                }
                newsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"Unsuccessfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryModelArrayList.get(position).getCategory();
        getNews(category);
    }
}