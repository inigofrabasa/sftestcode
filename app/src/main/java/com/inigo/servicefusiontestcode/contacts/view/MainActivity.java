package com.inigo.servicefusiontestcode.contacts.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inigo.servicefusiontestcode.R;
import com.inigo.servicefusiontestcode.contacts.adapter.ContactsAdapterRecyclerView;
import com.inigo.servicefusiontestcode.contacts.model.Contacts;
import com.inigo.servicefusiontestcode.contacts.presenter.MainPresenter;
import com.inigo.servicefusiontestcode.contact.view.CreateContactActivity;

public class MainActivity extends AppCompatActivity implements MainPresenter.View{

    public static final String CONTACT_CREATED = "contactCreated";
    public static final String CREATED = "Contact Created.";
    public static final String CONTACT_ID = "contactId";

    private MainPresenter mainPresenter;
    private RecyclerView contacstRecycler;
    private ContactsAdapterRecyclerView contactsAdapterRecyclerView;

    private EditText editTextSearch;
    private Button clearSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().hasExtra(CONTACT_CREATED)){
            if(getIntent().getStringExtra(CONTACT_CREATED).equals(CREATED)){
            Toast.makeText(getApplicationContext(), CREATED, Toast.LENGTH_SHORT).show();
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        editTextSearch = (EditText) findViewById(R.id.et_searchContact);
        clearSearch = (Button) findViewById(R.id.bt_clearSearch);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CreateContactActivity.class);
                //intent.putExtra("Key", item);
                startActivity(intent);
            }
        });

        contacstRecycler = (RecyclerView)findViewById(R.id.contactsRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        contacstRecycler.setLayoutManager(linearLayoutManager);

        mainPresenter = new MainPresenter(this);

        //Search
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editTextSearch.getText().toString();

                if(text.equals("")){
                    clearSearch.setVisibility(View.GONE);
                    mainPresenter.bindAllContacts();
                }else{
                    clearSearch.setVisibility(View.VISIBLE);
                    mainPresenter.searchContact(text);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSearch.setText("");
                editTextSearch.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                clearSearch.setVisibility(View.GONE);
                mainPresenter.bindAllContacts();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        mainPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void bindData(Contacts inContacts){
        contactsAdapterRecyclerView
                = new ContactsAdapterRecyclerView(inContacts,
                R.layout.contact_cardview, this);

        contacstRecycler.setAdapter(contactsAdapterRecyclerView);
        contacstRecycler.invalidate();
    }
}
