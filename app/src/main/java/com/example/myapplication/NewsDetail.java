package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class NewsDetail extends AppCompatActivity {

    TextView view_title, view_content, view_date;
    ImageView imageView;
    JSONParser jsonParser;
    Button buttonEdit;
    JSONObject jsonObject;
    Bundle extras;
    String title;
    String content;
    String img_url;
    String date;
    String id, position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        prepareMe();
    }

    private void prepareMe() {

        view_title = findViewById(R.id.newsItemTitle_4detail);
        view_content = findViewById(R.id.newsItemContent_4detail);
        view_date = findViewById(R.id.newsItemTarih_4detail);
        imageView = findViewById(R.id.newsItemImage_4detail);
        extras = getIntent().getExtras();


        if(extras!=null){

            title = extras.getString("title");
            content = extras.getString("content");
            date = extras.getString("date");
            img_url = extras.getString("imageUrl");

            view_title.setText(extras.getString("title"));
            view_date.setText(extras.getString("date"));
            view_content.setText(extras.getString("content"));
            id = extras.getString("id");
            position = extras.getString("position");

            Picasso.with(this).load(MainActivity.newslist.get(Integer.parseInt(position)).getImg_url()).into(imageView);
        }

    }
}