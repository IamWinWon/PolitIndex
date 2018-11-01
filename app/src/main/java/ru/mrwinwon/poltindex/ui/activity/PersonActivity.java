package ru.mrwinwon.poltindex.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.AdapterComments;
import ru.mrwinwon.poltindex.adapter.LikePersonAdapter;
import ru.mrwinwon.poltindex.adapter.util.CenterZoomLayoutManager;
import ru.mrwinwon.poltindex.connection.AsyncFigure;
import ru.mrwinwon.poltindex.connection.AsyncLove;
import ru.mrwinwon.poltindex.connection.AsyncSendComment;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Commentary;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Figure;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.util.Const;

public class PersonActivity extends AppCompatActivity
        implements
        View.OnClickListener,
        AsyncFigure.OnFigureLoader,
        AsyncLove.OnFigureLoveLoader,
        AsyncSendComment.OnSendComment,
        AdapterComments.OnRecyclerItemClickListener {

    private TextView likes, dislikes, pi, title_pi, vs, title;
    private ImageView leftArrow, rightArrow;
    private ImageView rating, biography, share;
    private ImageButton like, dislike, sendComment;
    private HashMap<String, String> items;
    private ArrayList<Person> people;
    private Event event;
    private SessionManager sessionManager;
    private RecyclerView recyclerViewPeople, recyclerViewComments;
    private LikePersonAdapter adapterPerson;
    private AdapterComments adapterComments;
    private LinearLayoutManager layoutManagerPeople, layoutManagerComments;
    private ArrayList<Commentary> commentaries;
    private EditText comment;

    private int personItem;
    private Context context;
    private boolean isVoited = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        init();

    }

    @SuppressLint("SetTextI18n")
    private void init() {
        context = this;
        final AsyncFigure.OnFigureLoader onFigureLoader = this;
        personItem = 0;

        sessionManager = new SessionManager(this);
        // Данные приложения
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            items = (HashMap<String, String>) bundle.getSerializable(Const.APPLICATION_ITEMS);
            people = (ArrayList<Person>) bundle.getSerializable(Const.APPLICATION_PEOPLE);
            personItem = bundle.getInt(Const.SELECTED_ITEM_POSITION);
            event = bundle.getParcelable(Const.SELECTED_EVENT);
        }

        new AsyncFigure(this, sessionManager, people.get(personItem), this, event, (ProgressBar) findViewById(R.id.pb_comments_loading)).execute();

        likes = findViewById(R.id.tv_likes);
        dislikes = findViewById(R.id.tv_dislikes);
        pi = findViewById(R.id.tv_rating_person_activity);
        title_pi = findViewById(R.id.tv_title_rating_person_activity);
        vs = findViewById(R.id.tv_vs_person);

        rating = findViewById(R.id.iv_rating_person);
        biography = findViewById(R.id.iv_biography_person);
        share = findViewById(R.id.iv_share);

        like = findViewById(R.id.ib_like_person);
        dislike = findViewById(R.id.ib_dislike_person);
        sendComment = findViewById(R.id.ib_send_comment);

        comment = findViewById(R.id.ed_commentary_person);
        comment.setHint(items.get(Const.APPLICATION_PEOPLE));
        if (sessionManager.getKeyLogged())
            findViewById(R.id.ll_ed_commentary).setVisibility(View.VISIBLE);

        rating.setOnClickListener(this);
        share.setOnClickListener(this);
        biography.setOnClickListener(this);
        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        sendComment.setOnClickListener(this);

//        leftArrow = findViewById(R.id.iv_left_person_activity);
        rightArrow = findViewById(R.id.iv_right_person_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_person_activity);
        title = toolbar.findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        title.setText(people.get(personItem).getFirstName() + " " + people.get(personItem).getSecondName());

        title.setText(people.get(personItem).getFirstName() + " " + people.get(personItem).getSecondName());
        String rating = people.get(personItem).getRating() + "%";
        pi.setText(rating);

        vs.setText(items.get(Const.LABEL_DECISION));
        likes.setText(people.get(personItem).getLikes() + "");
        dislikes.setText(people.get(personItem).getDislikes() + "");

        if (people.get(personItem).getIsLiked() == 0) {
            dislike.setBackgroundResource(R.drawable.shape_dislike_clicked_cycle);
        } else if (people.get(personItem).getIsLiked() == 1) {
            like.setBackgroundResource(R.drawable.shape_like_clicked_cycle);
        }

        if (people.get(personItem).getRating() > 49) {
            pi.setBackgroundResource(R.drawable.gradient_pi_person);
        } else {
            pi.setBackgroundResource(R.drawable.gradient_pi_person_negative);
        }
        pi.setText(people.get(personItem).getRating() + "%");


        likes.setText(people.get(personItem).getLikes() + "");

        // recyclerview ================================
