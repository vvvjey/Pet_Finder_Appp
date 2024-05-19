package com.example.pet_finder_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.Appoitment;
import com.example.pet_finder_app.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailRequetAdoptActivity extends AppCompatActivity {
    Button rejectBtn, acceptBtn;
    Toolbar arrowBack;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AdoptOrder adoptOrder;
    User user;
    Appoitment appointment;
    String ipPet, ipAdopt, idOrder, idUser;
    EditText addressEdt, nameEdt, dateBirthEdt, requestEdt, dateMeetEdt, phoneEdt, emailEdt, timeEdt, genderEdt;
    String fullName, dateBirth, gender, email, address, country, city,district, ward, phone, requestMsg, dateMeet, timeMeet, fullAddress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_request_adopt);
        rejectBtn = findViewById(R.id.rejectBtn);
        acceptBtn = findViewById(R.id.acceptBtn);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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

                dialog.show();
                Button acceptBtn = dialogView.findViewById(R.id.accept);
                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("AdoptOrder").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (idOrder.equals(snapshot.getValue(AdoptOrder.class).getIdOrder())) {
                                        databaseReference.child("AdoptOrder").child(idOrder).child("status").setValue("Accept");
                                        Toast.makeText(DetailRequetAdoptActivity.this, "Accept order successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), AdoptStatusActivity.class));
                                    } else {
                                        Log.d("ConstraintViolation", "idOrder does not match the desired value.");
                                    }
                                }}
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

                Button rejectBtn = dialogView.findViewById(R.id.reject);
                rejectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("AdoptOrder").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (idOrder.equals(snapshot.getValue(AdoptOrder.class).getIdOrder())) {
                                        databaseReference.child("AdoptOrder").child(idOrder).child("status").setValue("Reject");
                                        Toast.makeText(DetailRequetAdoptActivity.this, "Reject order successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), AdoptStatusActivity.class));
                                    } else {
                                        Log.d("ConstraintViolation", "idOrder does not match the desired value.");
                                    }
                                }}
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
                        idUser = adoptOrder.getUserId();
                        Log.d("Show id User: ", idUser);
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
        if (idUser == null) return;
        databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User tempUser = snap.getValue(User.class);
                    if (tempUser != null && idUser.equals(tempUser.getUserId())) {
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
    }
}
