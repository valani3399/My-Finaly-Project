package com.example.softices.pankajtrainee.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;

public class DialogBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_box);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        openDialog();
    }

    private void openDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("HELOO");
        alertDialog.setMessage("I am Pankaj ");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 200;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);

    }
}
