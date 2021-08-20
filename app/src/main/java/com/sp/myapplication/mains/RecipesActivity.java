package com.sp.myapplication.mains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.sp.myapplication.R;
import com.sp.myapplication.home.HomeActivity;
import com.sp.myapplication.menu.Menu_PreviousOrder;

public class RecipesActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        webView = findViewById(R.id.WebView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://eatbook.sg/");

        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())webView.goBack();
        else
        super.onBackPressed();
    }
}