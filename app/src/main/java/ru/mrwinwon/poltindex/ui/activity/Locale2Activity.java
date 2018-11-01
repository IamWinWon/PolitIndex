package ru.mrwinwon.poltindex.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.connection.AsyncLocale;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Country;
import ru.mrwinwon.poltindex.model.Language;
import ru.mrwinwon.poltindex.model.Translete;
import ru.mrwinwon.poltindex.ui.fragment.CountryFragment;
import ru.mrwinwon.poltindex.ui.fragment.LanguageFragment;
import ru.mrwinwon.poltindex.util.Const;

public class Locale2Activity extends AppCompatActivity implements AsyncLocale.OnLanguageCountryLoader {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private HashMap<String, String> items;
    private ArrayList<Language> languages;
    private ArrayList<Country> countries;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locale2);

        // Данные приложения
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            items = (HashMap<String, String>) bundle.getSerializable(Const.APPLICATION_ITEMS);
        }

        Toolbar toolbar = findViewById(R.id.toolbar_locale);
        toolbar.setTitle(items.get(Const.TITLE_LOCATION));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);

        new AsyncLocale(this, sessionManager, this).execute();

    }

    @Override
    public void asyncLocale(Translete mainScreanData) {
        languages = mainScreanData.getLanguages();
        countries = mainScreanData.getCountries();

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFrag(CountryFragment.newInstance(countries), items.get(Const.BTN_SELECT_COUNTRY));
        mSectionsPagerAdapter.addFrag(LanguageFragment.newInstance(languages), items.get(Const.BTN_SELECT_LANGUGE));
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(items.get(Const.BTN_SELECT_COUNTRY));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(items.get(Const.BTN_SELECT_LANGUGE));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            String title = String.valueOf(position);
//            if (position == 0) title = items.get(Const.BTN_SELECT_COUNTRY);
//            if (position == 1) title = items.get(Const.BTN_SELECT_LANGUGE);
            return mFragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            if (position == 0) return CountryFragment.newInstance(countries);
//            if (position == 1) return LanguageFragment.newInstance(languages);
            return mFragmentList.get(position);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
