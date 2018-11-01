package ru.mrwinwon.poltindex.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.util.ItemsLoader;
import ru.mrwinwon.poltindex.adapter.util.RoundedCornersTransformation;
import ru.mrwinwon.poltindex.model.Commentary;

public class AdapterComments extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Commentary> commentaries;
    private Context context;
    private Activity activity;

    private Commentary commentary;
    private int positionHolder;
    private int number_id;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    private final int VIEWW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ItemsLoader itemsLoader;
    boolean isLoading;
    int visibleThreshold = 6;
    int lastVisibleItem, totalItemCount;

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(String visit_id, String product_id, int position, int number_id);
    }

    public AdapterComments(ArrayList<Commentary> commentaries, Context context, Activity activity, OnRecyclerItemClickListener onRecyclerItemClickListener, RecyclerView recyclerView) {
        this.commentaries = commentaries;
        this.context = context;
        this.activity = activity;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;

        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (itemsLoader != null) {
                        itemsLoader.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return commentaries.get(position) == null ? VIEW_TYPE_LOADING : VIEWW_TYPE_ITEM;
    }

    public void setItemsLoader(ItemsLoader itemsLoader) {
        this.itemsLoader = itemsLoader;
    }

    @Override
    public AdapterComments.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_item_person, parent, false);

            AdapterComments.ViewHolder vh = new AdapterComments.ViewHolder(v, positionHolder, onRecyclerItemClickListener);
            return vh;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_item_person, parent, false); // todo loading item

            AdapterComments.ViewHolder vh = new AdapterComments.ViewHolder(v, positionHolder, onRecyclerItemClickListener);
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        commentary = commentaries.get(position);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.name.setText(commentary.getName());
            viewHolder.date.setText(commentary.getFullDate());
            viewHolder.text.setText(commentary.getText());

            Picasso.with(context).load(commentary.getAvatar()).resize(50,50).transform(new RoundedCornersTransformation(25, 0, RoundedCornersTransformation.CornerType.ALL)).centerCrop().into(((ViewHolder) holder).avatar);


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            // todo progressbar
        }

    }


    @Override
    public int getItemCount() {
        return commentaries.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView text;
        public TextView date;
        public ImageView avatar;

        public int position;

        private OnRecyclerItemClickListener onRecyclerItemClickListener;

        public ViewHolder(View itemView, int position, OnRecyclerItemClickListener onRecyclerItemClickListener) {
            super(itemView);
            this.onRecyclerItemClickListener = onRecyclerItemClickListener;

            name = itemView.findViewById(R.id.tv_comments_name);
            text = itemView.findViewById(R.id.tv_comments_text);
            date = itemView.findViewById(R.id.tv_comments_date);
            avatar = itemView.findViewById(R.id.iv_comments_person);

            this.position = position;
        }

        @Override
        public void onClick(View view) {

        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
            //todo progressbar
        }


    }
}

