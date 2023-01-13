package com.example.petbox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class ColorFragment extends Fragment implements View.OnClickListener {

    private enum Colors{
        ORANGE ("рыжий"),
        WHITE ("белый"),
        GRAY("серый"),
        BLACK ("чёрный"),
        TURTLE ("разноцветный");

        private String colorName;

        Colors(String colorName) {
            this.colorName=colorName;
        }

        public String getColorName() {
            return colorName;
        }
    }
    ArrayList<String> colors = new ArrayList<>();

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
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_color, container, false);

        CheckBox
                orange = view.findViewById(R.id.checkbox_orange),
                white = view.findViewById(R.id.checkbox_white),
                gray = view.findViewById(R.id.checkbox_gray),
                black = view.findViewById(R.id.checkbox_black),
                turtle = view.findViewById(R.id.checkbox_turtle);
        orange.setOnClickListener(this);
        white.setOnClickListener(this);
        gray.setOnClickListener(this);
        black.setOnClickListener(this);
        turtle.setOnClickListener(this);

        Button acceptFilter = view.findViewById(R.id.accept_btn);
        acceptFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colors.isEmpty()) back.goBack(this.getClass());
                else ControllerFilter.getController().setColors(colors);
            }
        });

        ImageButton back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.goBack(ColorFragment.class);
            }
        });

        return view;
    }
    @Override
    public void onClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        CheckBox checkBox = (CheckBox) view;
        switch (checkBox.getId()){
            case R.id.checkbox_orange:
                if(checked) colors.add(Colors.ORANGE.getColorName());
                else colors.remove(Colors.ORANGE.getColorName());
                break;
            case R.id.checkbox_white:if(checked) colors.add(Colors.WHITE.getColorName());
            else colors.remove(Colors.WHITE.getColorName());
                break;
            case R.id.checkbox_gray:if(checked) colors.add(Colors.GRAY.getColorName());
            else colors.remove(Colors.GRAY.getColorName());
            break;
            case R.id.checkbox_black:if(checked) colors.add(Colors.BLACK.getColorName());
            else colors.remove(Colors.BLACK.getColorName());
                break;
            case R.id.checkbox_turtle:if(checked) colors.add(Colors.TURTLE.getColorName());
            else colors.remove(Colors.TURTLE.getColorName());
                break;
        }
    }
}