package com.example.a2048game;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;


public class Card extends FrameLayout {
    private TextView label;
    private ImageView pic;

    private int idBomb = getResources().getIdentifier("res:drawable/bomb", null, null);
    private int idBrick = getResources().getIdentifier("res:drawable/brick", null, null);



    public Card(Context context){
        super(context);

        label = new TextView(getContext());
        label.setTextSize(20);
        label.setTextColor(Color.parseColor("#000000"));
        label.setGravity(Gravity.CENTER);

        pic = new ImageView(getContext());
        pic.setMaxWidth(30);
        pic.setMaxHeight(30);
        pic.setVisibility(ImageView.GONE);

        LayoutParams lp = (LayoutParams) new LayoutParams(-1,-1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);
        addView(pic, lp);

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
            pic.setVisibility(ImageView.GONE);
            setBackgroundColor(Color.parseColor("#CACFD2"));
        }else {
            label.setText("" + new DecimalFormat("#").format(num));
        }


        if(this.num == 1.1){
            //label.setText("BLOCK");
            label.setVisibility(INVISIBLE);
            pic.setImageDrawable(getResources().getDrawable(R.drawable.brick));
            pic.setVisibility(ImageView.VISIBLE);
            label.setBackgroundColor(Color.parseColor("#A3A3A3"));
        }

        if(this.num == 1.9){
            //label.setText("BOMB");
            label.setVisibility(INVISIBLE);
            pic.setImageDrawable(getResources().getDrawable(R.drawable.bomb1));
            pic.setVisibility(ImageView.VISIBLE);

            label.setBackgroundColor(Color.parseColor("#920202"));
        }

        if(this.num == 0){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#F0F3F4"));
        }
        if(this.num == 2){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#917EE5"));
        }
        if(this.num == 4){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#34B1D7"));
        }
        if(this.num == 8){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#79F2AF"));
        }
        if(this.num == 16){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#DAF7A6"));
        }
        if(this.num == 32){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#FFF633"));
        }
        if(this.num == 64){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#F2D879"));
        }
        if(this.num == 128){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#FFC300"));
        }
        if(this.num == 256){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#FF5733"));
        }
        if(this.num == 512){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#C70039"));
        }
        if(this.num == 1024){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#C70077"));
        }
        if(this.num == 2048){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#6E00C7"));
        }
        if(this.num == 4096){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#006EC7"));
        }
        if(this.num == 8192){
            label.setVisibility(VISIBLE);
            pic.setVisibility(ImageView.INVISIBLE);
            label.setBackgroundColor(Color.parseColor("#0C9376"));
        }


    }

    public boolean equals(Card o){
        return getNum() == o.getNum();
    }

}
