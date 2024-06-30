package com.example.pet_finder_app;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.MissingPetContact;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MissingAnimalPostContactActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MissingAnimalPostContactAdapter adapter;
    private List<MissingPetContact> missingContactList;
    private Toolbar arrowBack;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_animal_post_contact);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.lvMissingPostContact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        missingContactList = new ArrayList<>();
        adapter = new MissingAnimalPostContactAdapter(missingContactList,this);
        recyclerView.setAdapter(adapter);

        fetchMissingContacts();
    }

    private void fetchMissingContacts() {
        DatabaseReference missingContactRef = FirebaseDatabase.getInstance().getReference("MissingContact");
        missingContactRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                missingContactList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MissingPetContact contact = snapshot.getValue(MissingPetContact.class);
                    if (contact != null && contact.getPosterUserId().equals(currentUser.getUid())) {
                        missingContactList.add(contact);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MissingAnimalPostContactActivity", "Error fetching data", databaseError.toException());
            }
        });
    }
}
