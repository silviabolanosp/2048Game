package com.example.a2048game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.widget.TextView;


public class MyCount extends CountDownTimer {
    TextView timer = (TextView) findViewById(R.id.timer);

    public MyCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {
        timer.setText("0!");
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("Se agotó el tiempo");

        builder.setMessage("¿Desea ir al menú?");

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        close();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        close();
                        break;

                    case DialogInterface.BUTTON_NEUTRAL:
                        close();
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

    @Override
    public void onTick(long millisUntilFinished) {
        timer.setText("" + millisUntilFinished/1000);

    }

}