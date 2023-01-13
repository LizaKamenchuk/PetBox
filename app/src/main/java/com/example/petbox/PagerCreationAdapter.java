package com.example.petbox;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerCreationAdapter extends FragmentStateAdapter {

    public PagerCreationAdapter(FragmentActivity fa){
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CreateAnimalFragment();
            case 1:
                return new CreateHelpAdFragment();
            default: return new CreateAnimalFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
