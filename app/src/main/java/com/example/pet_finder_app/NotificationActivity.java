package com.example.pet_finder_app;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private NotificationAdapter notificationAdapter;
    private List<NotificationDomain> NotifiList;

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

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        NotifiList = new ArrayList<>();
        recyclerView = findViewById(R.id.notifi_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40, 40));
        notificationAdapter = new NotificationAdapter(NotifiList);
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));

        fetchNotifications(currentUserId);
    }

    private void fetchNotifications(String userId) {
        FirebaseDatabase.getInstance().getReference("Notification")
                .orderByChild("toUserId")
                .equalTo(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NotifiList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                NotificationDomain notification = snapshot.getValue(NotificationDomain.class);
                            NotifiList.add(notification);
                        }
                        Collections.reverse(NotifiList);
                        notificationAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("NotificationActivity", "Error fetching notifications", databaseError.toException());
                    }
                });
    }

}
