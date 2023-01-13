package com.example.petbox;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DeleteDialogFragment extends DialogFragment {

   private HelpAd helpAd;
   private Animal animal;
    interface Delete{
        public void deleteHelpAdDialog(HelpAd helpAd);
        public void deleteAnimalDialog(Animal animal);
    }

    private Delete delete;

    DeleteDialogFragment(HelpAd helpAd){
        this.helpAd = helpAd;
    }
    DeleteDialogFragment(Animal animal){this.animal = animal;}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        delete = (Delete) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Вы действительно хотите удалить?")
                .setMessage("После удаления материал восстановить будет невозможно")
                .setPositiveButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрываем диалоговое окно
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(animal == null)
                        delete.deleteHelpAdDialog(helpAd);
                        else delete.deleteAnimalDialog(animal);
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}