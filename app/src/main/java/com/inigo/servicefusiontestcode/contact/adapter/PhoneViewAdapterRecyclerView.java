package com.inigo.servicefusiontestcode.contact.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inigo.servicefusiontestcode.R;
import com.inigo.servicefusiontestcode.contact.model.Phones;

/**
 * Created by Inigo on 24/09/17.
 */

public class PhoneViewAdapterRecyclerView extends RecyclerView.Adapter<PhoneViewAdapterRecyclerView.PhoneViewHolder> {
    private Phones phones;
    private int resource;
    private Activity activity;

    public PhoneViewAdapterRecyclerView(Phones phones, int resource, Activity activity) {
        this.phones = phones;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public PhoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PhoneViewAdapterRecyclerView.PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhoneViewHolder holder, final int position) {
        holder.phoneText.setText(phones.getPhone(position));
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Loading Call...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phones.getPhone(position)));
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    activity.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return phones.getPhones().size();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder{

        private TextView phoneText;
        private Button callButton;

        public PhoneViewHolder(View itemView) {
            super(itemView);
            phoneText   = (TextView)itemView.findViewById(R.id.tv_viewPhone);
            callButton  = (Button)itemView.findViewById(R.id.bt_callPhone);

        }
    }
}
