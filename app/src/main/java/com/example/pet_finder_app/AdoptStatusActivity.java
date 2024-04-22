package com.example.pet_finder_app;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
public class AdoptStatusActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private Toolbar arrowBack;
    private boolean clicked;
    private ImageView filterAdopt;

    private Animation getFromBottom() {
        return AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
    }

    private Animation getToBottom() {
        return AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
    }

    private Animation getRotateClose() {
        return AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
    }

    private Animation getRotateOpen() {
        return AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adopt_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adopt_status), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        List<AdoptStatusItem> list = new ArrayList<AdoptStatusItem>();
        list.add(new AdoptStatusItem(R.drawable.cat2, "Timothie", "Average", "White, Blue eyes", "08-09-2023", "cyan"));
        list.add(new AdoptStatusItem(R.drawable.cat1, "Rook", "Small", "Yellow, White, Black", "08-09-2023", "white"));
        list.add(new AdoptStatusItem(R.drawable.cat1, "Messiiii", "Average", "Yellow, White, Black", "08-09-2023", "yellow"));
        list.add(new AdoptStatusItem(R.drawable.cat1, "Miles", "Small", "Yellow, White, Black", "08-09-2023", "black"));
        list.add(new AdoptStatusItem(R.drawable.cat1, "Testoron", "Small", "Yellow, White, Black", "08-09-2023", "white"));
        list.add(new AdoptStatusItem(R.drawable.cat1, "con ghe so 1", "Small", "Yellow, White, Black", "08-09-2023", "white"));
        list.add(new AdoptStatusItem(R.drawable.cat1, "con ghe so 2", "Average", "Yellow, White, Black", "08-09-2023", "black"));
        list.add(new AdoptStatusItem(R.drawable.cat1, "con ghe so 3", "Small", "Yellow, White, Black", "08-09-2023", "pink"));

        System.out.println(list);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyShopActivity.class));
            }
        });
        recyclerView = findViewById(R.id.recycle_adopt_view);
        AdoptStatusAdapter statusAdapter = new AdoptStatusAdapter(list,this);
        recyclerView.setAdapter(statusAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));


    }
}