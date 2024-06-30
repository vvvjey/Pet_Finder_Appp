package com.example.pet_finder_app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{
    private List<NotificationDomain> listNotifi;

    public NotificationAdapter(List<NotificationDomain> listNotifi){
        this.listNotifi = listNotifi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listNotifi.size();
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationDomain notifi = listNotifi.get(position);
        String fromUserId = notifi.getFromUserId();
        holder.img_avatar.setImageResource(notifi.getImg_avatar());
        holder.notifi_descrip.setText(notifi.getNotifi_descrip());
        holder.notifi_time.setText(notifi.getNotifi_time());
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(fromUserId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.child("fullname").getValue(String.class);
                    if (username != null) {
                        holder.notif_name.setText(username);
                    } else {
                        Log.d("name", "Username is null");
                        holder.notif_name.setText("Unknown User");
                    }
                } else {
                    holder.notif_name.setText("Unknown User");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("NotificationAdapter", "Error fetching username", databaseError.toException());
                holder.notif_name.setText("Error");
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView img_avatar;
        private TextView notif_name, notifi_descrip, notifi_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            img_avatar = itemView.findViewById(R.id.notifi_avatar);
            notif_name = itemView.findViewById(R.id.user);
            notifi_descrip = itemView.findViewById(R.id.notifi_text);
            notifi_time = itemView.findViewById(R.id.notifi_time);
        }
    }
}
