package com.example.pet_finder_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pet_finder_app.fragments.AppointmentAdopt;
import com.example.pet_finder_app.fragments.RequestAdopt;

public class TabAdoptAdapter extends FragmentStateAdapter {
    public TabAdoptAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RequestAdopt();
            case 1:
                return new AppointmentAdopt();
            default:
                return new RequestAdopt();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
