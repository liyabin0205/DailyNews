package com.app.dailynews;

import loopj.android.image.SmartImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

	TextView text_news,title_news,time_news;
	SmartImageView image_news;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        text_news = (TextView)findViewById(R.id.text_news);
        time_news = (TextView)findViewById(R.id.time_news);
        title_news = (TextView)findViewById(R.id.title_news);
        image_news = (SmartImageView)findViewById(R.id.image_news);
        
        Intent intent = getIntent();
        int position = intent.getIntExtra("extra",0);
        String detailnews = intent.getStringExtra("extra1");
        String titlenews = intent.getStringExtra("extra2");
        String timenews = intent.getStringExtra("extra4");
        String url = intent.getStringExtra("extra3");

        text_news.setText(detailnews);
        title_news.setText(titlenews);
        time_news.setText(timenews);
        image_news.setImageUrl(url);

        
    }
}
