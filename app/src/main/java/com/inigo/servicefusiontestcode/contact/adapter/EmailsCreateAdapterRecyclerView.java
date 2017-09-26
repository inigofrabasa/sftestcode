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
import com.inigo.servicefusiontestcode.contact.model.Emails;
import com.inigo.servicefusiontestcode.contact.presenter.CreateUpdateContactPresenter;

/**
 * Created by Inigo on 25/09/17.
 */

public class EmailsCreateAdapterRecyclerView extends RecyclerView.Adapter<EmailsCreateAdapterRecyclerView.EmailCreateViewHolder>{

    private Emails emails;
    private int resource;
    private Activity activity;

    private CreateUpdateContactPresenter createUpdateContactPresenter;

    public EmailsCreateAdapterRecyclerView(Emails emails, int resource, Activity activity) {
        this.emails = emails;
        this.resource = resource;
        this.activity = activity;
    }

    public void setCreateUpdateContactPresenter(CreateUpdateContactPresenter createUpdateContactPresenter){
        this.createUpdateContactPresenter = createUpdateContactPresenter;
    }

    @Override
    public EmailsCreateAdapterRecyclerView.EmailCreateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new EmailsCreateAdapterRecyclerView.EmailCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EmailsCreateAdapterRecyclerView.EmailCreateViewHolder holder, final int position) {

        holder.email.setText(emails.getEmail(position));
        holder.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emails.setEmail(position, holder.email.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.deleteEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emails.deleteEmail(position);
                createUpdateContactPresenter.emailsDataChange(emails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return emails.getEmails().size();
    }

    public interface OnEmailsDataChange{
        void emailsDataChange(Emails emails);
    }

    public class EmailCreateViewHolder extends RecyclerView.ViewHolder{

        private TextView emailText;
        private EditText email;
        private Button deleteEmail;

        public EmailCreateViewHolder(View itemView) {
            super(itemView);

            emailText   = (TextView)itemView.findViewById(R.id.contactNameCard);
            email = (EditText)itemView.findViewById(R.id.et_editEmail);
            deleteEmail = (Button)itemView.findViewById(R.id.bt_deleteEmail);

        }
    }
}
