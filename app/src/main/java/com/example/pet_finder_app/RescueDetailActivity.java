package com.example.pet_finder_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RescueDetailActivity extends AppCompatActivity {
    Toolbar arrowBack;
    TextView edtName, address_dis;
    OkHttpClient client;
    private ImageView image_view;
    String origin = "10.8700,106.8031";
    String url = "";
    DatabaseReference dtbRef = FirebaseDatabase.getInstance().getReference().child("RescueStation");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rescue_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rescue_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        client = new OkHttpClient();
        arrowBack = findViewById(R.id.toolbarArrowBack);
        edtName = findViewById(R.id.rescue_title);
        address_dis = findViewById(R.id.textView17);
        image_view = findViewById(R.id.image_view);

        String stationId = getIntent().getStringExtra("stationId");
        Log.d("stationId", stationId);
        fetchStationDetails(stationId);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RescueCategoryActivity.class));
            }
        });
    }

    private void fetchStationDetails(String stationId) {
        // Firebase setup (replace with your actual logic)
        DatabaseReference dtbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference stationRef = dtbRef.child("RescueStation").child(stationId);

        stationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String distance = snapshot.child("distance").getValue(String.class);
                    String province = snapshot.child("province").getValue(String.class);
                    String add_dis = address + " " + distance;
                    String geoCode = snapshot.child("geoCode").getValue(String.class);
                    edtName.setText(name);
                    address_dis.setText(add_dis);
                    String vehicle = "car";
                    clickCallApi(origin,geoCode,vehicle);


                } else {
                    // Handle case where station data doesn't exist (optional)
                    // You might want to display an error message or default information.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors (optional)
            }
        });
    }

    private void clickCallApi(String origin, String destination, String vehicle) {
        url = "https://rsapi.goong.io/staticmap/route?"
                + "origin=" + origin
                + "&destination=" + destination
                + "&vehicle=" + vehicle
                + "&api_key=" + getString(R.string.goong_map_api_key);
        get(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        // Get the image data as byte array
                        byte[] imageBytes = response.body().bytes();

                        // Decode the byte array into a Bitmap
                        final Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("run out ?", String.valueOf(imageBytes));
                                System.out.println(image);
                                Log.d("bitmap", String.valueOf(image));
                                image_view.setImageBitmap(image);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                } else {

                }
            }
        });

    }

    private void get(String input, Callback callback) {
        Request request = new Request.Builder().url(input).build();
        Log.d(">>","Inside");
        client.newCall(request).enqueue(callback);
    }
}