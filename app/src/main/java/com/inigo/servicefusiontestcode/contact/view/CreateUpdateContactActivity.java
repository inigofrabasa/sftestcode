package com.inigo.servicefusiontestcode.contact.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inigo.servicefusiontestcode.contact.adapter.AddressesCreateAdapterRecyclerView;
import com.inigo.servicefusiontestcode.contact.adapter.EmailsCreateAdapterRecyclerView;
import com.inigo.servicefusiontestcode.contact.adapter.PhonesCreateAdapterRecyclerView;
import com.inigo.servicefusiontestcode.contact.model.Addresses;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.model.Emails;
import com.inigo.servicefusiontestcode.contact.model.Phones;
import com.inigo.servicefusiontestcode.contact.presenter.CreateUpdateContactPresenter;
import com.inigo.servicefusiontestcode.contactslist.view.MainActivity;
import com.inigo.servicefusiontestcode.R;

public class CreateUpdateContactActivity
        extends AppCompatActivity implements CreateUpdateContactPresenter.View,
        NavigationView.OnNavigationItemSelectedListener{

    public static final String CONTACT_ID = "contactId";
    public static final String CONTACT_NAME = "contactName";
    public static final String CONTACT_LASTNAME = "contactLastName";

    private Boolean createContact = true;

    private Toolbar toolbar;

    private EditText name;
    private EditText lastName;
    private Button saveButton;
    private Button addPhone;
    private Button addAddress;
    private Button addEmail;

    private CreateUpdateContactPresenter createUpdateContactPresenter;

    private RecyclerView phonesRecycler;
    private PhonesCreateAdapterRecyclerView phonesCreateAdapterRecyclerView;

    private RecyclerView addressesRecycler;
    private AddressesCreateAdapterRecyclerView addressesCreateAdapterRecyclerView;

    private RecyclerView emailsRecycler;
    private EmailsCreateAdapterRecyclerView emailsCreateAdapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_contact);

        createUpdateContactPresenter = new CreateUpdateContactPresenter(this);

        toolbar     = (Toolbar) findViewById(R.id.toolbar);

        name =      (EditText)findViewById(R.id.et_contactName);
        lastName =  (EditText)findViewById(R.id.et_contactLastName);
        addPhone =  (Button)findViewById(R.id.addPhone);
        addAddress =  (Button)findViewById(R.id.addAddress);
        addEmail =  (Button)findViewById(R.id.addEmail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdateContactPresenter.initPhone();
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdateContactPresenter.initAddress();
            }
        });

        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdateContactPresenter.initEmail();
            }
        });

        phonesRecycler = (RecyclerView)findViewById(R.id.editPhonesRecycler);
        addressesRecycler = (RecyclerView)findViewById(R.id.editAddressesRecycler);
        emailsRecycler = (RecyclerView)findViewById(R.id.editEmailsRecycler);

        LinearLayoutManager linearLayoutManagerPhones = new LinearLayoutManager(this);
        linearLayoutManagerPhones.setOrientation(LinearLayoutManager.VERTICAL);
        phonesRecycler.setLayoutManager(linearLayoutManagerPhones);

        LinearLayoutManager linearLayoutManagerAddresses = new LinearLayoutManager(this);
        linearLayoutManagerAddresses.setOrientation(LinearLayoutManager.VERTICAL);
        addressesRecycler.setLayoutManager(linearLayoutManagerAddresses);

        LinearLayoutManager linearLayoutManagerEmails = new LinearLayoutManager(this);
        linearLayoutManagerEmails.setOrientation(LinearLayoutManager.VERTICAL);
        emailsRecycler.setLayoutManager(linearLayoutManagerEmails);

        saveButton = (Button)findViewById(R.id.bt_saveContact);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                bundle.putString(CONTACT_NAME, name.getText().toString());
                bundle.putString(CONTACT_LASTNAME, lastName.getText().toString());

                if(createContact){
                    if(createUpdateContactPresenter.tryCreateContact(bundle)){

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra(MainActivity.CONTACT_CREATED, MainActivity.CREATED);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "Please complete information!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    bundle.putString(CONTACT_ID, getIntent().getStringExtra(ContactActivity.CONTACT_ID));
                    if(createUpdateContactPresenter.tryUpdateContact(bundle)){

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra(MainActivity.CONTACT_CREATED, MainActivity.CREATED);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "Please complete information!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        if(getIntent().hasExtra(ContactActivity.UPDATE_CONTACT)){
            if(getIntent().getExtras().getBoolean(ContactActivity.UPDATE_CONTACT)){
                createContact = false;
                createUpdateContactPresenter.obtainContact(getIntent().getStringExtra(ContactActivity.CONTACT_ID));
            }
        }
        else{
            createUpdateContactPresenter.initPhone();
            createUpdateContactPresenter.initAddress();
            createUpdateContactPresenter.initEmail();
        }
    }

    @Override
    public void bindData(Contact contact) {
        name.setText(contact.getName());
        lastName.setText(contact.getLastName());
    }

    @Override
    public void bindPhones(Phones phones) {
        phonesCreateAdapterRecyclerView
                = new PhonesCreateAdapterRecyclerView(phones,
                R.layout.phone_item_edit, this);
        phonesCreateAdapterRecyclerView.setCreateUpdateContactPresenter(createUpdateContactPresenter);

        phonesRecycler.setAdapter(phonesCreateAdapterRecyclerView);
        phonesRecycler.invalidate();
    }

    @Override
    public void bindAddresses(Addresses addresses) {
        addressesCreateAdapterRecyclerView
                = new AddressesCreateAdapterRecyclerView(addresses,
                R.layout.address_item_edit, this);
        addressesCreateAdapterRecyclerView.setCreateUpdateContactPresenter(createUpdateContactPresenter);

        addressesRecycler.setAdapter(addressesCreateAdapterRecyclerView);
        addressesRecycler.invalidate();
    }

    @Override
    public void bindEmails(Emails emails) {
        emailsCreateAdapterRecyclerView
                = new EmailsCreateAdapterRecyclerView(emails,
                R.layout.email_item_edit, this);
        emailsCreateAdapterRecyclerView.setCreateUpdateContactPresenter(createUpdateContactPresenter);

        emailsRecycler.setAdapter(emailsCreateAdapterRecyclerView);
        emailsRecycler.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
