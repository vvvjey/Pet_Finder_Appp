package com.example.pet_finder_app;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.Chatroom;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChatPageActivity extends AppCompatActivity {
    Toolbar arrowBack;
    RecyclerView recycleView;
    ChatPageAdapter adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        recycleView = findViewById(R.id.user_list_chat_view);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("chatrooms")
                .whereArrayContains("userIds",currentUser.getUid())
                .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING);


        if (currentUser != null) {
            // User is signed in
            String uid = currentUser.getUid();
            Log.d("MainActivity", "User ID: " + uid);

        }



        // Create a new chatroom map
        //        DocumentReference newChatroomRef = db.collection("chatrooms").document();
//        String chatroomId = newChatroomRef.getId();

//        Map<String, Object> chatroom = new HashMap<>();
//        chatroom.put("chatroomId", chatroomId);
//        chatroom.put("userIds", Arrays.asList(currentUser.getUid(), "7wbrp82Nn7My0KXVQwTbjRr18J62"));
//        chatroom.put("lastMessageTimestamp", Timestamp.now());
//        chatroom.put("lastMessageSenderId", "user1");
//        chatroom.put("lastMessage", "Hello!");
//
//        // Set the document data
//        newChatroomRef.set(chatroom)
//                .addOnSuccessListener(aVoid -> {
//                    // Document successfully written
//                    Log.d("MainActivity", "Chatroom created with ID: " + chatroomId);
//                })
//                .addOnFailureListener(e -> {
//                    // Handle the error
//                    Log.w("MainActivity", "Error creating chatroom", e);
//                });


        FirestoreRecyclerOptions<Chatroom> options = new FirestoreRecyclerOptions.Builder<Chatroom>()
                .setQuery(query, Chatroom.class)
                .build();

        adapter = new ChatPageAdapter(options, this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatPageActivity.this, HomepageActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
