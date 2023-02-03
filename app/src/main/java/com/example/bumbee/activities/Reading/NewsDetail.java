package com.example.bumbee.activities.Reading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bumbee.R;
import com.squareup.picasso.Picasso;

public class NewsDetail extends AppCompatActivity {
    String title , desc , content , imageURL , url;
    private TextView titleTV  ,subtitle,  contentTV;
    private ImageView imageNews;
    private Button readNewsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");
        titleTV = findViewById(R.id.Title);
        subtitle = findViewById(R.id.subtitle);
        contentTV = findViewById(R.id.Content);
        imageNews = findViewById(R.id.imageURL);
        readNewsBtn = findViewById(R.id.btnReadFullNews);
        titleTV.setText(title);
        subtitle.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageURL).into(imageNews);
        readNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
