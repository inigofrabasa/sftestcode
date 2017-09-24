package com.inigo.servicefusiontestcode.contact.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.inigo.servicefusiontestcode.R;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.presenter.ContactPresenter;
import com.inigo.servicefusiontestcode.contacts.view.MainActivity;

public class ContactActivity extends AppCompatActivity implements ContactPresenter.View{

    public static final String CONTACT_ID = "contactId";
    public static final String UPDATE_CONTACT = "updateContact";

    private  Toolbar toolbar;

    private TextView name;
    private TextView lastname;

    private ContactPresenter contactPresenter;
    private String contantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        toolbar     = (Toolbar) findViewById(R.id.toolbar);
        name        = (TextView)findViewById(R.id.tv_name);
        lastname    = (TextView)findViewById(R.id.tv_lastname);

        setSupportActionBar(toolbar);

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
            contantId = contact.getId().toString();
            name.setText(contact.getName());
            lastname.setText(contact.getLastName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intent = new Intent(getBaseContext(), CreateUpdateContactActivity.class);
            intent.putExtra(CONTACT_ID, contantId);
            intent.putExtra(UPDATE_CONTACT, true);
            startActivity(intent);
        }
        else if(id == R.id.action_delete){
            AlertDialog.Builder alert = new AlertDialog.Builder(ContactActivity.this);
            alert.setTitle("Delete");
            alert.setMessage("Are you sure you want to delete?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    contactPresenter.deleteContact();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra(MainActivity.CONTACT_DELETED, MainActivity.DELETED);
                            startActivity(intent);
                }
            });

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
