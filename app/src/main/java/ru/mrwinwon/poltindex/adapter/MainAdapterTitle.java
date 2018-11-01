package ru.mrwinwon.poltindex.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.model.Event;

public class MainAdapterTitle extends RecyclerView.Adapter<MainAdapterTitle.ViewHolder> {

    private ArrayList<Event> events;
    private Context context;
    private Activity activity;
    private static ClickListener clickListener;

    public interface OnAsyncPeopleLoad {
        void getPeople();
    }

    public MainAdapterTitle(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public MainAdapterTitle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MainAdapterTitle.ViewHolder holder, int position) {
        final Event eventObg = events.get(position);
        holder.title.setText(eventObg.getTitle());
        Picasso.with(context).load(eventObg.getIcon()).into(holder.imageTittle);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView imageTittle;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_persons_title);
            imageTittle = itemView.findViewById(R.id.iv_persons_title);

            title.setOnClickListener(this);
            imageTittle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MainAdapterTitle.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
//        void onItemLongClick(int position, View v);
    }

}


