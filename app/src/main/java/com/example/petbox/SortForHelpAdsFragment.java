package com.example.petbox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.util.ArrayList;

public class SortForHelpAdsFragment extends Fragment implements View.OnClickListener{

    ControllerDB controllerDB = ControllerDB.getController();
    ArrayList<HelpAd> helpAds;
    int i = 2;
    interface CallScrollFragment{
        public void callScrollHelpAdsFragment();
    }

    private CallScrollFragment callScrollFragment;

    interface Back{
        public void goBack(Class cls);
    }

    private Back back;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callScrollFragment = (CallScrollFragment) context;
        back = (Back) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_for_help_ads, container, false);

        RadioButton
                newAds = view.findViewById(R.id.radio_alphabet),
                oldAds = view.findViewById(R.id.radio_urgency);
        newAds.setOnClickListener(this);
        oldAds.setOnClickListener(this);

        ImageButton back = view.findViewById(R.id.back_btn);
        back.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.radio_alphabet:
                i=0;
                break;
            case R.id.radio_urgency:
                i=1;
                break;
            case R.id.back_btn:
                if(i == 2) back.goBack(SortForHelpAdsFragment.class);
                else {
                    if (i == 0) {
                        helpAds = ControllerDB.sortAlphabet(controllerDB.getHelpAdsList());
                        controllerDB.setHelpAdsList(helpAds);
                    } else if (i == 1) {
                        helpAds = ControllerDB.sortFirstUrgency(controllerDB.getHelpAdsList());
                        controllerDB.setHelpAdsList(helpAds);
                    }
                    callScrollFragment.callScrollHelpAdsFragment();
                }
                break;
        }
    }
}