package com.example.petbox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;


public class AgeFragment extends Fragment implements View.OnClickListener {

    private String age;
    private int position= 3;
    ControllerFilter controllerFilter = ControllerFilter.getController();

    public enum Ages{
        SMALL(0,5," м"),
        MEDIUM(6,11," м"),
        ADULT(1,10, " г");
        private int fromAge,toAge;
        String measure;
        Ages(int fromAge,int toAge,String measure){
            this.fromAge=fromAge;
            this.toAge=toAge;
            this.measure=measure;
        }

        public int getFromAge() {
            return fromAge;
        }

        public int getToAge() {
            return toAge;
        }

        public String getMeasure() {
            return measure;
        }
    };

    interface Back{
        public void goBack(Class cls);
    }
    private Back back;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        back = (Back) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_age, container, false);

        RadioButton
                small = view.findViewById(R.id.radio_small),
                medium = view.findViewById(R.id.radio_medium),
                adult = view.findViewById(R.id.radio_adult);
        small.setOnClickListener(this);
        medium.setOnClickListener(this);
        adult.setOnClickListener(this);

        Button acceptFilter = view.findViewById(R.id.accept_btn);
        acceptFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 3){back.goBack(this.getClass());}
                else {
                    int from_age = Ages.values()[position].getFromAge();
                    int to_age = Ages.values()[position].getToAge();
                    String measure = Ages.values()[position].getMeasure();

                    controllerFilter.setFromAge(from_age);
                    controllerFilter.setToAge(to_age);
                    controllerFilter.setAgeMeasure(measure);
                }
            }
        });

        ImageButton back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.goBack(AgeFragment.class);
            }
        });

        return view;
    }
    @Override
    public void onClick(View view) {
        RadioButton radioButton = (RadioButton)view;
        switch (radioButton.getId()){
            case R.id.radio_small:position=0;
            break;
            case R.id.radio_medium:position=1;
            break;
            case R.id.radio_adult:position=2;
            break;
        }
    }

}