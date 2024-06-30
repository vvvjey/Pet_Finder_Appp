package com.example.pet_finder_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference = firebaseDatabase.getReference();;
    private List<AppointmentItem> ListAppointment;
    private Context mContext;
    private String senderName;

    public AppointmentAdapter(List<AppointmentItem> ListAppointment, Context context){
        this.ListAppointment = ListAppointment;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return ListAppointment.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AppointmentItem appointmentItem = ListAppointment.get(position);
        if (holder.image_id != null) {
            Picasso.get().load(appointmentItem.getImageId()).into(holder.image_id);
        }
        if(appointmentItem.getGender().equals("male")){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/male.png?alt=media&token=9326764f-5c4d-49ee-9b54-9cd6a6c5f418").into(holder.gender);
        }else{
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/female.png?alt=media&token=6be18497-fa44-4fc3-8b68-7ba80b622e75").into(holder.gender);
        }
        holder.name.setText(appointmentItem.getName());
        holder.date.setText(appointmentItem.getDate());
        holder.time.setText(appointmentItem.getTime());
        holder.date.setText(appointmentItem.getDate());
        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    User user = snap.getValue(User.class);
                    Log.d("ShowSenderId", "IdSender1" + appointmentItem.getSender());
                    assert user != null;
                    Log.d("ShowSenderId", "IdSender2" + user.getUserId());
                    if(user.getUserId() != null && appointmentItem.getSender() != null && user.getUserId().equals(appointmentItem.getSender())){
                        senderName = user.getFullname();
                        holder.sender.setText(senderName);
                        Log.d("ShowSenderId", appointmentItem.getSender());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set click listener for the item
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppointmentDetail.class);
                intent.putExtra("idOrder" ,appointmentItem.getIdOrder());
                intent.putExtra("idPet" ,appointmentItem.getIdPet());
                intent.putExtra("idAppointment", appointmentItem.getAppointmentId());
                mContext.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id, gender;
        private TextView name, time, date, sender;
        private Button detailBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.catImg);
            name = itemView.findViewById(R.id.nameCat);
            date = itemView.findViewById(R.id.date_value);
            time = itemView.findViewById(R.id.time_value);
            sender = itemView.findViewById(R.id.sender_value);
            gender = itemView.findViewById(R.id.genderImg);
            detailBtn = itemView.findViewById(R.id.btn_detail_appointment);

        }
    }
}
