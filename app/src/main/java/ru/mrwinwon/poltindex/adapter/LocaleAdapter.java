package ru.mrwinwon.poltindex.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.model.Event;

public class LocaleAdapter extends RecyclerView.Adapter<LocaleAdapter.ViewHolder> {

    private ArrayList<Event> events;
    private Context context;
    private Activity activity;
    private static ClickListener clickListener;

    public interface OnAsyncPeopleLoad {
        void getPeople();
    }

    public LocaleAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public LocaleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.locale_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(LocaleAdapter.ViewHolder holder, int position) {
//        final Event titleObg = events.get(position);
//        holder.title.setText(titleObg.getTitle());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleTop;
        public TextView titleBottom;
        public LinearLayout llLocale;


        public ViewHolder(View itemView) {
            super(itemView);
            llLocale = itemView.findViewById(R.id.ll_locale);
            titleTop = itemView.findViewById(R.id.tv_top_locale_title);
            titleBottom = itemView.findViewById(R.id.tv_bottom_locale_title);

            llLocale.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        LocaleAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
//        void onItemLongClick(int position, View v);
    }

}