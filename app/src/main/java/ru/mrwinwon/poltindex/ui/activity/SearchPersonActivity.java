package ru.mrwinwon.poltindex.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.AdapterPersonSearch;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.util.Const;

public class SearchPersonActivity extends AppCompatActivity {

    private HashMap<String, String> items;
    private AdapterPersonSearch personsAdapter;
    private ArrayList<String> personsNames;
    private ArrayList<Person> people;

    private ListView personSearch;
    private EditText personFinder;
    private Context context;

    private ImageButton ibProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_person);

        init();
    }

    private void init() {
        context = this;
        // Данные приложения
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            items = (HashMap<String, String>) bundle.getSerializable(Const.APPLICATION_ITEMS);
            people = (ArrayList<Person>) bundle.getSerializable(Const.APPLICATION_PEOPLE);
        }

        personsAdapter = new AdapterPersonSearch(this, 0, people);
        personSearch = findViewById(R.id.lv_person_search);
        personSearch.setAdapter(personsAdapter);
        personSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, BiographyActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelable(Const.APPLICATION_PERSON, (Person) parent.getItemAtPosition(position));
                extras.putString(Const.TITLE, "Biography"); // todo
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });

        personFinder = findViewById(R.id.ed_search_person_search);
        personFinder.setHint(items.get(Const.SEARCH_PLACEHOLDER));
        personFinder.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"]");

                personsAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_search);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);

        ibProfile = findViewById(R.id.iv_account_toolbar_search);
        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AccountActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(Const.APPLICATION_ITEMS, items);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        personsAdapter = null;
        people = new ArrayList<>();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
