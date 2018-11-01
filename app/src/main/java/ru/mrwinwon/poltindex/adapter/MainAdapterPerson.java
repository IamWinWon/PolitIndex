package ru.mrwinwon.poltindex.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.util.RoundedCornersTransformation;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.ui.activity.PersonActivity;
import ru.mrwinwon.poltindex.util.Const;
import ru.mrwinwon.poltindex.util.MainUtil;

public class MainAdapterPerson extends RecyclerView.Adapter<MainAdapterPerson.ViewHolder> {

    private ArrayList<Person> people;
    private HashMap<String, String> items;
    private Context context;
    private Activity activity;
    private boolean isLogged;
    private Event event;
    private int lastPosition = -1;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public MainAdapterPerson(HashMap<String, String> items, ArrayList<Person> people, Context context, Activity activity, boolean isLogged, Event event) {
        this.people = people;
        this.context = context;
        this.activity = activity;
        this.isLogged = isLogged;
        this.items = items;
        this.event = event;
    }

    @Override
    public MainAdapterPerson.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.persons_row, parent, false);

        MainAdapterPerson.ViewHolder vh = new MainAdapterPerson.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MainAdapterPerson.ViewHolder holder, int position) {
        final Person person = people.get(position);

        if (isLogged) {
            Picasso.with(context).load(person.getAvatar()).resize(MainUtil.kWidth(MainUtil.getPersonRecylerHeightLogged(activity, context)), MainUtil.getPersonRecylerHeightLogged(activity, context)).centerCrop().transform( new RoundedCornersTransformation(24, 0, RoundedCornersTransformation.CornerType.ALL )).into(holder.imagePerson);
        } else {
            Picasso.with(context).load(person.getAvatar()).resize(MainUtil.kWidth(MainUtil.getPersonRecylerHeight(activity, context)), MainUtil.getPersonRecylerHeight(activity, context)).centerCrop().transform( new RoundedCornersTransformation(24, 0, RoundedCornersTransformation.CornerType.ALL )).into(holder.imagePerson);
        }

    }


    @Override
    public int getItemCount() {
        return people.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageButton imagePerson;

        public ViewHolder(View itemView) {
            super(itemView);
            imagePerson = itemView.findViewById(R.id.ib_person);
            imagePerson.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, PersonActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(Const.APPLICATION_PEOPLE, people);
            extras.putSerializable(Const.APPLICATION_ITEMS, items);
            extras.putInt(Const.SELECTED_ITEM_POSITION, getLayoutPosition());
            extras.putParcelable(Const.SELECTED_EVENT, event);
            intent.putExtras(extras);
            context.startActivity(intent);
        }
    }

    public interface OnFlippedItem {
        void onFlipped(View view);
    }


}
