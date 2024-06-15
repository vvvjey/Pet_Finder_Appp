package com.example.pet_finder_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pet_finder_app.fragments.AdoptionPolicy;
import com.example.pet_finder_app.fragments.OwnerPet;

public class TabOwnerLayout extends FragmentStateAdapter {
    public TabOwnerLayout(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new OwnerPet();
            case 1:
                return new AdoptionPolicy();
            default:
                return new OwnerPet();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