//        layoutManagerPeople = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerPeople = new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapterPerson = new LikePersonAdapter(people, this, this, sessionManager.getKeyLogged(), "Biography"); // todo
        recyclerViewPeople = findViewById(R.id.rv_person_activity);
        recyclerViewPeople.setHasFixedSize(true);
        recyclerViewPeople.setLayoutManager(layoutManagerPeople);
        recyclerViewPeople.setAdapter(adapterPerson);
        final SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerViewPeople);

        recyclerViewPeople.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    new AsyncFigure(context, sessionManager, people.get(personItem), onFigureLoader, event, (ProgressBar) findViewById(R.id.pb_comments_loading)).execute();
                    View centerView = helper.findSnapView(layoutManagerPeople);
                    int personPosition = layoutManagerPeople.getPosition(centerView);
                    try {
                        Person temp = people.get(personPosition);
                        title.setText(temp.getFirstName() + " " + temp.getSecondName());
                        String rating = temp.getRating() + "%";
                        pi.setText(rating);

                        personItem = personPosition; // for biography

                        likes.setText(people.get(personItem).getLikes() + "");
                        dislikes.setText(people.get(personItem).getDislikes() + "");

                        if (personPosition == 0) {
//                            leftArrow.setVisibility(View.GONE);
                            rightArrow.setVisibility(View.VISIBLE);
                        } else if (personPosition == people.size() - 1) {
//                            leftArrow.setVisibility(View.VISIBLE);
                            rightArrow.setVisibility(View.GONE);
                        } else {
//                            leftArrow.setVisibility(View.VISIBLE);
                            rightArrow.setVisibility(View.VISIBLE);
                        }

                        if (temp.getIsLiked() == 0) {
                            dislike.setBackgroundResource(R.drawable.shape_dislike_clicked_cycle);
                            like.setBackgroundResource(R.drawable.shape_like_cycle);
                            people.get(personItem).setIsLiked(-1);
                            pi.setBackgroundResource(R.drawable.gradient_pi_person_negative);
                            pi.setText("0" + "%");
                        } else if (temp.getIsLiked() == -1) {
                            dislike.setBackgroundResource(R.drawable.shape_like_cycle);
                            like.setBackgroundResource(R.drawable.shape_like_cycle);
                            people.get(personItem).setIsLiked(-1);
                            pi.setText("0" + "%");
                        } else if (temp.getIsLiked() == 1) {
                            like.setBackgroundResource(R.drawable.shape_like_clicked_cycle);
                            dislike.setBackgroundResource(R.drawable.shape_like_cycle);
                            people.get(personItem).setIsLiked(1);
                            pi.setBackgroundResource(R.drawable.gradient_pi_person);
                            pi.setText("100%");
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    Log.i("Snapped Item Position:", "" + personPosition);
                }
            }
        });

        recyclerViewPeople.scrollToPosition(personItem);

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        findViewById(R.id.ll_left_buttons).bringToFront();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_share) {
            // todo
        } else if (view.getId() == R.id.iv_biography_person) {
            Intent intent = new Intent(this, BiographyActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable(Const.APPLICATION_PERSON, people.get(personItem));
            extras.putString(Const.TITLE, "Biography"); // todo
            intent.putExtras(extras);
            startActivity(intent);
        } else if (view.getId() == R.id.iv_rating_person) {
            Intent intent = new Intent(this, RatingsActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable(Const.APPLICATION_PERSON, people.get(personItem));
            extras.putSerializable(Const.APPLICATION_ITEMS, items);
            extras.putParcelable(Const.SELECTED_EVENT, event);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (view.getId() == R.id.ib_like_person) {
            isVoited = true;
            likes.setText(people.get(personItem).getLikes() + 1 + "");
            like.setBackgroundResource(R.drawable.shape_like_clicked_cycle);
            people.get(personItem).setIsLiked(1);
            pi.setBackgroundResource(R.drawable.gradient_pi_person);
            pi.setText("100%");
            if (isVoited)
                new AsyncLove(sessionManager, people.get(personItem), this, event, event.getIdEvent(), people.get(personItem).getIdPerson(), 1).execute();
        } else if (view.getId() == R.id.ib_dislike_person) {
            isVoited = true;
            dislikes.setText(people.get(personItem).getDislikes() + 1 + "");
            dislike.setBackgroundResource(R.drawable.shape_dislike_clicked_cycle);
            people.get(personItem).setIsLiked(0);
            pi.setBackgroundResource(R.drawable.gradient_pi_person_negative);
            pi.setText("0" + "%");
            if (isVoited)
                new AsyncLove(sessionManager, people.get(personItem), this, event, event.getIdEvent(), people.get(personItem).getIdPerson(), 0).execute();
        } else if (view.getId() == R.id.ib_send_comment) {
            new AsyncSendComment(this, this, event.getIdEvent(), people.get(personItem).getIdPerson(), comment.getText().toString()).execute();
        }
    }

    @Override
    public void onCommentResult(HashMap items) {

    }

    @Override
    public void onRecyclerItemClick(String visit_id, String product_id, int position, int number_id) {

    }

    @Override
    public void asyncEventPeople(ArrayList<Commentary> commentaries) {
        this.commentaries = commentaries;

        layoutManagerComments = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewComments = findViewById(R.id.rv_comments_person);
        recyclerViewComments.setHasFixedSize(true);
        recyclerViewComments.setLayoutManager(layoutManagerComments);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewComments.getContext(),
                layoutManagerComments.getOrientation());
        recyclerViewComments.addItemDecoration(dividerItemDecoration);
        adapterComments = new AdapterComments(commentaries, this, this, this, recyclerViewComments);
        recyclerViewComments.setAdapter(adapterComments);
    }

    @Override
    public void asyncLovePeople(Figure figure) {
        if (figure != null) {
            likes.setText(figure.getLike() + "");
            dislikes.setText(figure.getDislike() + "");
            pi.setText(figure.getRating() + "");
            if (figure.getIsLiked() == 1) {
                like.setBackgroundResource(R.drawable.shape_like_clicked_cycle);
                people.get(personItem).setIsLiked(1);
                pi.setBackgroundResource(R.drawable.gradient_pi_person);
            } else if (figure.getIsLiked() == 0) {
                dislike.setBackgroundResource(R.drawable.shape_dislike_clicked_cycle);
                people.get(personItem).setIsLiked(0);
                pi.setBackgroundResource(R.drawable.gradient_pi_person_negative);
            } else if (figure.getIsLiked() == -1) {
                dislike.setBackgroundResource(R.drawable.shape_like_cycle);
                like.setBackgroundResource(R.drawable.shape_like_cycle);
                people.get(personItem).setIsLiked(-1);
            }
        }
    }
}
