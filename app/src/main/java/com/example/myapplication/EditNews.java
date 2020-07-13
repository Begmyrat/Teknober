package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class EditNews extends AppCompatActivity {

    EditText editTitle, editContent, editDate, editImageURL;
    JSONParser jsonParser;
    Button buttonEdit;
    JSONObject jsonObject;
    Bundle extras;
    String title;
    String content;
    String img_url;
    String date;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);

        prepareMe();

        if(buttonEdit!=null){
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editNews();
                    finish();
                }
            });
        }
    }

    private void editNews(){
        final String url = "http://www.sehirkesfi.com/mobil/update_news.php";

        title = editTitle.getText().toString();
        content = editContent.getText().toString();
        date = editDate.getText().toString();
        img_url = editImageURL.getText().toString();
        if(extras!=null)
            id = extras.getString("id");



        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                int success = 0;
                try {

                    jsonData.put("id", id);
                    jsonData.put("title", title);
                    jsonData.put("content", content);
                    jsonData.put("date", date);
                    jsonData.put("image_url", img_url);


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
            }


        }.execute(null, null, null);
    }

    private void prepareMe() {
        editTitle = findViewById(R.id.edittext_title_4edit);
        editContent = findViewById(R.id.edittext_content_4edit);
        editDate = findViewById(R.id.edittext_date_4edit);
        editImageURL = findViewById(R.id.edittext_imageURL_4edit);
        buttonEdit = findViewById(R.id.button_edit);
        jsonObject = new JSONObject();
        jsonParser = new JSONParser();
        extras = getIntent().getExtras();

        if(extras!=null){
            editTitle.setText(extras.getString("title"));
            editImageURL.setText(extras.getString("imgUrl"));
            editDate.setText(extras.getString("date"));
            editContent.setText(extras.getString("content"));
            id = extras.getString("id");
        }

    }


}