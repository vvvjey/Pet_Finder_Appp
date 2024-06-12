package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Appoitment;
import com.example.pet_finder_app.Class.Pet;
import com.example.pet_finder_app.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HistoryAdoptDetailActivity extends AppCompatActivity {

    Toolbar arrowBack;
    Button backPet;
    Pet pet;
    AdoptPet adopt;
    AdoptOrder order;
    User user;
    Appoitment appoitment;

    String idPet, idOrder, idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_adopt_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.history_adopt_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HistoryAdoptActivity.class));
            }
        });

        TextView petAge = findViewById(R.id.age_value);
        TextView petBreed = findViewById(R.id.breed_value);
        TextView registerDay = findViewById(R.id.date_adopt);
        ImageView imageView = findViewById(R.id.catImg);
        TextView petName = findViewById(R.id.nameCat);
        TextView petPrice = findViewById(R.id.price_value);
        ImageView petGender = findViewById(R.id.genderImg);

        TextView nameUser = findViewById(R.id.name_full);
        TextView dateBrith = findViewById(R.id.date_birth_value);
        TextView gender = findViewById(R.id.gender_value);
        TextView address = findViewById(R.id.address_value);
        TextView requestMsg = findViewById(R.id.request_value);
        TextView phoneNumber = findViewById(R.id.phone_value);

        TextView requestText = findViewById(R.id.request_text);
        TextView statusText = findViewById(R.id.booked_text);
        TextView adoptionText = findViewById(R.id.adoption_text);

        TextView requestTime = findViewById(R.id.request_time);
        TextView bookTime = findViewById(R.id.booked_time);
        TextView adoptionTime = findViewById(R.id.adoption_time);

        TextView dateBook = findViewById(R.id.date_book_value);
        TextView timeBook = findViewById(R.id.time_book_value);

        idPet = getIntent().getStringExtra("idPet");
        idOrder = getIntent().getStringExtra("idOrder");
        Log.d("Show id order", idOrder);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("AdoptPet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idPet.equals(snap.getValue(AdoptPet.class).getIdPet())) {
                        adopt = snap.getValue(AdoptPet.class);
                        petPrice.setText(adopt.getPrice());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Appointment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idOrder.equals(snap.getValue(Appoitment.class).getIdOrder())) {
                        appoitment = snap.getValue(Appoitment.class);
                        dateBook.setText(appoitment.getDate());
                        timeBook.setText(appoitment.getTimeType());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("AdoptOrder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    AdoptOrder adoptOrder = snap.getValue(AdoptOrder.class);
                    if (adoptOrder != null && idOrder.equals(adoptOrder.getIdOrder())) {
                        order = adoptOrder;
                        requestText.setText("Request to Adopt");
                        requestTime.setText(order.getStatusTime().get(0));
                        if ("Accept".equals(order.getStatus())) {
                            statusText.setText("Successfully booked appointment");
                            bookTime.setText(order.getStatusTime().get(1));
                        } else if ("Reject".equals(order.getStatus())) {
                            statusText.setText("The appointment has been cancelled");
                            bookTime.setText(order.getStatusTime().get(1));
                        } else if ("Completed".equals(order.getStatus())) {
                            statusText.setText("Successfully booked appointment");
                            bookTime.setText(order.getStatusTime().get(1));
                            adoptionText.setText("The adoption has been completed");
                            adoptionTime.setText(order.getStatusTime().get(2));
                        } else if ("Rejected".equals(order.getStatus())) {
                            statusText.setText("Successfully booked appointment");
                            bookTime.setText(order.getStatusTime().get(1));
                            adoptionText.setText("The adoption has been rejected");
                            adoptionTime.setText(order.getStatusTime().get(2));
                        }
                        idUser = order.getUserId();
                        requestMsg.setText(order.getRequestMsg());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HistoryAdoptDetail", "Failed to read AdoptOrder value.", error.toException());
            }
        });

        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idPet.equals(snap.getValue(Pet.class).getIdPet())) {
                        pet = snap.getValue(Pet.class);
                        Picasso.get().load(pet.getImgUrl().get(0)).into(imageView);
                        petAge.setText(pet.getAge());
                        petBreed.setText(pet.getBreed());
                        registerDay.setText(pet.getRegisterDate());
                        petName.setText(pet.getName());
                        Picasso.get().load(pet.getGender()).into(petGender);

                        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap: snapshot.getChildren()){
                                    Log.d("Show id user", idUser);
                                    if(idUser.equals(snap.getValue(User.class).getUserId())) {
                                        user = snap.getValue(User.class);
                                        nameUser.setText(user.getName());
                                        dateBrith.setText(user.getDateBirth());
                                        gender.setText(user.getGender());
                                        address.setText(user.getAddress());
                                        phoneNumber.setText(user.getPhoneNumber());
                                        break;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
