package com.example.petbox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerScrollAdapter extends FragmentStateAdapter {
    private String email;
    public PagerScrollAdapter(FragmentActivity fa, String email){
        super(fa);
        this.email = email;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                System.out.println("aniamal");
                return new ScrollAnimalsMeFragment(email);
            case 1:
                System.out.println("help");
                 return new ScrollHelpAdMeFragment(email);
            default:
                return new ScrollAnimalsMeFragment(email);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
