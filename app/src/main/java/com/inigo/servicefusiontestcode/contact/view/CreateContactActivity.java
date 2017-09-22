package com.inigo.servicefusiontestcode.contact.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inigo.servicefusiontestcode.contact.presenter.CreateContactPresenter;
import com.inigo.servicefusiontestcode.contacts.view.MainActivity;
import com.inigo.servicefusiontestcode.R;

public class CreateContactActivity extends AppCompatActivity {

    public static final String CONTACT_NAME = "contactName";
    public static final String CONTACT_LASTNAME = "contactLastName";

    private EditText name;
    private EditText lastName;
    private Button button;

    private CreateContactPresenter createContactPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        createContactPresenter = new CreateContactPresenter(this);

        name = (EditText)findViewById(R.id.et_contactName);
        lastName = (EditText)findViewById(R.id.et_contactLastName);
        button = (Button)findViewById(R.id.bt_saveContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                bundle.putString(CONTACT_NAME, name.getText().toString());
                bundle.putString(CONTACT_LASTNAME, lastName.getText().toString());

                if(createContactPresenter.tryCreateContact(bundle)){

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra(MainActivity.CONTACT_CREATED, MainActivity.CREATED);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Please complete information!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
