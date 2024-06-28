package com.example.pet_finder_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pet_finder_app.Class.Recognition;

import java.io.IOException;
import java.util.ArrayList;

public class DogBreedRecActivity extends AppCompatActivity {
    private final int IMAGE_PICK = 100;
    ImageView imageView;
    Bitmap bitmap;
    DetectorActivity detectorActivity;
    Paint boxPaint = new Paint();
    Paint textPain = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dectection);

        imageView = findViewById(R.id.imageView);

        detectorActivity = new DetectorActivity();
        detectorActivity.setModelFile("model.tflite");
        detectorActivity.initialModel(this);

        boxPaint.setStrokeWidth(5);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setColor(Color.RED);

        textPain.setTextSize(50);
        textPain.setColor(Color.GREEN);
        textPain.setStyle(Paint.Style.FILL);
    }

    public void selectImage(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK);
    }
    float maxConf = 0.0f;
    String highestConfLabel = "";
    public void predict(View view){
        ArrayList<Recognition> recognitions =  detectorActivity.detect(bitmap);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);

        for(Recognition recognition: recognitions){

            if(recognition.getConfidence() > 0.7 && recognition.getConfidence() > maxConf){
                maxConf = recognition.getConfidence();
                highestConfLabel = recognition.getLabelName();
                RectF location = recognition.getLocation();
                canvas.drawRect(location, boxPaint);
                canvas.drawText(recognition.getLabelName() + ":" + recognition.getConfidence(), location.left, location.top, textPain);
            }

        }

        Log.d("Predict result",highestConfLabel);
        imageView.setImageBitmap(mutableBitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_PICK && data != null){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
