package com.inigo.servicefusiontestcode.contact.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inigo.servicefusiontestcode.contact.model.Contact;
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
    private Button button;

    private CreateUpdateContactPresenter createUpdateContactPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_contact);

        createUpdateContactPresenter = new CreateUpdateContactPresenter(this);

        name = (EditText)findViewById(R.id.et_contactName);
        lastName = (EditText)findViewById(R.id.et_contactLastName);

        button = (Button)findViewById(R.id.bt_saveContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                bundle.putString(CONTACT_NAME, name.getText().toString());
                bundle.putString(CONTACT_LASTNAME, lastName.getText().toString());

                if(createContact){
                    if(createUpdateContactPresenter.tryCreateContact(bundle)){

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra(MainActivity.CONTACT_CREATED, MainActivity.CREATED);
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
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "Please complete information!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        if(getIntent().hasExtra(ContactActivity.UPDATE_CONTACT))
            if(getIntent().getExtras().getBoolean(ContactActivity.UPDATE_CONTACT)){
                createContact = false;
                createUpdateContactPresenter.obtainContact(getIntent().getStringExtra(ContactActivity.CONTACT_ID));
            }
    }

    @Override
    public void bindData(Contact contact) {
        name.setText(contact.getName());
        lastName.setText(contact.getLastName());
    }
}
