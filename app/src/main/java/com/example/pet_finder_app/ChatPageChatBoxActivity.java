package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.ChatMessage;
import com.example.pet_finder_app.Class.Chatroom;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Arrays;
import java.util.List;
public class ChatPageChatBoxActivity extends AppCompatActivity {
    EditText inputMessage;
    ImageButton sendMessageBtn;
    Chatroom chatroom;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String chatroomId,username;
    ChatBoxAdapter adapter;
    RecyclerView recyclerView;
    List<String> userIds;
    TextView receiverName;
    Toolbar arrowBack;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page_chatbox);
        inputMessage = findViewById(R.id.chat_message_input);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        recyclerView = findViewById(R.id.recyclerView);
        receiverName = findViewById(R.id.receiverName);
        arrowBack = findViewById(R.id.toolbarArrowBack);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        chatroomId = getIntent().getStringExtra("chatroomId");
        username = getIntent().getStringExtra("username");
        String[] userIdsArray = getIntent().getStringArrayExtra("userIds");

        receiverName.setText(username);

        userIds = Arrays.asList(userIdsArray);
        if(chatroomId == null){
            getChatroomId(userIds, new ChatroomIdCallback() {
                @Override
                public void onChatroomIdFound(String foundChatroomId) {
                    if (foundChatroomId != null) {
                        chatroomId = foundChatroomId;
                        getOrCreateChatroomModel();
                    } else {
                        getOrCreateChatroomModel();

                    }
                }
            });
        } else {
            getOrCreateChatroomModel();
        }
        Log.d("ChatPageChatBoxActivity", "Chatroom ID: " + chatroomId);
        Log.d("ChatPageChatBoxActivity", "User IDs: " + userIds.toString());
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChatPageActivity.class));
            }
        });
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = inputMessage.getText().toString().trim();
                if(message.isEmpty())
                    return;
                sendMessage(message);

            }
        });

    }
    void setupChatRecyclerView(){
        DocumentReference getChatroomReference = FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
        CollectionReference getChatroomMessageReference = getChatroomReference.collection("chats");


        Query query = getChatroomMessageReference.orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessage> options = new FirestoreRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query,ChatMessage.class).build();

        adapter = new ChatBoxAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
            recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }
    public void sendMessage(String message){
//        Handle chatroom collection
        chatroom.setLastMessageTimestamp(Timestamp.now());
        chatroom.setLastMessageSenderId(currentUser.getUid());
        chatroom.setLastMessage(message);
        DocumentReference getChatroomReference = FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
        getChatroomReference.set(chatroom);
//        Handle message collection
        ChatMessage chatmessage = new ChatMessage(message,currentUser.getUid(),Timestamp.now());
        CollectionReference getChatroomMessageReference = getChatroomReference.collection("chats");
        getChatroomMessageReference.add(chatmessage)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            inputMessage.setText("");
//                            sendNotification(message);
                        }
                    }
                });

    }
    void getOrCreateChatroomModel(){
        if (chatroomId == null) {
            // Case: chatroomId is null, create new chatroom
            CollectionReference chatroomsRef = FirebaseFirestore.getInstance().collection("chatrooms");

            chatroom = new Chatroom(null, userIds, null, "", "");
            chatroomsRef.add(chatroom).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {
                        chatroomId = task.getResult().getId();
                        chatroom.setChatroomId(chatroomId);
                        chatroomsRef.document(chatroomId).set(chatroom).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    setupChatRecyclerView();
                                    Log.d("ChatPageChatBoxActivity", "Chatroom created successfully with ID: " + chatroomId);
                                } else {
                                    Log.e("ChatPageChatBoxActivity", "Error updating chatroom with ID", task.getException());
                                }
                            }
                        });
                    } else {
                        Log.e("ChatPageChatBoxActivity", "Error creating chatroom", task.getException());
                    }
                }
            });
        } else {
            DocumentReference chatroomRef = FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);

            chatroomRef.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    chatroom = task.getResult().toObject(Chatroom.class);
                    if(chatroom == null){
                        Log.e("ChatPageChatBoxActivity", "Chatroom with provided ID does not exist");
                        return;
                    }

                    Log.d("ChatPageChatBoxActivity", "Chatroom retrieved successfully with ID: " + chatroomId);
                    setupChatRecyclerView();
                } else {
                    Log.e("ChatPageChatBoxActivity", "Error fetching chatroom", task.getException());
                }
            });
        }
    }
    void getChatroomId(List<String> userIds, ChatroomIdCallback callback) {
        CollectionReference chatroomsRef = FirebaseFirestore.getInstance().collection("chatrooms");

        chatroomsRef
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean chatroomFound = false;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Chatroom chatroom = document.toObject(Chatroom.class);

                            if (chatroom.getUserIds() != null && chatroom.getUserIds().containsAll(userIds)) {
                                chatroomId = document.getId();
                                Log.e("ChatPageChatBoxActivity 1", chatroomId);
                                chatroomFound = true;
                                callback.onChatroomIdFound(chatroomId);
                                return;
                            }
                        }
                        if (!chatroomFound) {
                            Log.e("ChatPageChatBoxActivity", "No chatroom found for the provided user IDs");
                            callback.onChatroomIdFound(null);
                        }
                    } else {
                        Log.e("ChatPageChatBoxActivity", "Error getting documents: ", task.getException());
                        callback.onChatroomIdFound(null);
                    }
                });
    }
    interface ChatroomIdCallback {
        void onChatroomIdFound(String chatroomId);
    }



}
