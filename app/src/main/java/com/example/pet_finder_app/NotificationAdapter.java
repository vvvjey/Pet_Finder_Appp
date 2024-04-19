package com.example.pet_finder_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.img_avatar.setImageResource(notifi.getImg_avatar());
        holder.notif_name.setText(notifi.getNotif_name());
        holder.notifi_descrip.setText(notifi.getNotifi_descrip());
        holder.notifi_time.setText(notifi.getNotifi_time());
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
