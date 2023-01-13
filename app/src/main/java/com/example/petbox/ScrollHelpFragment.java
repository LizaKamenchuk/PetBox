package com.example.petbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScrollHelpFragment extends Fragment implements View.OnClickListener{
    ArrayList<HelpAd> helpAds = new ArrayList<HelpAd>();

    public interface goToHelpAd {
        public void goToHelpAd(HelpAd helpAd);
    }
    private ScrollHelpFragment.goToHelpAd goToHelpAd;

    ScrollHelpFragment(ArrayList<HelpAd> helpAds){
        this.helpAds = helpAds;
    }

    ScrollHelpFragment(){
        setInitialData();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_bar, container, false);
        // получаем элемент ListView
        RecyclerView recyclerView = view.findViewById(R.id.scrollanimalsList);
        // создаем адаптер
        HelpAdAdapter.OnHelpClickListener helpClickListener = new HelpAdAdapter.OnHelpClickListener() {
            @Override
            public void onHelpAdClick(int position) {
                System.out.println("fragment");
            }
        };
        HelpAdAdapter adapter = new HelpAdAdapter(getContext(),helpAds,helpClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);


        Button
                filter_btn = view.findViewById(R.id.filter_btn),
                sort_btn = view.findViewById(R.id.sort_btn);
        filter_btn.setOnClickListener(this);
        sort_btn.setOnClickListener(this);

        return view;//inflater.inflate(R.layout.fragment_scroll_animals, container, false);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.filter_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_place, new FilterForHelpFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.sort_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_place, new SortForHelpAdsFragment())
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

    private void setInitialData() {
        System.out.println("set Initial Data HelpAds");
       this.helpAds = ControllerDB.getController().getHelpAdsList();
    }

}
