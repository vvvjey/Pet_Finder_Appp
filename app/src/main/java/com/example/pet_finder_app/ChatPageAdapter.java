package com.example.pet_finder_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.Chatroom;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatPageAdapter extends FirestoreRecyclerAdapter<Chatroom, ChatPageAdapter.ChatroomViewHolder> {
    private Context context;

    public ChatPageAdapter(FirestoreRecyclerOptions<Chatroom> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public ChatroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_page_user_list_item, parent, false);
        return new ChatroomViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatroomViewHolder holder, int position, @NonNull Chatroom model) {

//        Take information of other user

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String currentUid = currentUser.getUid();

            // Extract other user IDs (excluding current user's ID)
            for (String userId : model.getUserIds()) {
                if (!userId.equals(currentUid)) {
                    fetchUserFullName(userId, fullName -> {
                        holder.username.setText(fullName);
                        holder.lastMessageText.setText(model.getLastMessage());
                        holder.userItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(v.getContext(), ChatPageChatBoxActivity.class);
                                intent.putExtra("chatroomId",model.getChatroomId());
                                intent.putExtra("userIds", model.getUserIds().toArray(new String[0]));
                                intent.putExtra("username", fullName);

                                v.getContext().startActivity(intent);

                            }
                        });
                    });
                    break;
                }
            }
        }
//        holder.username.setText(model.getUserIds().toString());

    }

    class ChatroomViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView lastMessageText;
        ConstraintLayout userItem;
        public ChatroomViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_name);
            lastMessageText = itemView.findViewById(R.id.lastest_message);
            userItem = itemView.findViewById(R.id.user_chat_layout);
        }
    }
    private void fetchUserFullName(String uid, final FullNameCallback callback) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fullName = snapshot.child("fullname").getValue(String.class);
                    Log.d("full name",fullName);
                    callback.onCallback(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("abc", "Failed to read user data", error.toException());
            }
        });
    }
    public interface FullNameCallback {
        void onCallback(String fullName);
    }
}
