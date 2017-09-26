package com.inigo.servicefusiontestcode.contactslist.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inigo.servicefusiontestcode.R;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.view.ContactActivity;
import com.inigo.servicefusiontestcode.contactslist.model.Contacts;
import com.inigo.servicefusiontestcode.contactslist.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Inigo on 21/09/17.
 */

public class ContactsAdapterRecyclerView extends RecyclerView.Adapter<ContactsAdapterRecyclerView.ContactsViewHolder>{
    private static Contacts contacts;
    private int resource;
    private Activity activity;
    private List<Character> letters;

    public static Contacts getContacts() {
        return contacts;
    }

    public ContactsAdapterRecyclerView(Contacts contacts, int resource, Activity activity) {
        this.contacts = contacts;
        this.resource = resource;
        this.activity = activity;

        letters = new ArrayList<>();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        final Contact contact = contacts.getContact(position);

        holder.contactName.setText(contact.getName().toString());
        holder.contactLastName.setText(contact.getLastName().toString());

        if(letters.size() == 0){
            letters.add(contact.getName().charAt(0));
            holder.header.setVisibility(View.VISIBLE);
            holder.headerText.setText(letters.get(0).toString().toUpperCase());
        }
        else{
            if(!letters.get(letters.size() - 1).equals(contact.getName().charAt(0))){
                letters.add(contact.getName().charAt(0));
                holder.header.setVisibility(View.VISIBLE);
                holder.headerText.setText(letters.get(letters.size() - 1).toString().toUpperCase());
            }
        }

        holder.initialsContact.setText(Character.toString(contact.getName().charAt(0)) +
                Character.toString(contact.getLastName().charAt(0)));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ContactActivity.class);
                intent.putExtra(MainActivity.CONTACT_ID, contact.getId().toString());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.getContacts().size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView contactImage;
        private TextView contactName;
        private TextView contactLastName;

        private View container;
        private View header;
        private TextView headerText;
        private TextView initialsContact;

        public ContactsViewHolder(View itemView) {
            super(itemView);

            contactImage          = (CircleImageView)itemView.findViewById(R.id.contactImageCard);
            contactName           = (TextView)itemView.findViewById(R.id.contactNameCard);
            contactLastName       = (TextView)itemView.findViewById(R.id.contactLastNameCard);
            container             = itemView.findViewById(R.id.containerCard);
            header                = itemView.findViewById(R.id.header_section);
            headerText            = (TextView)itemView.findViewById(R.id.contactHeaderText);
            initialsContact       = (TextView)itemView.findViewById(R.id.initialsContactCircle);
        }
    }
}
