package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailRequetAdoptActivity extends AppCompatActivity {
    Button rejectBtn;
    Toolbar arrowBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_request_adopt);
        rejectBtn = findViewById(R.id.rejectBtn);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptStatusActivity.class));
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(DetailRequetAdoptActivity.this).inflate(R.layout.detail_request_adopt_reject_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRequetAdoptActivity.this);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                dialog.show();
                TextView closeButton = dialogView.findViewById(R.id.closeRejectAadoptBtn);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the dialog when the "Close" button is clicked
                        dialog.dismiss();
                    }
                });
            }
        });


    }
}
