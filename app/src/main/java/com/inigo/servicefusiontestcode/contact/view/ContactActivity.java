package com.inigo.servicefusiontestcode.contact.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.inigo.servicefusiontestcode.R;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.presenter.ContactPresenter;
import com.inigo.servicefusiontestcode.contacts.view.MainActivity;

public class ContactActivity extends AppCompatActivity implements ContactPresenter.View{

    private TextView name;
    private TextView lastname;

    private ContactPresenter contactPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        name        = (TextView)findViewById(R.id.tv_name);
        lastname    = (TextView)findViewById(R.id.tv_lastname);

        if(getIntent().hasExtra(MainActivity.CONTACT_ID)){
            Bundle bundle = new Bundle();
            bundle.putString(MainActivity.CONTACT_ID, (getIntent().getStringExtra(MainActivity.CONTACT_ID)));
            contactPresenter = new ContactPresenter(this);
            contactPresenter.obtainContact(bundle);
        }
    }

    @Override
    public void bindData(Contact contact) {
        if (contact != null){
            name.setText(contact.getName());
            lastname.setText(contact.getLastName());
        }
    }
}
