package com.inigo.servicefusiontestcode.contact.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inigo.servicefusiontestcode.R;
import com.inigo.servicefusiontestcode.contact.model.Addresses;
import com.inigo.servicefusiontestcode.contact.presenter.CreateUpdateContactPresenter;

/**
 * Created by Inigo on 25/09/17.
 */

public class AddressesCreateAdapterRecyclerView
        extends RecyclerView.Adapter<AddressesCreateAdapterRecyclerView.AddressCreateViewHolder>{

    private Addresses addresses;
    private int resource;
    private Activity activity;

    private CreateUpdateContactPresenter createUpdateContactPresenter;

    public AddressesCreateAdapterRecyclerView(Addresses addresses, int resource, Activity activity) {
        this.addresses = addresses;
        this.resource = resource;
        this.activity = activity;
    }

    public void setCreateUpdateContactPresenter(CreateUpdateContactPresenter createUpdateContactPresenter){
        this.createUpdateContactPresenter = createUpdateContactPresenter;
    }

    @Override
    public AddressesCreateAdapterRecyclerView.AddressCreateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new AddressesCreateAdapterRecyclerView.AddressCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddressesCreateAdapterRecyclerView.AddressCreateViewHolder holder, final int position) {

        holder.address.setText(addresses.getAddress(position));
        holder.address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addresses.setAddress(position, holder.address.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addresses.deleteAddress(position);
                createUpdateContactPresenter.addressesDataChange(addresses);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addresses.getAddresses().size();
    }

    public interface OnAddressesDataChange{
        void addressesDataChange(Addresses addresses);
    }

    public class AddressCreateViewHolder extends RecyclerView.ViewHolder{

        private TextView addressText;
        private EditText address;
        private Button deleteAddress;

        public AddressCreateViewHolder(View itemView) {
            super(itemView);

            addressText   = (TextView)itemView.findViewById(R.id.contactNameCard);
            address = (EditText)itemView.findViewById(R.id.et_editAddress);
            deleteAddress = (Button)itemView.findViewById(R.id.bt_deleteAddress);
        }
    }
}
