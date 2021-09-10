package com.example.consultingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.consultingapp.R;

public class ContactUsActivity extends AppCompatActivity {
    private ImageView facebbok,youtube,www;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        facebbok = findViewById(R.id.face);
        www = findViewById(R.id.www);
        youtube = findViewById(R.id.youtube);

        facebbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("https://www.facebook.com/aggricus/");
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("https://www.youtube.com/channel/UCELiTNvvK7_Y3mivPAIemzA");
            }
        });
        www.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("https://aggricus.com/");
            }
        });


    }
    private void goTo(String add)
    {
        Uri uri = Uri.parse(add);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
