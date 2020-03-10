package com.example.a2048game;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;


public class Tutorial extends AppCompatActivity {
    Button btnBack;
    ConstraintLayout layout;
    TextView title;
/*    private ImageSwitcher imageSwitcher;
    private int[] galeria = {R.drawable.tutorial1, R.drawable.tutorial2, R.drawable.tutorial3, R.drawable.tutorial4};
    private int posicion;
    private static final int DURACION = 9000;
    private Timer timer = null;*/
    Bundle mode;
    boolean darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);
        layout = findViewById(R.id.tutorial);
        mode = getIntent().getExtras();
        title = findViewById(R.id.titleTutorial);
        darkMode = mode.getBoolean("mode");
        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);
        changeMode();

 /*       imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory()
        {
            public View makeView()
            {
                ImageView imageView = new ImageView(Tutorial.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                return imageView;
            }
        });

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);


        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        imageSwitcher.setImageResource(galeria[posicion]);
                        posicion++;
                        if (posicion == galeria.length)
                            posicion = 0;
                    }
                });
            }
        }, 0, DURACION);
*/


        btnBack = findViewById(R.id.btnCancel);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextView = new Intent(Tutorial.this,MainActivity.class);
                Bundle userName = getIntent().getExtras();
                String user = userName.getString("user");
                nextView.putExtra("userName", user);
                nextView.putExtra("mode",darkMode);
                startActivity(nextView);
            }
        });

    }

    private void changeMode() {
        if (darkMode == true){
            layout.setBackgroundColor(Color.parseColor("#424242"));
            title.setTextColor(Color.parseColor("#FBFEF9"));
        } else {
            layout.setBackgroundColor(Color.parseColor("#F7F7EA"));
            title.setTextColor(Color.parseColor("#35B4F9"));
        }
    }


}
