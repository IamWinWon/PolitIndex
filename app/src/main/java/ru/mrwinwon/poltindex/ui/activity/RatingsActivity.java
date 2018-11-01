package ru.mrwinwon.poltindex.ui.activity;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.AdapterComments;
import ru.mrwinwon.poltindex.adapter.AdapterRatings;
import ru.mrwinwon.poltindex.adapter.util.ItemsLoader;
import ru.mrwinwon.poltindex.connection.AsyncFigure;
import ru.mrwinwon.poltindex.connection.AsyncGraphRequest;
import ru.mrwinwon.poltindex.model.Commentary;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Graph;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.util.Const;

public class RatingsActivity extends AppCompatActivity
        implements
        ItemsLoader,
        AsyncGraphRequest.OnGraphLoader,
        AdapterRatings.OnRecyclerItemClickListener {

    private HashMap<String, String> items;
    private ArrayList<Person> people;
    private int personItem;
    private Event event;
    private Person person;
    private ArrayList<Graph> graphs;
    private LinearLayoutManager layoutManagerGraphs;
    private RecyclerView recyclerViewGraphs;
    private AdapterRatings adapterGraphs;
    private ItemsLoader itemsLoader;

    private boolean shouldLoadMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        // Данные приложения
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            items = (HashMap<String, String>) bundle.getSerializable(Const.APPLICATION_ITEMS);
//            people = (ArrayList<Person>) bundle.getSerializable(Const.APPLICATION_PEOPLE);
            person = bundle.getParcelable(Const.APPLICATION_PERSON);
//            personItem = bundle.getInt(Const.SELECTED_ITEM_POSITION);
            event = bundle.getParcelable(Const.SELECTED_EVENT);
        }

        new AsyncGraphRequest(this, person, event, this).execute();

        Toolbar toolbar = findViewById(R.id.toolbar_rating_activity);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        title.setText(person.getFirstName() + " " + person.getSecondName());

    }

    @Override
    public void asyncEventPeople(final ArrayList<Graph> graphs) {
        this.graphs = graphs;

        layoutManagerGraphs = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewGraphs = findViewById(R.id.rv_rating);
        recyclerViewGraphs.setHasFixedSize(true);
        recyclerViewGraphs.setLayoutManager(layoutManagerGraphs);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewGraphs.getContext(),
                layoutManagerGraphs.getOrientation());
        recyclerViewGraphs.addItemDecoration(dividerItemDecoration);
        adapterGraphs = new AdapterRatings(graphs,this, this, recyclerViewGraphs, person, event, items);
        adapterGraphs.setItemsLoader(this);
        recyclerViewGraphs.setAdapter(adapterGraphs);

        final SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerViewGraphs);


        recyclerViewGraphs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                    View centerView = helper.findSnapView(layoutManagerGraphs);
                    int itemPosition = layoutManagerGraphs.getPosition(centerView);
                    if (itemPosition == graphs.size()-1) {
//                        itemsLoader.onLoadMore(); // todo ругой лоадер
                    }
                }
            }
        });
    }

    @Override
    public void onRecyclerItemClick() {

    }

    @Override
    public void onLoadMore() {
//        new AsyncGraphRequest(this, person, event, this).execute();

    }
}
