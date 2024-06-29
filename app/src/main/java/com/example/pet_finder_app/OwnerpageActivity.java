package com.example.pet_finder_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pet_finder_app.Class.Pet;
import com.example.pet_finder_app.Class.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OwnerpageActivity extends AppCompatActivity {
    private Toolbar arrowBack;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TextView userName;
    TextView user_address;
    TextView user_phone;
    TextView user_email;
    ImageView user_img;
    String idUserPost;
    int countPet = 0;
    TabOwnerLayout tabOwnerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idUserPost = getIntent().getStringExtra("idUserPost");
        setContentView(R.layout.owner_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.owner_profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("idUserPost", idUserPost);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        userName = findViewById(R.id.user_nameP);
        user_address = findViewById(R.id.user_addressP);
        user_phone = findViewById(R.id.user_phoneP);
        user_email = findViewById(R.id.user_emailP);

        tabLayout = findViewById(R.id.your_tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        user_img = findViewById(R.id.user_image);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    User user = snap.getValue(User.class);
                    assert user != null;
                    if(user.getUserId().equals(idUserPost)){
                        userName.setText(user.getName());
                        user_address.setText(user.getAddress());
                        user_email.setText(user.getEmail());
                        user_phone.setText(user.getPhoneNumber());
                        Picasso.get().load(user.getImgUser()).into(user_img);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    Pet pet = snap.getValue(Pet.class);
                    assert pet != null;
                    if(pet.getPostUserId().equals(idUserPost)){
                        Log.d("idUserPost", pet.getIdPet());
                        countPet += 1;
                    }
                    updateTabText();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tabOwnerLayout = new TabOwnerLayout(this, idUserPost);
        viewPager2.setAdapter(tabOwnerLayout);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Owner Pet");
                    break;
                case 1:
                    tab.setText("Adoption Policy");
                    break;
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
    private void updateTabText() {
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        if (tab != null) {
            tab.setText("Owner Pet (" + countPet + ")");
        }
    }
}