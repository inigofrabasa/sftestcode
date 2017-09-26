package com.inigo.servicefusiontestcode.contact.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inigo.servicefusiontestcode.R;
import com.inigo.servicefusiontestcode.contact.model.Emails;

/**
 * Created by Inigo on 25/09/17.
 */

public class EmailsViewAdapterRecyclerView extends RecyclerView.Adapter<EmailsViewAdapterRecyclerView.EmailViewHolder> {
    private Emails emails;
    private int resource;
    private Activity activity;

    public EmailsViewAdapterRecyclerView(Emails emails, int resource, Activity activity) {
        this.emails = emails;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public EmailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new EmailsViewAdapterRecyclerView.EmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmailViewHolder holder, final int position) {
        holder.emailText.setText(emails.getEmail(position));
        holder.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Loading Message...", Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                activity.startActivity(emailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return emails.getEmails().size();
    }

    public class EmailViewHolder extends RecyclerView.ViewHolder{

        private TextView emailText;
        private Button emailButton;

        public EmailViewHolder(View itemView) {
            super(itemView);
            emailText = (TextView)itemView.findViewById(R.id.tv_viewEmail);
            emailButton = (Button)itemView.findViewById(R.id.bt_sendMail);

        }
    }
}
