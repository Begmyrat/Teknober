package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyListAdapter adapter;
    public static ArrayList<News> newslist;
    JSONParser jsonParser;
    JSONObject jsonObject;
    Button buttonAdd;
    TextView textview_title;
    int count=0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareMe();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                listViewItemLongClicked(position);

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToNewsDetail(position);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddClick(v);
            }
        });

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if(firstVisibleItem>count){
//                    count=firstVisibleItem;
//                    findViewById(R.id.layout_top_header).setVisibility(View.INVISIBLE);
//                }
//                else if(firstVisibleItem<count){
//                    count=firstVisibleItem;
//                    findViewById(R.id.layout_top_header).setVisibility(View.VISIBLE);
//                }
//            }
//        });

    }

    private void goToNewsDetail(int position){
        int id = newslist.get(position).getId();
        String title = newslist.get(position).getTitle();
        String content = newslist.get(position).getContent();
        String date = newslist.get(position).getDate();
        int readCount = newslist.get(position).getReadCount();
        String img_url = newslist.get(position).getImg_url();

        Intent intent = new Intent(MainActivity.this, NewsDetail.class);
        intent.putExtra("id", String.valueOf(id));
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("date", date);
        intent.putExtra("readCount", readCount);
        intent.putExtra("imageUrl", img_url);
        intent.putExtra("position", String.valueOf(position));
        startActivity(intent);
    }

    private void listViewItemLongClicked(final int position) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to make some changes on " + newslist.get(position).getTitle())
                .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNews(position);
                    }
                })
                .setNegativeButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editNews(position);
                    }
                })
                .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void editNews(int position){
        int id = newslist.get(position).getId();
        String title = newslist.get(position).getTitle();
        String content = newslist.get(position).getContent();
        String date = newslist.get(position).getDate();
        int readCount = newslist.get(position).getReadCount();
        String img_url = newslist.get(position).getImg_url();

        Intent intent = new Intent(MainActivity.this, EditNews.class);
        intent.putExtra("id", String.valueOf(id));
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("date", date);
        intent.putExtra("readCount", readCount);
        intent.putExtra("imgUrl", img_url);
        startActivity(intent);
    }

    private void deleteNews(final int position){
        final String url = "http://www.sehirkesfi.com/mobil/delete_news.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                int success = 0;
                try {

                    //Tüm objeyi yakalıyoruz

                    jsonData.put("id", String.valueOf(newslist.get(position).getId()));

                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    success = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("", ex.getMessage());
                    }
                }
                return String.valueOf(success);

            }

            @Override
            protected void onPostExecute(String res) {

                if (jsonObject != null && res.equals("1"))
                {
                    getNews();
                }
            }


        }.execute(null, null, null);
    }



    private void prepareMe() {
        listView = findViewById(R.id.listview);
        newslist = new ArrayList<>();
        adapter = new MyListAdapter(MainActivity.this, newslist);
        listView.setAdapter(adapter);
        buttonAdd = findViewById(R.id.button_add);
        textview_title = findViewById(R.id.title);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getNews();
    }

    private void getNews() {

        final String url = "http://www.sehirkesfi.com/mobil/get_news.php";

        newslist.clear();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                int success = 0;
                try {

                    //Tüm objeyi yakalıyoruz
                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    success = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("", ex.getMessage());
                    }
                }
                return String.valueOf(success);

            }

            @Override
            protected void onPostExecute(String res) {

                if (jsonObject != null && res.equals("1"))
                {
                    try
                    {
                        JSONArray posts = jsonObject.getJSONArray("pro");


                        for (int i=0 ; i< posts.length(); i++)
                        {
                            JSONObject gelen_post = posts.getJSONObject(i);


                            int newsID = gelen_post.getInt("id");
                            String newsTitle = gelen_post.getString("title");
                            String newsContent = gelen_post.getString("content");
                            String newsDate = gelen_post.getString("news_date");
                            int newsReadCount = gelen_post.getInt("read_count");
                            String newsImageURL = gelen_post.getString("image_url");

                            News news = new News();
                            news.setId(newsID);
                            news.setTitle(newsTitle);
                            news.setContent(newsContent);
                            news.setDate(newsDate);
                            news.setReadCount(newsReadCount);
                            news.setImg_url(newsImageURL);

                            newslist.add(news);

                            /*date = gelen_post.getString("UserMail");
                            date = gelen_post.getString("UserPassword");
                            date = gelen_post.getString("UserLoginDate");*/

                            adapter = new MyListAdapter(MainActivity.this, newslist);
                            listView.setAdapter(adapter);




                        }


                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();

                    }

//                    if(size!=0) {
//                        for (int i = 0; i < userlist.size(); i++) {
//                            boolean found = false;
//                            for (int j = 0; j < filteredUsers.size(); j++) {
//                                if (userlist.get(i).getId() == filteredUsers.get(j).getId())
//                                    found = true;
//                            }
//                            if (!found) {
//                                userlist.remove(i);
//                            }
//                        }
//                    }




                }
                else
                {
                    // ringProgressDialog.dismiss();

                }
            }


        }.execute(null, null, null);
    }


    public void buttonMenuClick(View view) {
    }

    public void buttonAddClick(View view) {

        Intent i = new Intent(this, AddNews.class);
        startActivity(i);

    }
}