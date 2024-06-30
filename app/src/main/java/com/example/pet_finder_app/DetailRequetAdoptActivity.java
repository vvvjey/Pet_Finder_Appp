package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.Appoitment;
import com.example.pet_finder_app.Class.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DetailRequetAdoptActivity extends AppCompatActivity {
    Button rejectBtn, acceptBtn;
    Toolbar arrowBack;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AdoptOrder adoptOrder;
    User user;
    Appoitment appointment;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    List<String> timeStatus = new ArrayList<>();
    List<String> imageUrl = new ArrayList<>(Collections.nCopies(5, ""));
    List<ImageView> uploadImg = new ArrayList<>(5);
    String ipPet, ipAdopt, idOrder, idUser, idPostUser;
    EditText addressEdt, nameEdt, dateBirthEdt, requestEdt, dateMeetEdt, phoneEdt, emailEdt, timeEdt, genderEdt;
    String fullName, dateBirth, gender, email, address, country, city,district, ward, phone, requestMsg, dateMeet, timeMeet, fullAddress;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_request_adopt);
        rejectBtn = findViewById(R.id.rejectBtn);
        acceptBtn = findViewById(R.id.acceptBtn);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        uploadImg.add(findViewById(R.id.uploadImg1));
        uploadImg.add(findViewById(R.id.uploadImg2));
        uploadImg.add(findViewById(R.id.uploadImg3));
        uploadImg.add(findViewById(R.id.uploadImg4));
        uploadImg.add(findViewById(R.id.uploadImg5));
        idOrder = getIntent().getStringExtra("idOrder");
        Log.d("Show id Order: ", idOrder);
        nameEdt = findViewById(R.id.name_value);
        dateBirthEdt = findViewById(R.id.date_value);
        genderEdt = findViewById(R.id.gender_value);
        addressEdt = findViewById(R.id.address_value);
        phoneEdt = findViewById(R.id.phone_value);
        requestEdt = findViewById(R.id.request_value);
        dateMeetEdt = findViewById(R.id.dateMeet_value);
        timeEdt = findViewById(R.id.time_value);
        emailEdt = findViewById(R.id.email_value);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        idUser = currentUser.getUid();

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptStatusActivity.class));
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(DetailRequetAdoptActivity.this).inflate(R.layout.detail_request_adopt_accept, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRequetAdoptActivity.this);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                String formattedDate = dateFormat.format(currentTime);

                dialog.show();
                Button acceptBtn = dialogView.findViewById(R.id.accept);
                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveNotification("Accept");
                        databaseReference.child("AdoptOrder").child(idOrder).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                AdoptOrder order = dataSnapshot.getValue(AdoptOrder.class);
                                if (order != null && idOrder.equals(order.getIdOrder())) {
                                    // Update the status
                                    databaseReference.child("AdoptOrder").child(idOrder).child("status").setValue("Accept");

                                    // Retrieve the current statusTime list
                                    List<String> statusTimeList = new ArrayList<>();
                                    if (dataSnapshot.child("statusTime").exists()) {
                                        for (DataSnapshot snapshot : dataSnapshot.child("statusTime").getChildren()) {
                                            statusTimeList.add(snapshot.getValue(String.class));
                                        }
                                    }

                                    // Add the new date to the list
                                    statusTimeList.add(formattedDate);

                                    // Update the statusTime list in Firebase
                                    databaseReference.child("AdoptOrder").child(idOrder).child("statusTime").setValue(statusTimeList);

                                    Toast.makeText(DetailRequetAdoptActivity.this, "Accept order successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), AdoptStatusActivity.class));
                                } else {
                                    Log.d("ConstraintViolation", "idOrder does not match the desired value.");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("FirebaseError", databaseError.getMessage());
                            }
                        });
                    }
                });
                TextView closeButton = dialogView.findViewById(R.id.closeAcceptBtn);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the dialog when the "Close" button is clicked
                        dialog.dismiss();
                    }
                });
            }
        });


        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(DetailRequetAdoptActivity.this).inflate(R.layout.detail_request_adopt_reject_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRequetAdoptActivity.this);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                dialog.show();
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                String formattedDate = dateFormat.format(currentTime);

                Button rejectBtn = dialogView.findViewById(R.id.reject);
                rejectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveNotification("Reject");
                        databaseReference.child("AdoptOrder").child(idOrder).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                AdoptOrder order = dataSnapshot.getValue(AdoptOrder.class);
                                if (order != null && idOrder.equals(order.getIdOrder())) {
                                    // Update the status
                                    databaseReference.child("AdoptOrder").child(idOrder).child("status").setValue("Reject");

                                    // Retrieve the current statusTime list
                                    List<String> statusTimeList = new ArrayList<>();
                                    if (dataSnapshot.child("statusTime").exists()) {
                                        for (DataSnapshot snapshot : dataSnapshot.child("statusTime").getChildren()) {
                                            statusTimeList.add(snapshot.getValue(String.class));
                                        }
                                    }

                                    // Add the new date to the list
                                    statusTimeList.add(formattedDate);

                                    // Update the statusTime list in Firebase
                                    databaseReference.child("AdoptOrder").child(idOrder).child("statusTime").setValue(statusTimeList);

                                    Toast.makeText(DetailRequetAdoptActivity.this, "Reject order successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), AdoptStatusActivity.class));
                                } else {
                                    Log.d("ConstraintViolation", "idOrder does not match the desired value.");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("FirebaseError", databaseError.getMessage());
                            }
                        });
                    }
                });

                TextView closeButton = dialogView.findViewById(R.id.closeRejectAadoptBtn);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the dialog when the "Close" button is clicked
                        dialog.dismiss();
                    }
                });
            }
        });

        fetchAdoptOrder();
    }

    private void fetchAdoptOrder() {
        databaseReference.child("AdoptOrder").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    AdoptOrder tempOrder = snap.getValue(AdoptOrder.class);
                    if (tempOrder != null && idOrder.equals(tempOrder.getIdOrder()) && "Pretending".equals(tempOrder.getStatus())) {
                        adoptOrder = tempOrder;
                        idPostUser = adoptOrder.getUserId();
                        Log.d("Show id User: ", idPostUser);
                        fetchUser();
                        fetchAppointment();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    private void fetchUser() {
        if (idPostUser == null) return;
        databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User tempUser = snap.getValue(User.class);
                    if (tempUser != null && idPostUser.equals(tempUser.getUserId())) {
                        user = tempUser;
                        Log.d("Show id UserTemp: ", user.getUserId());
                        if (user != null && appointment != null && adoptOrder != null) {
                            showData();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    private void fetchAppointment() {
        databaseReference.child("Appointment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Appoitment tempAppointment = snap.getValue(Appoitment.class);
                    if (tempAppointment != null && idOrder.equals(tempAppointment.getIdOrder())) {
                        appointment = tempAppointment;
                        Log.d("Show id appointment: ", appointment.getIdAppointment());
                        if (user != null && appointment != null && adoptOrder != null) {
                            showData();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    public void showData() {
        if (user == null || adoptOrder == null || appointment == null) {
            Log.e("ShowData", "Cannot show data, missing user, adoptOrder, or appointment");
            return;
        }

        nameEdt.setText(user.getName());
        dateBirthEdt.setText(user.getDateBirth());
        genderEdt.setText(user.getGender());
        addressEdt.setText(user.getAddress());
        phoneEdt.setText(user.getPhoneNumber());
        requestEdt.setText(adoptOrder.getRequestMsg());
        dateMeetEdt.setText(appointment.getDate());
        timeEdt.setText(appointment.getTimeType());
        emailEdt.setText(user.getEmail());
        for (int i = 0; i < 5; i++){
            if(!Objects.equals(adoptOrder.getImageUrl().get(i), "")){
                imageUrl.set(i, adoptOrder.getImageUrl().get(i));
                Picasso.get().load(adoptOrder.getImageUrl().get(i)).into(uploadImg.get(i));
            }
        }
    }

    public void saveNotification(String statusBtn){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("Notification");
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d 'at' h:mm a", Locale.getDefault());
        String formattedDate = sdf.format(new Date());
        Map<String, Object> contact = new HashMap<>();
        contact.put("fromUserId", idUser);
        contact.put("toUserId", idPostUser);
        if(statusBtn.equals("Reject")){
            contact.put("notifi_descrip", "A pet request has been cancelled");
        }
        else{
            contact.put("notifi_descrip", "A pet request has been accepted");
        }
        contact.put("notifi_time", formattedDate);
        contact.put("notifi_type", "Adopt");



        // Add a new record
        notificationsRef.push().setValue(contact)
                .addOnSuccessListener(aVoid -> {
                    Log.d("PetDetailActivity", "Notification record added successfully.");
                    // You can show a success message or perform other actions
                })
                .addOnFailureListener(e -> {
                    Log.w("PetDetailActivity", "Error adding notification record", e);
                    // You can show an error message or perform other actions
                });
    }
}
