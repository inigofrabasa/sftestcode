package com.inigo.servicefusiontestcode.contact.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inigo.servicefusiontestcode.R;
import com.inigo.servicefusiontestcode.contact.model.Addresses;

/**
 * Created by Inigo on 25/09/17.
 */

public class AddressViewAdapterRecyclerView extends RecyclerView.Adapter<AddressViewAdapterRecyclerView.AddressViewHolder> {
    private Addresses addresses;
    private int resource;
    private Activity activity;

    public AddressViewAdapterRecyclerView(Addresses addresses, int resource, Activity activity) {
        this.addresses = addresses;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, final int position) {
        holder.addressText.setText(addresses.getAddress(position));
    }

    @Override
    public int getItemCount() {
        return addresses.getAddresses().size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder{

        private TextView addressText;

        public AddressViewHolder(View itemView) {
            super(itemView);
            addressText = (TextView)itemView.findViewById(R.id.tv_viewAddress);

        }
    }
}