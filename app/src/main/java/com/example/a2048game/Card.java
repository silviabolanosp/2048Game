package com.example.a2048game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.text.DecimalFormat;


public class Card extends FrameLayout {
    private TextView label;

    public Card(Context context){
        super(context);
        label = new TextView(getContext());
        label.setTextSize(20);
        label.setTextColor(Color.parseColor("#000000"));
        label.setGravity(Gravity.CENTER);

        LayoutParams lp = (LayoutParams) new LayoutParams(-1,-1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);

        setNum(0);
    }

    private double num = 0;

    public double getNum(){
        return num;
    }

    public void setNum(double num){
        this.num = num;

        if (num <= 0){
            label.setText("");
        }else {
            label.setText("" + new DecimalFormat("#").format(num));
        }


        if(this.num == 1.1){
            label.setText("BLOCK");
            label.setBackgroundColor(Color.parseColor("#A3A3A3"));
        }

        if(this.num == 1.9){
            label.setText("BOMB");
            label.setBackgroundColor(Color.parseColor("#920202"));
        }

        if(this.num == 0){
            label.setBackgroundColor(Color.parseColor("#F0F3F4"));
        }
        if(this.num == 2){
            label.setBackgroundColor(Color.parseColor("#FFF59D"));
        }
        if(this.num == 4){
            label.setBackgroundColor(Color.parseColor("#C5E1A5"));
        }
        if(this.num == 8){
            label.setBackgroundColor(Color.parseColor("#80CBC4"));
        }
        if(this.num == 16){
            label.setBackgroundColor(Color.parseColor("#81D4FA"));
        }
        if(this.num == 32){
            label.setBackgroundColor(Color.parseColor("#9FA8DA"));
        }
        if(this.num == 64){
            label.setBackgroundColor(Color.parseColor("#CE93D8"));
        }
        if(this.num == 128){
            label.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        if(this.num == 256){
            label.setBackgroundColor(Color.parseColor("#8BC34A"));
        }
        if(this.num == 512){
            label.setBackgroundColor(Color.parseColor("#3F51B5"));
        }
        if(this.num == 1024){
            label.setBackgroundColor(Color.parseColor("#9C2780"));
        }
        if(this.num == 2048){
            label.setBackgroundColor(Color.parseColor("#F44336"));
        }



    }

    public boolean equals(Card o){
        return getNum() == o.getNum();
    }

}
