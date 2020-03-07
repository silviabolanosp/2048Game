package com.example.a2048game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;


public class GameView extends GridLayout {

    private Card[][] cardsMap = new Card[4][4];
    private Card[][] grid = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
    private double currentBestBlock = 0;
    Typeface font;
    String fontLabel;
    public GameView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);

        initGameView();
    }

    public GameView(Context context){
        super(context);

        initGameView();
}

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        addGrid();
        initGameView();
    }

    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(Color.parseColor("#35b5f9")); // fondo azul
    //#CACFD2   fondo gris
        setOnTouchListener(new OnTouchListener() {

            private float startX,startY,offsetX,offsetY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = motionEvent.getX() - startX;
                        offsetY = motionEvent.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                    swipeLeft();

                                }else if (offsetX > 5) {
                                    swipeRight();


                                }
                        }else{
                            if(offsetY<-5){
                                swipeUp();

                            }else if (offsetY > 5){
                                swipeDown();

                            }
                        }
                        break;
                }
                return true;
            }
        });

    }

    protected void onSizeChanged(int w,int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw,oldh);

        int cardWidth = (Math.min(w,h)-10)/4; //237

        addCards(cardWidth,cardWidth);

        startGame();

    }
    private void addGrid(){
/*        fontLabel = "fonts/Daddy.otf";
        this.font = Typeface.createFromAsset(getAssets(),fontLabel);*/
        Card c;

        for (int y= 0;y < 4;y++){
            for (int x = 0;x < 4; x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c,220,220);

                grid[x][y] = c;
            }
        }

    }
    private void addCards(int cardWidth,int cardHeight){

        Card c;

        for (int y= 0;y < 4;y++){
            for (int x = 0;x < 4; x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);

                cardsMap[x][y] = c;
            }
        }

    }

    private void startGame(){

        GameActivity.getGameActivity().clearScore();

        for (int y= 0;y<4;y++){
            for (int x= 0;x<4;x++){
                cardsMap[x][y].setNum(0);

            }
        }

        addRandomNum();
        addRandomNum();

    }

    private void addRandomNum(){

        emptyPoints.clear();

        for (int y = 0;y < 4;y++){
            for (int x = 0; x < 4;x++){
                if (cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }

        Point p =emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);

        checkHighestBlock();
        drawGrid();

    }


    private void swipeLeft(){

        boolean merge = false;

        for (int y =0 ;y < 4;y++){
            for (int x= 0;x<4;x++){

                for(int x1 = x + 1; x1 < 4;x1++){
                    if (cardsMap[x1][y].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x--;

                            merge = true;

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;

                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeRight(){

        boolean merge = false;

        for (int y =0 ;y < 4;y++){
            for (int x= 3;x >= 0;x--){

                for(int x1 = x - 1; x1 >= 0;x1--){
                    if (cardsMap[x1][y].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);


                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }

        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeUp(){

        boolean merge = false;

        for (int x =0 ;x < 4;x++){
            for (int y= 0;y<4;y++){

                for(int y1 = y + 1; y1 < 4;y1++){
                    if (cardsMap[x][y1].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y--;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);

                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeDown(){

        boolean merge = false;

        for (int x =0 ;x < 4;x++){
            for (int y= 3;y>=0;y--){

                for(int y1 = y - 1; y1 >= 0;y1--){
                    if (cardsMap[x][y1].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);

                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            merge= true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void drawGrid(){
        double num = 0;
        for (int y= 0;y < 4; y++){
            for (int x= 0; x < 4; x++){
                num = cardsMap[x][y].getNum();
                grid[x][y].setNum(num);
            }
        }
    }

    private void checkComplete(){

        boolean complete = true;

        ALL:
        for (int y= 0;y < 4; y++){
            for (int x= 0; x < 4; x++){
                if (cardsMap[x][y].getNum() == 0 ||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("Game Over").setMessage("Press button below to start again.").setPositiveButton("Play Again",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    GameActivity.getGameActivity().close();
                    startGame();

                }
            }).show();
        }

    }

    public void checkHighestBlock(){
        double possibleHighestBlock = 0;

        for(int x =0; x<4;x++){
            for(int y =0; y<4;y++){
                if (cardsMap[x][y].getNum() > possibleHighestBlock){
                    possibleHighestBlock = cardsMap[x][y].getNum();
                }
            }
        }

        if(possibleHighestBlock > currentBestBlock){
            currentBestBlock = possibleHighestBlock;

        }

        GameActivity.getGameActivity().showHighestBlock(possibleHighestBlock, getColorForBlock(possibleHighestBlock));

    }

    private int getColorForBlock(double score){
        int colorInt = 0;

        if(score == 1.1){
            return Color.parseColor("#A3A3A3");
        }

        if(score == 1.9){
            return Color.parseColor("#920202");
        }

        if(score == 0){
            return Color.parseColor("#F0F3F4");
        }
        if(score == 2){
            return Color.parseColor("#FFF59D");
        }
        if(score == 4){
            return Color.parseColor("#C5E1A5");
        }
        if(score == 8){
            return Color.parseColor("#80CBC4");
        }
        if(score == 16){
            return Color.parseColor("#81D4FA");
        }
        if(score == 32){
            return Color.parseColor("#9FA8DA");
        }
        if(score == 64){
            return Color.parseColor("#CE93D8");
        }
        if(score == 128){
            return Color.parseColor("#FFEB3B");
        }
        if(score == 256){
            return Color.parseColor("#8BC34A");
        }
        if(score == 512){
            return Color.parseColor("#3F51B5");
        }
        if(score == 1024){
            return Color.parseColor("#9C2780");
        }
        if(score == 2048){
            return Color.parseColor("#F44336");
        }

        return colorInt;
    }



}
