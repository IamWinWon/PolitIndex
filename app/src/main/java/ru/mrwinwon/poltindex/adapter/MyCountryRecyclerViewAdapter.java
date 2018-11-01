package ru.mrwinwon.poltindex.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.model.Country;
import ru.mrwinwon.poltindex.ui.fragment.CountryFragment.OnListFragmentInteractionListener;

public class MyCountryRecyclerViewAdapter extends RecyclerView.Adapter<MyCountryRecyclerViewAdapter.ViewHolder> {

    private final List<Country> countries;
    private final OnListFragmentInteractionListener mListener;
    private static ClickListener clickListener;


    public MyCountryRecyclerViewAdapter(List<Country> items, OnListFragmentInteractionListener listener) {
        countries = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = countries.get(position);
        holder.mIdView.setText(countries.get(position).getTopName());
        holder.mContentView.setText(countries.get(position).getBottomName());
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.tv_top_country);
            mContentView = view.findViewById(R.id.tv_bottom_country);
            mView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.onCountryClick(countries.get(getAdapterPosition()).getTopName());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void setOnItemClickListener(MyCountryRecyclerViewAdapter.ClickListener clickListener) {
        MyCountryRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onCountryClick(String country);
//        void onItemLongClick(int position, View v);
    }
}
