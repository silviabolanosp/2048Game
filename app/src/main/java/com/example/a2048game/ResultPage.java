package com.example.a2048game;

import android.content.Intent;
import android.os.Bundle;;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ResultPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int result= getIntent().getExtras().getInt("Result");
        Button btn= findViewById(R.id.ResultBtn);
        TextView tv = findViewById(R.id.ResultTxt);
        if(result==0)
        {
            btn.setText("Go to Main Menu");
            tv.setText("CONGRATULATIONS! YOU WON!");
        }
        else
        {
            tv.setText("OOPS! YOU LOST!");
            btn.setText("Try Again");
        }
    }
    public void retry(View v)
    {
        Intent i= new Intent(ResultPage.this,MainActivity.class);
        startActivity(i);
    }
}
