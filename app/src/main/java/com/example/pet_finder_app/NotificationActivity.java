package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.notification), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        List<NotificationDomain> NotifiList = new ArrayList<NotificationDomain>();
        NotifiList.add(new NotificationDomain(R.drawable.avatar, "Pickle","Xin chào! Có điều gì tôi có thể giúp bạn hôm nay không?", "Now"));
        NotifiList.add(new NotificationDomain(R.drawable.avatar, "Pickle","Xin chào! Có điều gì tôi có thể giúp bạn hôm nay không?", "Now"));
        NotifiList.add(new NotificationDomain(R.drawable.avatar, "Pickle","Xin chào! Có điều gì tôi có thể giúp bạn hôm nay không?", "Now"));
        NotifiList.add(new NotificationDomain(R.drawable.avatar, "Pickle","Xin chào! Có điều gì tôi có thể giúp bạn hôm nay không?", "Now"));
        NotifiList.add(new NotificationDomain(R.drawable.avatar, "Pickle","Xin chào! Có điều gì tôi có thể giúp bạn hôm nay không?", "Now"));

        recyclerView = findViewById(R.id.notifi_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40,40));
        NotificationAdapter notificationAdapter = new NotificationAdapter(NotifiList);
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));

    }
}
