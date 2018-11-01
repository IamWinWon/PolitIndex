package ru.mrwinwon.poltindex.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.model.Language;
import ru.mrwinwon.poltindex.ui.fragment.LanguageFragment.OnListFragmentInteractionListener;

public class MyLanguageRecyclerViewAdapter extends RecyclerView.Adapter<MyLanguageRecyclerViewAdapter.ViewHolder> {

    private final List<Language> languageList;
    private final OnListFragmentInteractionListener mListener;
    private static MyLanguageRecyclerViewAdapter.ClickListener clickListener;


    public MyLanguageRecyclerViewAdapter(List<Language> items, OnListFragmentInteractionListener listener) {
        languageList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_language, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = languageList.get(position);
        holder.mTopLanguage.setText(languageList.get(position).getTopName());
        holder.mBottomLanguage.setText(languageList.get(position).getBottomName());

    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mTopLanguage;
        public final TextView mBottomLanguage;
//        public Language mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTopLanguage = view.findViewById(R.id.tv_top_language);
            mBottomLanguage = view.findViewById(R.id.tv_bottom_language);
            mView.setOnClickListener(this);
//            mItem = languageList.get(getAdapterPosition());
        }


        @Override
        public void onClick(View view) {
            clickListener.onLanguageClick(languageList.get(getAdapterPosition()).getTopName());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBottomLanguage.getText() + "'";
        }
    }

    public void setOnItemClickListener(MyLanguageRecyclerViewAdapter.ClickListener clickListener) {
        MyLanguageRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onLanguageClick(String language);
//        void onItemLongClick(int position, View v);
    }
}
