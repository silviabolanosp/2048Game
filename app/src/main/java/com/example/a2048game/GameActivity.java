package com.example.a2048game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.*;
public class GameActivity extends AppCompatActivity{

    HashMap<String, Integer> map= null;
    HashMap<String, Integer> prevmap= null;
    GridView grid=null;

    public GameActivity()
        {
            map=new HashMap<>();
        }
        int score=0;
        TextView t=null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);
            t=(TextView) findViewById(R.id.Score);
            grid=(GridView)findViewById(R.id.gamebackground);
            Random random1=new Random();
            int r1 = random1.nextInt(16-1+1)+1;
            Random random2=new Random();
            int r2 = random2.nextInt(16-1+1)+1;
            if(r1==r2 && r2!=16)
            {
                r2++;
            }
            else
            {
                if(r1==r2)
                {
                    r2--;
                }
            }


            String[] nums = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
            Random r3 = new Random();
            Random r4 = new Random();
            int[] choice={2,4};
            int ind1=r3.nextInt(choice.length);
            int ind2=r3.nextInt(choice.length);

        for(int i=0 ;i<nums.length;i++)
        {
            if(Integer.parseInt(nums[i])==r1)
            {
                map.put(nums[i],choice[ind1]);
            }
            if(Integer.parseInt(nums[i])==r2)
            {
                map.put(nums[i],choice[ind2]);
            }
            if(Integer.parseInt(nums[i])!=r2 && Integer.parseInt(nums[i])!=r1)
            {
                map.put(nums[i],0);
            }
        }

        final List<String> plantsList = new ArrayList<String>(Arrays.asList(nums));
        gridChanged(plantsList);

        grid.setOnTouchListener(new OnSwipeTouchListener(GameActivity.this) {

            public void onSwipeTop() {
                prevmap = new HashMap<>(map);
                int k=0;

                while(!(allOkTop(1) && allOkTop(2) && allOkTop(3) && allOkTop(4))) {
                    k=1;
                    for (int i = 16; i > 4; i--) {
                        int j = i;
                        if (j > 4 && map.get(j + "") == map.get((j - 4) + "")) {

                            map.put((j - 4) + "", map.get(j + "") + map.get((j - 4) + ""));
                            map.put(j + "", 0);
                            String score=t.getText().toString();
                            int scorenumber=Integer.parseInt(score);
                            scorenumber+=map.get((j-4)+"");
                            t.setText(scorenumber+"");

                        } else if (j > 4 && map.get((j - 4) + "") == 0) {

                            map.put((j - 4) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }

                    }
                }
                gridChanged(plantsList);
                generateRandomNumber();
                checkSwipe();
            }

            public void onSwipeRight() {
                prevmap=new HashMap<>(map);
                int k=0;
                int l=0;
                while(l==0 || !(allOkRight(4) && allOkRight(8) && allOkRight(12) && allOkRight(16))) {
                    l=1;
                    for (int i = 1; i < 17; i++) {
                        int j = i;

                        if (k == 0 && map.get(j + "") == map.get((j + 1) + "")) {
                            map.put((j + 1) + "", map.get(j + "") + map.get((j + 1) + ""));
                            map.put(j + "", 0);
                            String score=t.getText().toString();
                            int scorenumber=Integer.parseInt(score);
                            scorenumber+=map.get((j+1)+"");
                            t.setText(scorenumber+"");

                        } else if (k == 0 && map.get((j + 1) + "") == 0) {
                            map.put((j + 1) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                        if ((j / 4) != (j + 1) / 4) {
                            k = 1;
                        } else {
                            k = 0;
                        }
                    }
                }
                gridChanged(plantsList);
                generateRandomNumber();
                checkSwipe();
            }

            public void onSwipeLeft() {
                prevmap=new HashMap<>(map);
                int k=0;
                int l=0;
                while(l==0 || !(allOkLeft(1) && allOkLeft(5) && allOkLeft(9) && allOkLeft(13))) {
                    l=1;
                    for (int i = 16; i > 1; i--) {
                        int j = i;
                        if (j == 13 || j == 9 || j == 5) {
                            k = 1;
                        } else {
                            k = 0;
                        }
                        if (k == 0 && map.get(j + "") == map.get((j - 1) + "")) {
                            map.put((j - 1) + "", map.get(j + "") + map.get((j - 1) + ""));
                            map.put(j + "", 0);
                            String score=t.getText().toString();
                            int scorenumber=Integer.parseInt(score);
                            scorenumber+=map.get((j-1)+"");
                            t.setText(scorenumber+"");

                        } else if (k == 0 && map.get((j - 1) + "") == 0) {
                            map.put((j - 1) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }

                    }
                }
                gridChanged(plantsList);
                generateRandomNumber();
                checkSwipe();
            }

            public void onSwipeBottom() {
                prevmap=new HashMap<>(map);

                int k=0;
                while (k==0 || !(allOkBottom(13) && allOkBottom(14) && allOkBottom(15) && allOkBottom(16))) {
                    k=1;
                    for (int i = 1; i < 13; i++) {
                        int j = i;

                        if (j < 13 && map.get(j + "") == map.get((j + 4) + "")) {
                            map.put((j + 4) + "", map.get(j + "") + map.get((j + 4) + ""));
                            map.put(j + "", 0);
                            String score=t.getText().toString();
                            int scorenumber=Integer.parseInt(score);
                            scorenumber+=map.get((j+4)+"");
                            t.setText(scorenumber+"");

                        } else if (j < 13 && map.get((j + 4) + "") == 0) {
                            map.put((j + 4) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                    }
                }

                gridChanged(plantsList);
                generateRandomNumber();
                checkSwipe();
            }

        });

        Button btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

                builder.setTitle("¿Terminar partida?");

                builder.setMessage("¿Desea terminar partida?");

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                endGame();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;

                            case DialogInterface.BUTTON_NEUTRAL:

                                break;
                        }
                    }
                };

                builder.setPositiveButton("Si", dialogClickListener);

                builder.setNegativeButton("No",dialogClickListener);

                builder.setNeutralButton("Cancelar", dialogClickListener);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void gridChanged(List<String> plantsList) {
        final List<String> l=plantsList;
        grid.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, l){
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position,convertView,parent);

                TextView tv = (TextView) view;

                RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
                );
                tv.setLayoutParams(lp);
                tv.setWidth(100);
                tv.setHeight(210);

                tv.setGravity(Gravity.CENTER);

                tv.setTextSize(20);

                if(map.get(l.get(position))!=0)
                {
                    tv.setText(map.get(l.get(position))+"");
                }
                else
                {
                    tv.setText("");
                }
                tv.setId(position);

                if(map.get(l.get(position)) == 0){
                    tv.setBackgroundColor(Color.parseColor("#F0F3F4"));
                }
                if(map.get(l.get(position)) == 2){
                    tv.setBackgroundColor(Color.parseColor("#FFF59D"));
                }
                if(map.get(l.get(position)) == 4){
                    tv.setBackgroundColor(Color.parseColor("#C5E1A5"));
                }
                if(map.get(l.get(position)) == 8){
                    tv.setBackgroundColor(Color.parseColor("#80CBC4"));
                }
                if(map.get(l.get(position)) == 16){
                    tv.setBackgroundColor(Color.parseColor("#81D4FA"));
                }
                if(map.get(l.get(position)) == 32){
                    tv.setBackgroundColor(Color.parseColor("#9FA8DA"));
                }
                if(map.get(l.get(position)) == 64){
                    tv.setBackgroundColor(Color.parseColor("#CE93D8"));
                }
                if(map.get(l.get(position)) == 128){
                    tv.setBackgroundColor(Color.parseColor("#FFEB3B"));
                }
                if(map.get(l.get(position)) == 256){
                    tv.setBackgroundColor(Color.parseColor("#8BC34A"));
                }
                if(map.get(l.get(position)) == 512){
                    tv.setBackgroundColor(Color.parseColor("#3F51B5"));
                }
                if(map.get(l.get(position)) == 1024){
                    tv.setBackgroundColor(Color.parseColor("#9C2780"));
                }
                if(map.get(l.get(position)) == 2048){
                    tv.setBackgroundColor(Color.parseColor("#F44336"));
                }

                return tv;
            }
        });
    }

    //countdowntimer is an abstract class, so extend it and fill in methods
    public class MyCount extends CountDownTimer{
        TextView timer = (TextView) findViewById(R.id.timer);

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            timer.setText("done!");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText("Left: " + millisUntilFinished/1000);

        }

    }


    public boolean allOkTop(int loc)
    {
        if(map.get(loc+"")==0 && map.get((loc+4)+"")==0 && map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0)
        {
            return true;
        }
        else if(map.get((loc+4)+"")==0 && map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0 && map.get(""+(loc+4))!=0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+12))==0 && map.get(""+(loc+4))!=0 && map.get(""+loc)!=0 && map.get(""+(loc+8))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc+4)+"")!=0 && map.get(""+(loc+8))!=0 && map.get(""+(loc+12))!=0)
        {
            return true;
        }
        return false;
    }

    private boolean allOkRight(int loc) {
        if(map.get(loc+"")==0 && map.get((loc-1)+"")==0 && map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0)
        {
            return true;
        }
        else if(map.get((loc-1)+"")==0 && map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0 && map.get(""+(loc-1))!=0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-3))==0 && map.get(""+(loc-1))!=0 && map.get(""+loc)!=0 && map.get(""+(loc-2))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc-1)+"")!=0 && map.get(""+(loc-2))!=0 && map.get(""+(loc-3))!=0)
        {
            return true;
        }
        return false;
    }

    private boolean allOkLeft(int loc) {
        if(map.get(loc+"")==0 && map.get((loc+1)+"")==0 && map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0)
        {
            return true;
        }
        else if(map.get((loc+1)+"")==0 && map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0 && map.get(""+(loc+1))!=0 && map.get(""+(loc))!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+3))==0 && map.get(""+(loc+1))!=0 && map.get(""+(loc))!=0 && map.get(""+(loc+2))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc+1)+"")!=0 && map.get(""+(loc+2))!=0 && map.get(""+(loc+3))!=0)
        {
            return true;
        }
        return false;
    }

    private boolean allOkBottom(int loc) {
        if(map.get(loc+"")==0 && map.get((loc-4)+"")==0 && map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0)
        {
            return true;
        }
        else if(map.get((loc-4)+"")==0 && map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0 && map.get(""+(loc-4))!=0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-12))==0 && map.get(""+(loc-4))!=0 && map.get(""+loc)!=0 && map.get(""+(loc-8))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc-4)+"")!=0 && map.get(""+(loc-8))!=0 && map.get(""+(loc-12))!=0)
        {
            return true;
        }
        return false;
    }

    public void generateRandomNumber()
    {
        Random ran = new Random();
        int i=ran.nextInt(16)+1;
        int flag=0;
        for(String key: map.keySet())
        {
            if(map.get(key)!=0)
            {
                flag++;
            }
        }
        if(flag!=16)
        {
            while(map.get(i+"")!=0)
            {
                i=ran.nextInt(16)+1;
            }
            int[] choice={2,4};
            int ind1=ran.nextInt(choice.length);
            map.put(i+"",choice[ind1]);
            String[] nums = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
            List<String> plantsList=Arrays.asList(nums);

            gridChanged(plantsList);
        }

    }

    public void checkSwipe()
    {
        ConstraintLayout cl=(ConstraintLayout) findViewById(R.id.wholelayout);
        LinearLayout lll= new LinearLayout(this);
        lll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lll.setBackgroundColor(Color.parseColor("#9E9E9E"));
        lll.setGravity(Gravity.CENTER);
        TextView result= new TextView(this);
        for(String key : map.keySet())
        {
            if(map.get(key)==2048)
            {
//                result.setText("CONGRATULATIONS! YOU WIN!");
//                result.setGravity(Gravity.CENTER);
//                lll.addView(result);
//                cl.addView(lll);
//                Intent i= new Intent(GameActivity.this,ResultPage.class);
//                i.putExtra("Result",0);
//                startActivity(i);
//                return;
            }
        }
        Log.e("MAP",map.toString());
        Log.e("PREVMAP",prevmap.toString());
        for(String key : map.keySet())
        {
            if(map.get(key)!=prevmap.get(key))
            {

                return;
            }
        }

        result.setText("Perdiste la partida!");
        result.setGravity(Gravity.CENTER);
        lll.addView(result);
        cl.addView(lll);
        Intent i= new Intent(GameActivity.this,ResultPage.class);
        i.putExtra("Result",1);
        startActivity(i);

    }


    public void endGame()
    {
        ConstraintLayout cl=(ConstraintLayout) findViewById(R.id.wholelayout);
        LinearLayout lll= new LinearLayout(this);
        lll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lll.setBackgroundColor(getResources().getColor(R.color.translucent));
        lll.setGravity(Gravity.CENTER);
        TextView result= new TextView(this);


        result.setText("Partida terminada");
        result.setGravity(Gravity.CENTER);
        lll.addView(result);
        cl.addView(lll);
        Intent i= new Intent(GameActivity.this,ResultPage.class);
        i.putExtra("Result",1);
        startActivity(i);

    }
}
