package ru.mrwinwon.poltindex.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.util.RoundedCornersTransformation;
import ru.mrwinwon.poltindex.model.Timeline;


public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Timeline> timelines;
    private Context context;
    private String time = "", time2 = "";


    public TimelineAdapter(ArrayList<Timeline> timelines, Context context) {
        this.timelines = timelines;
        this.context = context;
    }

    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_item, parent, false);

        TimelineAdapter.ViewHolder vh = new TimelineAdapter.ViewHolder(v);
        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Timeline timeline = timelines.get(position);
        if (holder instanceof TimelineAdapter.ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.name.setText(timeline.getName());
            viewHolder.date.setText(timeline.getFullDate().substring(11,19)); // 2018-01-20T08:30:13+00:00

            if (!timeline.getAvatar().equals("null")) {
                Picasso.with(context).load(timeline.getAvatar()).resize(50,50).transform(new RoundedCornersTransformation(25, 0, RoundedCornersTransformation.CornerType.ALL)).centerCrop().into(viewHolder.avatarWeb);
//                viewHolder.avatar.setVisibility(View.GONE);
                viewHolder.avatar.setText(timeline.getName().substring(0,1));
            } else {
                viewHolder.avatarWeb.setVisibility(View.GONE);
                viewHolder.avatar.setText(timeline.getName().substring(0,1));
            }

            if (timeline.getType() == 0) { // like/dislike
                viewHolder.rating.setVisibility(View.VISIBLE);
                viewHolder.text.setVisibility(View.GONE);
                if (timeline.getLike() == 1) {
                    viewHolder.rating.setImageResource(R.mipmap.btn_like);
                    viewHolder.rating.setBackground(context.getResources().getDrawable(R.drawable.shape_round_like_timeline));
                } else if (timeline.getLike() == 0) {
                    viewHolder.rating.setImageResource(R.mipmap.btn_dislike);
                    viewHolder.rating.setBackground(context.getResources().getDrawable(R.drawable.shape_round_dislike_timeline));
                }
            } else if (timeline.getType() == 1) { // comment
                viewHolder.text.setText(timeline.getText());
                viewHolder.text.setVisibility(View.VISIBLE);
                viewHolder.rating.setVisibility(View.GONE);
            }

            if (time.length() == 0) {
                time = timeline.getFullDate().substring(11,13);
                viewHolder.time.setText(time+":00:00");
                viewHolder.time.setVisibility(View.VISIBLE);
            } else {
                if (!time.equals(timeline.getFullDate().substring(11,13))) {
                    time = timeline.getFullDate().substring(11,13);
                    viewHolder.time.setText(time+":00:00");
                    viewHolder.time.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.time.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return timelines.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatarWeb;
        public Button avatar;
        public ImageView rating;
        public TextView text;
        public TextView name;
        public TextView date;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.btn_round_avatar_timeline);
            avatarWeb = itemView.findViewById(R.id.iv_round_avatar_timeline);
            rating = itemView.findViewById(R.id.iv_rating_timeline);
            text = itemView.findViewById(R.id.tv_text_timeline);
            name = itemView.findViewById(R.id.tv_name_timeline);
            date = itemView.findViewById(R.id.tv_date_timeline);
            time = itemView.findViewById(R.id.tv_top_time_timeline);

        }
    }
}


