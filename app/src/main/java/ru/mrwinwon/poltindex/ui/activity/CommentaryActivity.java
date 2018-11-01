package ru.mrwinwon.poltindex.ui.activity;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.AdapterRatings;
import ru.mrwinwon.poltindex.adapter.TimelineAdapter;
import ru.mrwinwon.poltindex.connection.AsyncGraphRequest;
import ru.mrwinwon.poltindex.connection.AsyncTimeLine;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.model.Timeline;
import ru.mrwinwon.poltindex.util.Const;
import ru.mrwinwon.poltindex.util.MainUtil;

public class CommentaryActivity extends AppCompatActivity implements AsyncTimeLine.OnTimelineDataLoaded {

    private HashMap<String, String> items;
    private ArrayList<Person> people;
    private int personItem;
    private Event event;
    private Person person;
    private String date;
    private LinearLayoutManager layoutManagerTimeline;
    private RecyclerView recyclerViewTimeline;
    private TimelineAdapter adapterTimeline;
    private Context context;
    private TextView titleChronics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentary);

        context = this;

        // Данные приложения
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            items = (HashMap<String, String>) bundle.getSerializable(Const.APPLICATION_ITEMS);
            person = bundle.getParcelable(Const.APPLICATION_PERSON);
            event = bundle.getParcelable(Const.SELECTED_EVENT);
            date = bundle.getString(Const.APPLICATION_DATE);
        }

        new AsyncTimeLine(this, event, person, date, this).execute();


        Toolbar toolbar = findViewById(R.id.toolbar_commentary_activity);
        TextView title = toolbar.findViewById(R.id.tv_toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        title.setText(person.getFirstName() + " " + person.getSecondName());

        titleChronics = findViewById(R.id.tv_chronics);
        titleChronics.setText("Хроника событий: " + date.substring(8, 10) + " " + MainUtil.makeTimeHeader(date, items.get(Const.MONTH))); // 2018-01-20T08:30:13+00:00

    }

    @Override
    public void asyncTimelineData(ArrayList<Timeline> timelines) {
        layoutManagerTimeline = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewTimeline = findViewById(R.id.rv_commentary_activity);
        recyclerViewTimeline.setHasFixedSize(true);
        recyclerViewTimeline.setLayoutManager(layoutManagerTimeline);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewTimeline.getContext(),
//                layoutManagerTimeline.getOrientation());
//        recyclerViewTimeline.addItemDecoration(dividerItemDecoration);
        adapterTimeline = new TimelineAdapter(timelines,context);
        recyclerViewTimeline.setAdapter(adapterTimeline);

//        final SnapHelper helper = new LinearSnapHelper();
//        helper.attachToRecyclerView(recyclerViewTimeline);

    }
}
