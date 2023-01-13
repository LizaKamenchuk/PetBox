package com.example.petbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HelpAdAdapter extends RecyclerView.Adapter<HelpAdAdapter.ViewHolder>  {
    private final LayoutInflater inflater;
    private final List<HelpAd> helpAds ;

    interface OnHelpClickListener{
        void onHelpAdClick(int position);
    }

    private  HelpAdAdapter.OnHelpClickListener onClickListener;

    HelpAdAdapter(Context context, ArrayList<HelpAd> helpAds, HelpAdAdapter.OnHelpClickListener onClickListener) {
        this.helpAds = helpAds;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    HelpAdAdapter(Context context,ArrayList<HelpAd> helpAds){
        this.helpAds = helpAds;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.help_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HelpAdAdapter.ViewHolder viewHolder, int position) {
        position=viewHolder.getAdapterPosition();
        HelpAd helpAd = helpAds.get(position);

        viewHolder.typeView.setText(helpAd.getType());
        viewHolder.descriptionView.setText(helpAd.getDescription());
        viewHolder.addressView.setText("Меcто: "+helpAd.getShelterName()+", "+helpAd.getAddress());
        viewHolder.contactView.setText("Контакты: "+helpAd.getContactName()+",  эл. почта: "+helpAd.getContactEmail());

        int finalPosition = position;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onHelpAdClick(finalPosition);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return helpAds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView typeView, descriptionView, addressView,contactView;

        public ViewHolder(View view) {
            super(view);
            typeView = view.findViewById(R.id.typeOfHelp);
            descriptionView = view.findViewById(R.id.helpDescription);
            addressView = view.findViewById(R.id.addressForHelp);
            contactView = view.findViewById(R.id.contactsForHelp);
        }
    }
}
