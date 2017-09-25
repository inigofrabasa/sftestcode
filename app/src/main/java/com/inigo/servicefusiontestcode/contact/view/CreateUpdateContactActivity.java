package com.inigo.servicefusiontestcode.contact.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inigo.servicefusiontestcode.contact.adapter.PhoneAdapterRecyclerView;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.model.Phones;
import com.inigo.servicefusiontestcode.contact.presenter.CreateUpdateContactPresenter;
import com.inigo.servicefusiontestcode.contacts.view.MainActivity;
import com.inigo.servicefusiontestcode.R;

public class CreateUpdateContactActivity extends AppCompatActivity implements CreateUpdateContactPresenter.View{

    public static final String CONTACT_ID = "contactId";
    public static final String CONTACT_NAME = "contactName";
    public static final String CONTACT_LASTNAME = "contactLastName";

    private Boolean createContact = true;

    private EditText name;
    private EditText lastName;
    private Button saveButton;
    private Button addPhone;

    private CreateUpdateContactPresenter createUpdateContactPresenter;

    private RecyclerView phonesRecycler;
    private PhoneAdapterRecyclerView phoneAdapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_contact);

        createUpdateContactPresenter = new CreateUpdateContactPresenter(this);

        name =      (EditText)findViewById(R.id.et_contactName);
        lastName =  (EditText)findViewById(R.id.et_contactLastName);
        addPhone =  (Button)findViewById(R.id.addPhone);

        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdateContactPresenter.initPhone();
            }
        });

        phonesRecycler = (RecyclerView)findViewById(R.id.editPhonesRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        phonesRecycler.setLayoutManager(linearLayoutManager);

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
        }
    }

    @Override
    public void bindData(Contact contact) {
        name.setText(contact.getName());
        lastName.setText(contact.getLastName());
    }

    @Override
    public void bindPhones(Phones phones) {
        phoneAdapterRecyclerView
                = new PhoneAdapterRecyclerView(phones,
                R.layout.phone_item_edit, this);
        phoneAdapterRecyclerView.setCreateUpdateContactPresenter(createUpdateContactPresenter);

        phonesRecycler.setAdapter(phoneAdapterRecyclerView);
        phonesRecycler.invalidate();
    }
}
