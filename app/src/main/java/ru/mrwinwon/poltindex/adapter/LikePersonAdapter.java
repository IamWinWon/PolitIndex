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
import java.util.List;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.util.RoundedCornersTransformation;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.ui.activity.BiographyActivity;
import ru.mrwinwon.poltindex.util.Const;
import ru.mrwinwon.poltindex.util.MainUtil;

public class LikePersonAdapter extends RecyclerView.Adapter<LikePersonAdapter.ViewHolder> {

    private ArrayList<Person> people;
    private String title;
    private Context context;
    private Activity activity;
    private boolean isLogged;
    private int lastPosition = -1;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public LikePersonAdapter(ArrayList<Person> people, Context context, Activity activity, boolean isLogged, String title) {
        this.people = people;
        this.context = context;
        this.activity = activity;
        this.isLogged = isLogged;
        this.title = title;
    }

    @Override
    public LikePersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_persons_row, parent, false);

        LikePersonAdapter.ViewHolder vh = new LikePersonAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(LikePersonAdapter.ViewHolder holder, int position) {
        final Person person = people.get(position);
        Picasso.with(context).load(person.getAvatar()).resize(MainUtil.convertDpToPixel(220, context) - 10,MainUtil.convertDpToPixel(220, context) - 10).transform(new RoundedCornersTransformation(MainUtil.convertDpToPixel(110, context), 0, RoundedCornersTransformation.CornerType.ALL)).centerCrop().into(holder.imagePerson);
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
            Intent intent = new Intent(context, BiographyActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable(Const.APPLICATION_PERSON, people.get(getAdapterPosition()));
            extras.putString(Const.TITLE, title);
            intent.putExtras(extras);
            context.startActivity(intent);
        }
    }

    public interface OnFlippedItem {
        void onFlipped(View view);
    }


}
