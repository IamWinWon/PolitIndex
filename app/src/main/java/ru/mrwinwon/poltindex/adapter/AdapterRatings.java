package ru.mrwinwon.poltindex.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.util.ItemsLoader;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Graph;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.ui.activity.CommentaryActivity;
import ru.mrwinwon.poltindex.util.Const;

public class AdapterRatings extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Graph> graphs;
    private Context context;
    private Activity activity;

    private Graph graph;
    private int positionHolder;
    private int number_id;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    private final int VIEWW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ItemsLoader itemsLoader;
    boolean isLoading;
    int visibleThreshold = 6;
    int lastVisibleItem, totalItemCount;
    Person person;
    Event event;
    HashMap<String, String> items;

    public interface OnRecyclerItemClickListener {
        //        void onRecyclerItemClick(String visit_id, String product_id, int position, int number_id);
        void onRecyclerItemClick();
    }

    public AdapterRatings(ArrayList<Graph> graphs, Context context, OnRecyclerItemClickListener onRecyclerItemClickListener, RecyclerView recyclerView, Person person, Event event, HashMap<String, String> items) {
        this.graphs = graphs;
        this.context = context;
//        this.activity = activity;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
        this.person = person;
        this.event = event;
        this.items = items;

        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (itemsLoader != null) {
                        itemsLoader.onLoadMore(); // todo другой лоадер
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return graphs.get(position) == null ? VIEW_TYPE_LOADING : VIEWW_TYPE_ITEM;
    }

    public void setItemsLoader(ItemsLoader itemsLoader) {
        this.itemsLoader = itemsLoader;
    }

    @Override
    public AdapterRatings.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rating_item, parent, false);

            AdapterRatings.ViewHolder vh = new AdapterRatings.ViewHolder(v, positionHolder, onRecyclerItemClickListener);
            return vh;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rating_item, parent, false); // todo loading item

            AdapterRatings.ViewHolder vh = new AdapterRatings.ViewHolder(v, positionHolder, onRecyclerItemClickListener);
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        graph = graphs.get(position);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.rating.setText(graph.getRating() + "% PI");
            viewHolder.comments.setText(graph.getComments() + "");
            viewHolder.dislike.setText(graph.getDisLikes() + "");
            viewHolder.like.setText(graph.getLikes() + "");
            viewHolder.date.setText(graph.getDate());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                viewHolder.ratingBar.setProgress(graph.getRating(), true);
            } else {
                viewHolder.ratingBar.setProgress(graph.getRating());
            }

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            // todo progressbar
        }

    }


    @Override
    public int getItemCount() {
        return graphs.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView like;
        public TextView dislike;
        public TextView comments;
        public TextView date;
        public TextView rating;
        public ProgressBar ratingBar;
        public View view;

        public int position;

        private OnRecyclerItemClickListener onRecyclerItemClickListener;

        public ViewHolder(View itemView, int position, OnRecyclerItemClickListener onRecyclerItemClickListener) {
            super(itemView);
            this.onRecyclerItemClickListener = onRecyclerItemClickListener;

            view = itemView.findViewById(R.id.rl_rating_item);
            like = itemView.findViewById(R.id.tv_like_rating);
            dislike = itemView.findViewById(R.id.tv_dislike_rating);
            comments = itemView.findViewById(R.id.tv_comments_rating);
            date = itemView.findViewById(R.id.tv_date_rating);
            rating = itemView.findViewById(R.id.tv_pi_rating);

            ratingBar = itemView.findViewById(R.id.pb_rating_rl);

            this.position = position;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.rl_rating_item) {
                Intent intent = new Intent(context, CommentaryActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelable(Const.APPLICATION_PERSON, person);
                extras.putSerializable(Const.APPLICATION_ITEMS, items);
                extras.putParcelable(Const.SELECTED_EVENT, event);
                extras.putString(Const.APPLICATION_DATE, graphs.get(getLayoutPosition()).getDate());
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
            //todo progressbar
        }
    }
}
