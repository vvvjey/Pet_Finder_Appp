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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.AdoptOrder;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class    PetDetailActivity extends AppCompatActivity {

    ImageView arrowBack;
    private ImageView petImageView;
    private TextView petNameTextView;
    private TextView petAgeTextView;
    private TextView petSizeTextView;
    private TextView petBreedTextView;
    private ImageView petGenderImageView;
    private TextView petColorTextView;
    private TextView petMissingDateTextView;
    private TextView petTypeMissingTextView;
    private TextView postUserIdTextView;
    private TextView requestPosterTextView;
    private TextView descriptionTextView;
    private TextView addressMissingTextView;
    private TextView statusMissingTextView;
    private Button chatBtn,contactBtn;
    String petName;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String postUserId;
    String idPet;
    String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pet_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pet_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        petImageView = findViewById(R.id.imagePetDetailMissing);
        petNameTextView = findViewById(R.id.namePetDetailMissing);
        petAgeTextView = findViewById(R.id.agePetDetailMissing);
        petSizeTextView = findViewById(R.id.sizePetDetailMissing);
        petBreedTextView = findViewById(R.id.breedPetDetailMissing);
        petGenderImageView = findViewById(R.id.genderPetDetailMissing);
        petColorTextView = findViewById(R.id.colorPetDetailMissing);
//        petMissingDateTextView = findViewById(R.id.postTimeDetailMissing);
        petTypeMissingTextView = findViewById(R.id.typeMissingPetDetailMissing);
        requestPosterTextView = findViewById(R.id.requestPosterPetDetailMissing);
        descriptionTextView = findViewById(R.id.descriptionPetDetailMissing);
        addressMissingTextView = findViewById(R.id.addressPetDetailMissing);
//        statusMissingTextView = findViewById(R.id.petStatusDetailMissing);
        chatBtn = findViewById(R.id.chatBtn);
        contactBtn = findViewById(R.id.contactBtn);
//        Take data
        Intent intent = getIntent();
        idPet = intent.getStringExtra("idPet");
        petName = intent.getStringExtra("petName");
        String petAge = intent.getStringExtra("petAge");
        String petSize = intent.getStringExtra("petSize");
        String petBreed = intent.getStringExtra("petBreed");
        String addressMissing = intent.getStringExtra("addressMissing");
        String petGender = intent.getStringExtra("petGender");
        String petColor = intent.getStringExtra("petColor");
        String petRegisterDate = intent.getStringExtra("petRegisterDate");
        String petMissingDate = intent.getStringExtra("petMissingDate");
        String petTypeMissing = intent.getStringExtra("petTypeMissing");
        String petImageUrl = intent.getStringExtra("petImageUrl");
        String requestPoster = intent.getStringExtra("requestPoster");
        description = intent.getStringExtra("desciptionPet");
        String statusMissing = intent.getStringExtra("statusMissing");

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        postUserId = intent.getStringExtra("postUserId");


        petNameTextView.setText(petName);
        petAgeTextView.setText(petAge);
        petSizeTextView.setText(petSize);
        petBreedTextView.setText(petBreed);
        if(petGender.equals("Male")){
            petGenderImageView.setImageResource(R.drawable.male);
        } else {
            petGenderImageView.setImageResource(R.drawable.female);
        }
        petColorTextView.setText(petColor);
//        petMissingDateTextView.setText(petMissingDate);
        petTypeMissingTextView.setText(petTypeMissing);
        requestPosterTextView.setText(requestPoster);
        descriptionTextView.setText(description);
        addressMissingTextView.setText(addressMissing);
        Picasso.get().load(petImageUrl).into(petImageView);
//        statusMissingTextView.setText(statusMissing);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUserId = currentUser.getUid();
                List<String> userIds = Arrays.asList(currentUserId, postUserId);
                Intent intent = new Intent(getApplicationContext(), ChatPageChatBoxActivity.class);
                intent.putExtra("userIds", userIds.toArray(new String[0]));
                startActivity(intent);
            }
        });
        contactBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(PetDetailActivity.this).inflate(R.layout.detail_request_adopt_accept, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(PetDetailActivity.this);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                String formattedDate = dateFormat.format(currentTime);

                dialog.show();
                EditText messageEditText = dialogView.findViewById(R.id.editTextText4);

                Button acceptBtn = dialogView.findViewById(R.id.accept);
                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveNotification();

                        String message = messageEditText.getText().toString();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference missingContactRef = database.getReference("MissingContact");
                        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d 'at' h:mm a", Locale.getDefault());
                        String formattedDate = sdf.format(new Date());

                        Map<String, Object> missingContact = new HashMap<>();
                        missingContact.put("name",petName);
                        missingContact.put("idPet",idPet);
                        missingContact.put("description",description);
                        missingContact.put("fromUserId",currentUser.getUid());
                        missingContact.put("posterUserId",postUserId);
                        missingContact.put("message",message);
                        missingContact.put("time",formattedDate);


                        missingContactRef.push().setValue(missingContact)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("PetDetailActivity", "Missing contact record added successfully.");
                                    Toast.makeText(PetDetailActivity.this, "Contact request sent successfully!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("PetDetailActivity", "Error adding notification record", e);
                                    // You can show an error message or perform other actions
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
    }
    public void saveNotification(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("Notification");
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d 'at' h:mm a", Locale.getDefault());
        String formattedDate = sdf.format(new Date());
        // Create a new contact record
        Map<String, Object> contact = new HashMap<>();
        contact.put("fromUserId", currentUser.getUid());
        contact.put("toUserId", postUserId);
        contact.put("notifi_descrip", "You have a missing notification about " + getIntent().getStringExtra("petName") + " post");
        contact.put("notifi_time", formattedDate);
        contact.put("notifi_type", "Missing");



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
