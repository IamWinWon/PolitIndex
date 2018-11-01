package ru.mrwinwon.poltindex.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.util.RoundedCornersTransformation;
import ru.mrwinwon.poltindex.connection.AsyncBiography;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Biography;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.util.Const;

public class BiographyActivity extends AppCompatActivity implements AsyncBiography.OnBigraphyLoader {

    private SessionManager sessionManager;
    private Biography biographyPerson;
    private Person person;
    private String titleString;

    private TextView birthDate, biographyText, name;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biography);

        sessionManager = new SessionManager(this);

        // Данные приложения
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            person = bundle.getParcelable(Const.APPLICATION_PERSON);
            titleString = bundle.getString(Const.TITLE);
        }

        Toolbar toolbar = findViewById(R.id.toolbar_biography);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        title.setText(titleString);

        birthDate = findViewById(R.id.tv_date_birth);
        biographyText = findViewById(R.id.tv_biography);
        name = findViewById(R.id.tv_name_biography);
        avatar = findViewById(R.id.iv_person_biography);

        new AsyncBiography(this, sessionManager, person, this).execute();
    }

    @Override
    public void asyncTitlesPeople(Biography biography) {
        this.biographyPerson = biography;
        birthDate.setText(biographyPerson.getDateBirth());
        biographyText.setText(biography.getBio());
        name.setText(String.format("%s %s", person.getSecondName(), person.getFirstName()));
        Picasso.with(this).load(person.getAvatar()).resize(140,140).transform(new RoundedCornersTransformation(70, 0, RoundedCornersTransformation.CornerType.ALL)).centerCrop().into(avatar);

    }
}
