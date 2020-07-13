package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class AddNews extends AppCompatActivity {

    JSONParser jsonParser;
    JSONObject jsonObject;
    EditText editText_title, editText_content, editText_date, editText_imgURL;
    Button button_publish;
    String title,content,img_url, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        prepareMe();

        button_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_content!=null && editText_date!=null && editText_imgURL!=null && editText_title!=null) {
                    addNews();
                    finish();
                }
                else{
                    Toast.makeText(AddNews.this, "Doldurulmadi...", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void prepareMe() {
        editText_content = findViewById(R.id.edittext_content_4add);
        editText_date = findViewById(R.id.edittext_date_4add);
        editText_imgURL = findViewById(R.id.edittext_imageURL_4add);
        editText_title = findViewById(R.id.edittext_title_4add);
        button_publish = findViewById(R.id.button_publish_4add);
    }

    private void addNews(){

        final String url = "http://www.sehirkesfi.com/mobil/insert_news.php";

        title = editText_title.getText().toString();
        content = editText_content.getText().toString();
        date = editText_date.getText().toString();
        img_url = editText_imgURL.getText().toString();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                int success = 0;
                try {




                    jsonData.put("title", title);
                    jsonData.put("content", content);
                    jsonData.put("news_date", date);
                    jsonData.put("image_url", img_url);

                    News news = new News();

                    news.setTitle(title);
                    news.setContent(content);
                    news.setDate(date);
                    news.setImg_url(img_url);

                    MainActivity.newslist.add(news);

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
                    Log.e("Mesaj", "Successful");
                }
                else
                {
                    // ringProgressDialog.dismiss();

                }
            }


        }.execute(null, null, null);


    }

}