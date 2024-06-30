package com.example.pet_finder_app;

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
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Appoitment;
import com.example.pet_finder_app.Class.Pet;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AppointmentDetail extends AppCompatActivity {
    Button rejectBtn, acceptBtn;
    Toolbar arrowBack;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AdoptOrder adoptOrder;
    User user;
    Pet pet;
    Appoitment appointment;
    AdoptPet adoptPet;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    List<String> timeStatus = new ArrayList<>();
    String idPet, idAdopt, idOrder, idUserPost,idUser, idAppointment;
    EditText reasonMsg;
    TextView namePet, agePet, breedPet, statusPet, pricePet, userName, userAge, userPhone, userEmail;
    ImageView genderPet, genderUser, imagePet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.appointment_detail);
        rejectBtn = findViewById(R.id.rejectBtn);
        acceptBtn = findViewById(R.id.acceptBtn);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        idOrder = getIntent().getStringExtra("idOrder");
        idPet = getIntent().getStringExtra("idPet");
        idAppointment = getIntent().getStringExtra("idAppointment");
        Log.d("Show id Order: ", idOrder);
        namePet = findViewById(R.id.nameCat);
        agePet = findViewById(R.id.age_value);
        breedPet = findViewById(R.id.breed_value);
        statusPet = findViewById(R.id.status_value);
        pricePet = findViewById(R.id.price_value);
        userName = findViewById(R.id.nameUser);
        userAge = findViewById(R.id.age_value2);
        userPhone = findViewById(R.id.phone_value);
        userEmail = findViewById(R.id.email_value);
        genderPet = findViewById(R.id.genderImg);
        genderUser = findViewById(R.id.genderUser);
        imagePet = findViewById(R.id.catImg);
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
                View dialogView = LayoutInflater.from(AppointmentDetail.this).inflate(R.layout.detail_appointment_adopt, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentDetail.this);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                String formattedDate = dateFormat.format(currentTime);

                dialog.show();
                TextView title = findViewById(R.id.title_appointment);
                title.setText("Do you want to accept this adoption");
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
                                    databaseReference.child("AdoptOrder").child(idOrder).child("status").setValue("Completed");

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

                                    Toast.makeText(AppointmentDetail.this, "Adoption process completed successfully", Toast.LENGTH_SHORT).show();
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
                View dialogView = LayoutInflater.from(AppointmentDetail.this).inflate(R.layout.detail_appointment_adopt, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentDetail.this);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                dialog.show();
                TextView title = findViewById(R.id.title_appointment);
                title.setText("Do you want to cancelled this adoption");
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                String formattedDate = dateFormat.format(currentTime);

                Button rejectBtn = dialogView.findViewById(R.id.accept);
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
                                    databaseReference.child("AdoptOrder").child(idOrder).child("status").setValue("Rejected");

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

                                    Toast.makeText(AppointmentDetail.this, "Adoption process cancelled successfully", Toast.LENGTH_SHORT).show();
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

        fetchAdoptOrder();
    }

//    private void showAlertDialog(View v, String title, String message){
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle(title);
//        alert.setMessage(message);
//        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        alert.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//    }

    private void fetchAdoptOrder() {
        databaseReference.child("AdoptOrder").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    AdoptOrder tempOrder = snap.getValue(AdoptOrder.class);
                    if (tempOrder != null && idOrder.equals(tempOrder.getIdOrder())) {
                        adoptOrder = tempOrder;
                        idUserPost = adoptOrder.getUserId();
                        Log.d("Show id User: ", idUserPost);
                        fetchUser();
                        fetchPet();
                        fetchAdoptPet();
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
        if (idUserPost == null) return;
        databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User tempUser = snap.getValue(User.class);
                    if (tempUser != null && idUserPost.equals(tempUser.getUserId())) {
                        user = tempUser;
                        Log.d("Show id UserTemp: ", user.getUserId());
                        if (user != null && appointment != null && adoptOrder != null && pet != null && adoptPet != null) {
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

    private void fetchPet() {
        if (idPet == null) return;
        databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Pet tempPet = snap.getValue(Pet.class);
                    if (tempPet != null && idPet.equals(tempPet.getIdPet())) {
                        pet = tempPet;
                        if (user != null && appointment != null && adoptOrder != null && pet != null && adoptPet != null) {
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

    private void fetchAdoptPet() {
        if (idPet == null) return;
        databaseReference.child("AdoptPet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    AdoptPet tempAdoptPet = snap.getValue(AdoptPet.class);
                    if (tempAdoptPet != null && idPet.equals(tempAdoptPet.getIdPet())) {
                        adoptPet = tempAdoptPet;
                        if (user != null && appointment != null && adoptOrder != null && pet != null && adoptPet != null) {
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
                        if (user != null && appointment != null && adoptOrder != null && pet != null && adoptPet != null) {
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
        if (user == null || adoptOrder == null || appointment == null || pet == null) {
            Log.e("ShowData", "Cannot show data, missing user, adoptOrder, or appointment");
            return;
        }
        namePet.setText(pet.getName());
        agePet.setText(pet.getAge());
        breedPet.setText(pet.getBreed());
        statusPet.setText(pet.getTypeId());
        pricePet.setText(adoptPet.getPrice());
        userName.setText(user.getFullname());
        userAge.setText(user.getDateBirth());
        userPhone.setText(user.getPhoneNumber());
        userEmail.setText(user.getEmail());
        if(pet.getGender().equals("male")){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/male.png?alt=media&token=9326764f-5c4d-49ee-9b54-9cd6a6c5f418").into(genderPet);
        }else{
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/female.png?alt=media&token=6be18497-fa44-4fc3-8b68-7ba80b622e75").into(genderPet);
        }
        if(user.getGender().equals("Male")){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/male.png?alt=media&token=9326764f-5c4d-49ee-9b54-9cd6a6c5f418").into(genderUser);
        }else{
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/female.png?alt=media&token=6be18497-fa44-4fc3-8b68-7ba80b622e75").into(genderUser);
        }
        Picasso.get().load(pet.getImgUrl().get(0)).into(imagePet);

    }

    public void saveNotification(String statusBtn){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("Notification");
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d 'at' h:mm a", Locale.getDefault());
        String formattedDate = sdf.format(new Date());
        Map<String, Object> contact = new HashMap<>();
        contact.put("fromUserId", idUser);
        contact.put("toUserId", idUserPost);
        if(statusBtn.equals("Reject")){
            contact.put("notifi_descrip", "The pet appointment has been reject");
        }
        else{
            contact.put("notifi_descrip", "The pet appointment has been accept");
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
