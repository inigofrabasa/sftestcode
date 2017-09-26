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
import com.inigo.servicefusiontestcode.contact.model.Phones;
import com.inigo.servicefusiontestcode.contact.presenter.CreateUpdateContactPresenter;

/**
 * Created by Inigo on 24/09/17.
 */

public class PhonesCreateAdapterRecyclerView
        extends RecyclerView.Adapter<PhonesCreateAdapterRecyclerView.PhoneCreateViewHolder>{

    private Phones phones;
    private int resource;
    private Activity activity;

    private CreateUpdateContactPresenter createUpdateContactPresenter;

    public PhonesCreateAdapterRecyclerView(Phones phones, int resource, Activity activity) {
        this.phones = phones;
        this.resource = resource;
        this.activity = activity;
    }

    public void setCreateUpdateContactPresenter(CreateUpdateContactPresenter createUpdateContactPresenter){
        this.createUpdateContactPresenter = createUpdateContactPresenter;
    }

    @Override
    public PhoneCreateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PhoneCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhoneCreateViewHolder holder, final int position) {

        holder.phoneNumber.setText(phones.getPhone(position));
        holder.phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phones.setPhone(position, holder.phoneNumber.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.deletePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phones.deletePhone(position);
                createUpdateContactPresenter.phonesDataChange(phones);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phones.getPhones().size();
    }

    public interface OnPhonesDataChange{
        void phonesDataChange(Phones phones);
    }

    public class PhoneCreateViewHolder extends RecyclerView.ViewHolder{

        private TextView phoneText;
        private EditText phoneNumber;
        private Button deletePhone;

        public PhoneCreateViewHolder(View itemView) {
            super(itemView);

            phoneText   = (TextView)itemView.findViewById(R.id.contactNameCard);
            phoneNumber = (EditText)itemView.findViewById(R.id.et_editPhone);
            deletePhone = (Button)itemView.findViewById(R.id.bt_deletePhone);

        }
    }
}
