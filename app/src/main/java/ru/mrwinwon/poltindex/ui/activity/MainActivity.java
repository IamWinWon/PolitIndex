package ru.mrwinwon.poltindex.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.MainAdapterPerson;
import ru.mrwinwon.poltindex.adapter.MainAdapterTitle;
import ru.mrwinwon.poltindex.connection.AsyncAuth;
import ru.mrwinwon.poltindex.connection.AsyncMainData;
import ru.mrwinwon.poltindex.connection.AsyncPersonsViaTitle;
import ru.mrwinwon.poltindex.connection.util.WebView;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Graph;
import ru.mrwinwon.poltindex.model.MainScreanData;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.util.Const;
import ru.mrwinwon.poltindex.util.MainUtil;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class MainActivity extends AppCompatActivity
        implements
        View.OnClickListener,
        AsyncAuth.OnLocaleRequest,
        AsyncMainData.OnMainDataLoaded,
        AsyncPersonsViaTitle.OnClickTitleLoader {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Context context;
    private SessionManager sessionManager;
    private Handler mainHandler;
    private AsyncPersonsViaTitle.OnClickTitleLoader onClickTitleLoader;

    private String mUrl;
    private WebView webView;

    private TextView login, loginTitle, lawTitle, lawLink;
    private TextView firstName, middleName, secondName, todayRating;
    private RelativeLayout ll_tdy, ll_ytrdy, ll_ytrdy1, ll_ytrdy2, ll_ytrdy3;
    private LinearLayout ll_rating_unloged;
    private TextView tvRatingToday, tvRatingYesterday, tvRatingYesterday1, tvRatingYesterday2, tvRatingYesterday3;
    private TextView tvDateToday, tvDateYesterday, tvDateYesterday1, tvDateYesterday2, tvDateYesterday3;
    private EditText searchebleEdtiText;
    public ProgressBar today, yesterday, yesterday1, yesterday2, yesterday3;
    private ImageButton btnVkLogin, btnFbLogin, btnOkLogin, btnInstaLogin;
    private ImageView ivLangeagueBsh, ivHelpBsh, ivLangeagueToolbar, ivHelpToolbar;
    private LinearLayout llBottomSheet;
    private Toolbar toolbar;

    private RecyclerView recyclerViewPeople, recyclerViewEvents;
    private RecyclerView.LayoutManager layoutManagerPeople, layoutManagerEvent;
    private MainAdapterPerson adapterPerson;
    private MainAdapterTitle adapterEvents;

    private int eventPosition;

    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    private ArrayList<Person> peopleSet;
    private ArrayList<Event> eventSet;

    public static HashMap<String, String> items;
    // interface for retrieving main data
    public MainScreanData mainScreanData;
    public MainScreanData mainScreanDataByTitle;
    private SnapHelper helper2;
    private SnapHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setValues();
        initHanlder();
        initToolbar();

    }

    private void initToolbar() {
        if (sessionManager.getKeyLogged()) {
            setSupportActionBar(toolbar);
            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
            ll_rating_unloged.setVisibility(View.GONE);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            setSupportActionBar(toolbar);
            recyclerViewEvents.setVisibility(View.GONE);
            toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        // Сессия
        sessionManager = new SessionManager(this);
        context = this;

        // Данные приложения
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            items = (HashMap<String, String>) bundle.getSerializable(Const.APPLICATION_ITEMS);
        }

        // Допы язык и помощь
        ivLangeagueToolbar = findViewById(R.id.iv_language_toolbar);
        ivLangeagueBsh = findViewById(R.id.iv_language_bsh);
        ivHelpBsh = findViewById(R.id.iv_help_bsh);
        ivHelpToolbar = findViewById(R.id.iv_account_toolbar);

        // Логин
        btnVkLogin = findViewById(R.id.btn_vk_login);
        btnFbLogin = findViewById(R.id.btn_fb_login);
        btnOkLogin = findViewById(R.id.btn_ok_login);
        btnInstaLogin = findViewById(R.id.btn_inst_login);

        // Текстовые поля
        login = findViewById(R.id.tv_login_bsh);
        loginTitle = findViewById(R.id.tv_login_title);
        lawTitle = findViewById(R.id.tv_law_title);
        lawLink = findViewById(R.id.tv_law_link);

        // Поиск
        searchebleEdtiText = findViewById(R.id.ed_search_person_main);

        // OnClickListener
        ivLangeagueToolbar.setOnClickListener(this);
        ivLangeagueBsh.setOnClickListener(this);
        ivHelpToolbar.setOnClickListener(this);
        ivHelpBsh.setOnClickListener(this);
        btnVkLogin.setOnClickListener(this);
        btnFbLogin.setOnClickListener(this);
        btnOkLogin.setOnClickListener(this);
        btnInstaLogin.setOnClickListener(this);
        searchebleEdtiText.setOnClickListener(this);

        // toolbar
        toolbar = findViewById(R.id.toolbar);

        // Имя
        firstName = findViewById(R.id.tv_first_name);
        middleName = findViewById(R.id.tv_middle_name);
        secondName = findViewById(R.id.tv_second_name);
        todayRating = findViewById(R.id.tv_today_rating);

        // Райтинг
        ll_rating_unloged = findViewById(R.id.ll_persons_rating_unlogged);
        ll_tdy = findViewById(R.id.ll_today);
        ll_ytrdy = findViewById(R.id.ll_yesterday);
        ll_ytrdy1 = findViewById(R.id.ll_yesterday1);
        ll_ytrdy2 = findViewById(R.id.ll_yesterday2);
        ll_ytrdy3 = findViewById(R.id.ll_yesterday3);

        today = ll_tdy.findViewById(R.id.progress_bar_rating);
        yesterday = ll_ytrdy.findViewById(R.id.progress_bar_rating);
        yesterday1 = ll_ytrdy1.findViewById(R.id.progress_bar_rating);
        yesterday2 = ll_ytrdy2.findViewById(R.id.progress_bar_rating);
        yesterday3 = ll_ytrdy3.findViewById(R.id.progress_bar_rating);

        tvDateToday = ll_tdy.findViewById(R.id.tv_date_bottom);
        tvDateYesterday = ll_ytrdy.findViewById(R.id.tv_date_bottom);
        tvDateYesterday1 = ll_ytrdy1.findViewById(R.id.tv_date_bottom);
        tvDateYesterday2 = ll_ytrdy2.findViewById(R.id.tv_date_bottom);
        tvDateYesterday3 = ll_ytrdy3.findViewById(R.id.tv_date_bottom);

        tvRatingToday = ll_tdy.findViewById(R.id.tv_rating_top);
        tvRatingYesterday = ll_ytrdy.findViewById(R.id.tv_rating_top);
        tvRatingYesterday1 = ll_ytrdy1.findViewById(R.id.tv_rating_top);
        tvRatingYesterday2 = ll_ytrdy2.findViewById(R.id.tv_rating_top);
        tvRatingYesterday3 = ll_ytrdy3.findViewById(R.id.tv_rating_top);

        searchebleEdtiText.setOnTouchListener(new View.OnTouchListener() {
            private int CLICK_ACTION_THRESHOLD = 200;
            private float startX;
            private float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();
                        if (isAClick(startX, endX, startY, endY)) {
                            //            searchebleEdtiText.animate().x(0f).y(0f);
                            Intent intent = new Intent(context, SearchPersonActivity.class);
                            Bundle extras = new Bundle();
                            extras.putSerializable(Const.APPLICATION_ITEMS, items);
                            extras.putSerializable(Const.APPLICATION_PEOPLE, peopleSet);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                        break;
                }
                v.getParent().requestDisallowInterceptTouchEvent(true); //specific to my project
                return false; //specific to my project
            }

            private boolean isAClick(float startX, float endX, float startY, float endY) {
                float differenceX = Math.abs(startX - endX);
                float differenceY = Math.abs(startY - endY);
                return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
            }
        });

        // ресайклы
        recyclerViewPeople = findViewById(R.id.rv_persons);
        recyclerViewEvents = findViewById(R.id.rv_titles);
        recyclerViewPeople.setHasFixedSize(true);
        recyclerViewEvents.setHasFixedSize(true);

        // Snap helper ( пошаговая смена )
        helper = new LinearSnapHelper();
        helper2 = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerViewEvents);
        helper2.attachToRecyclerView(recyclerViewPeople);

        // up
        // Опасный ресайкл Личности
        layoutManagerPeople = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPeople.setLayoutManager(layoutManagerPeople);
        peopleSet = new ArrayList<>();

        // Опасный ресайкл тайтлы;
        layoutManagerEvent = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(layoutManagerEvent);
        eventSet = new ArrayList<>();

        onClickTitleLoader = this;

        // Браузер
        webView = findViewById(R.id.webView);
        webView.getSettings().setAppCacheEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new myWebClient());

        // Боттом щит
        // получение вью нижнего экрана
        llBottomSheet = findViewById(R.id.include);
        // настройка поведения нижнего экрана
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        // настройка колбэков при изменениях
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() { // add onTouch?
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    findViewById(R.id.bg).setVisibility(View.GONE);
                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                findViewById(R.id.bg).setVisibility(View.VISIBLE);
                findViewById(R.id.bg).bringToFront();
                toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                llBottomSheet.bringToFront();
                findViewById(R.id.bg).setAlpha(slideOffset);
            }
        });
        // настройка возможности скрыть элемент при свайпе вниз
        bottomSheetBehavior.setHideable(false);

        // Строка перехвата редиректа
        mUrl = "";

        // Подгрузка данных
        new AsyncMainData(this, this).execute();

    }

    private void setValues() {
        login.setText(items.get(Const.BTN_LOGIN));
        loginTitle.setText(items.get(Const.LOGIN_FORM_TEXT));
        lawTitle.setText(items.get(Const.LOGIN_FORM_LAW_TEXT));
        lawLink.setText(items.get(Const.LOGIN_FORM_LAW_LINK));
        searchebleEdtiText.setHint(items.get(Const.SEARCH_PLACEHOLDER));
    }

    @SuppressLint("HandlerLeak")
    private void initHanlder() {
        mainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                webView.setVisibility(View.GONE);
                if (msg.what == Const.HANLDER_START_AUTH) {
                    final String finalUrl = mUrl;
                    mUrl = "";
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL aURL = null;
                            try {
                                aURL = new URL(finalUrl);
                                HttpURLConnection conn = (HttpURLConnection) aURL.openConnection();
                                conn.setRequestMethod("POST");

                                String result = "";

                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null)
                                    response.append(inputLine);

                                in.close();
                                int code = conn.getResponseCode();
                                if (code == HTTP_OK || code == HTTP_UNAUTHORIZED) {
                                    result = response.toString();
                                    //Анализ полученных данных
                                    JSONObject resultJson = new JSONObject(result);
                                    parseAuth(resultJson);
                                }
                                conn.disconnect();


                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

                if (msg.what == Const.HANDLER_HIDE_TOOLBAR) {
                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_vk_login) {
            webView.loadUrl(Const.VK_AUTH);
            webView.setVisibility(View.VISIBLE);
            webView.bringToFront();
            bottomSheetBehavior.setHideable(true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view.getId() == R.id.btn_fb_login) {
            webView.loadUrl(Const.FB_AUTH);
            webView.setVisibility(View.VISIBLE);
            webView.bringToFront();
            bottomSheetBehavior.setHideable(true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view.getId() == R.id.btn_ok_login) {
            // todo
//            Intent intent = new Intent(this, VerificationActivity.class);
//            Bundle extras = new Bundle();
//            extras.putSerializable(Const.APPLICATION_ITEMS, items);
//            intent.putExtras(extras);
//            startActivity(intent);
        } else if (view.getId() == R.id.btn_inst_login) {
            // TODO: 23.12.2017
        } else if (view.getId() == R.id.iv_language_bsh) {
            Intent intent = new Intent(this, Locale2Activity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(Const.APPLICATION_ITEMS, items);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (view.getId() == R.id.iv_language_toolbar) {
            Intent intent = new Intent(this, Locale2Activity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(Const.APPLICATION_ITEMS, items);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (view.getId() == R.id.iv_help_bsh) {
            makeHelp();
        } else if (view.getId() == R.id.iv_account_toolbar) {
            Intent intent = new Intent(this, AccountActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(Const.APPLICATION_ITEMS, items);
            intent.putExtras(extras);
            startActivity(intent);
        }
//        else if (view.getId() == R.id.ed_search_person_main) {
////            searchebleEdtiText.animate().x(0f).y(0f);
//            Intent intent = new Intent(this, SearchPersonActivity.class);
//            Bundle extras = new Bundle();
//            extras.putSerializable(Const.APPLICATION_ITEMS, items);
//            extras.putSerializable(Const.APPLICATION_PEOPLE, peopleSet);
//            intent.putExtras(extras);
//            startActivity(intent);
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager.getKeyLocaleChanged()) {
            new AsyncAuth(this, this).execute();
        } else {
            if (sessionManager.getKeyLogged()) {
                toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                searchebleEdtiText.setVisibility(View.VISIBLE);
                ll_rating_unloged.setVisibility(View.GONE);
                findViewById(R.id.appBarLayout).setVisibility(View.VISIBLE);
                bottomSheetBehavior.setHideable(true);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {
                toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                toolbar.setVisibility(View.GONE);
                searchebleEdtiText.setVisibility(View.GONE);
                ll_rating_unloged.setVisibility(View.VISIBLE);
                findViewById(R.id.appBarLayout).setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (webView.getVisibility() == View.VISIBLE) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
            webView.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    private void parseAuth(JSONObject response) throws JSONException {
        if (response.getString(Const.STATUS).equals(Const.OK)) {
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.setKeyToken(response.getString(Const.TOKEN));
            sessionManager.setKeyIdUser(response.getInt(Const.USER_ID));
            if (!response.getBoolean(Const.VERIGICATION)) {
                Intent intent = new Intent(this, VerificationActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(Const.APPLICATION_ITEMS, items);
                intent.putExtras(extras);
                startActivity(intent);
            } else if (response.getBoolean(Const.VERIGICATION)) {
                if (!sessionManager.getKeyLogged()) sessionManager.setKeyIsLogged(true);
                searchebleEdtiText.setVisibility(View.VISIBLE);
                ll_rating_unloged.setVisibility(View.GONE);
                findViewById(R.id.appBarLayout).setVisibility(View.VISIBLE);
                sessionManager.setKeyIdUser(response.getInt(Const.ID_TOKEN));
                mainHandler.obtainMessage(Const.HANDLER_HIDE_TOOLBAR).sendToTarget();
                bottomSheetBehavior.setHideable(true);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {

            }
        } else if (response.getString(Const.STATUS).equals(Const.FAIL)) {
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            // todo что делать?
        } else if (response.getString(Const.STATUS).equals(Const.ERROR)) {
            // todo что делать?
        }
    }

    @Override
    public void asyncTitlesPeople(MainScreanData mainScreanData) {
        this.mainScreanDataByTitle = mainScreanData;
        if (mainScreanDataByTitle != null) {
            updateDataRecycler();
            Log.i(LOG_TAG, "IT IS!");
        }
    }

    @Override
    public void asyncMainData(MainScreanData mainScreanData) {
        this.mainScreanData = mainScreanData;
        if (mainScreanData != null) {
            if (sessionManager.getKeyLocaleChanged()) {
                sessionManager.setKeyLocaleChanged(false);
                updateLocaleChangeMainData();
            } else {
                initDataRecycler();
            }
            Log.i(LOG_TAG, "IT IS!");
        }
    }

    private void initDataRecycler() {
        peopleSet = new ArrayList<>();
        Person[] people = mainScreanData.getPeople();
        Collections.addAll(peopleSet, people);

        eventSet = new ArrayList<>();
        final Event[] events = mainScreanData.getEvents();
        Collections.addAll(eventSet, events);

        adapterPerson = new MainAdapterPerson(items, peopleSet, this, this, sessionManager.getKeyLogged(), eventSet.get(eventPosition));
        recyclerViewPeople.setAdapter(adapterPerson);

        adapterEvents = new MainAdapterTitle(eventSet, this);
        adapterEvents.setOnItemClickListener(new MainAdapterTitle.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                new AsyncPersonsViaTitle(context, sessionManager, eventSet.get(position).getIdEvent(), onClickTitleLoader).execute();
            }
        });
        recyclerViewEvents.setAdapter(adapterEvents);

        firstName.setText(peopleSet.get(0).getFirstName());
        secondName.setText(peopleSet.get(0).getSecondName());

        Graph[] tempRating = peopleSet.get(0).getGraph();
        tvDateToday.setText(tempRating[0].getDate());
        tvDateYesterday.setText(tempRating[1].getDate());
        tvDateYesterday1.setText(tempRating[2].getDate());
        tvDateYesterday2.setText(tempRating[3].getDate());
        tvDateYesterday3.setText(tempRating[4].getDate());

        tvRatingToday.setText(tempRating[0].getRating() + "%");
        tvRatingYesterday.setText(tempRating[1].getRating() + "%");
        tvRatingYesterday1.setText(tempRating[2].getRating() + "%");
        tvRatingYesterday2.setText(tempRating[3].getRating() + "%");
        tvRatingYesterday3.setText(tempRating[4].getRating() + "%");

        MainUtil.setProgressAnimate(today, tempRating[0].getRating(), context);
        MainUtil.setProgressAnimate(yesterday, tempRating[1].getRating(), context);
        MainUtil.setProgressAnimate(yesterday1, tempRating[2].getRating(), context);
        MainUtil.setProgressAnimate(yesterday2, tempRating[3].getRating(), context);
        MainUtil.setProgressAnimate(yesterday3, tempRating[4].getRating(), context);

        if (sessionManager.getKeyLogged()) {
            String rating = items.get(Const.LABEL_PI_TODAY) + " " + peopleSet.get(0).getRating() + "%";
            todayRating.setText(rating);
        } else {

        }

        recyclerViewPeople.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = helper2.findSnapView(layoutManagerPeople);
//                    findViewById(R.id.ib_person).setAlpha(0.5f);
                    int personPosition = layoutManagerPeople.getPosition(centerView);
                    try {
                        Person temp = peopleSet.get(personPosition);
                        firstName.setText(temp.getFirstName());
                        secondName.setText(temp.getSecondName());
                        if (sessionManager.getKeyLogged()) {
                            String rating = items.get(Const.LABEL_PI_TODAY) + " " + temp.getRating() + "%";
                            todayRating.setText(rating);
                        } else {
                            Graph[] tempReting = temp.getGraph();

                            try {
                                tvDateToday.setText(tempReting[0].getDate());
                                tvDateYesterday.setText(tempReting[1].getDate());
                                tvDateYesterday1.setText(tempReting[2].getDate());
                                tvDateYesterday2.setText(tempReting[3].getDate());
                                tvDateYesterday3.setText(tempReting[4].getDate());

                                tvRatingToday.setText(tempReting[0].getRating() + "%");
                                tvRatingYesterday.setText(tempReting[1].getRating() + "%");
                                tvRatingYesterday1.setText(tempReting[2].getRating() + "%");
                                tvRatingYesterday2.setText(tempReting[3].getRating() + "%");
                                tvRatingYesterday3.setText(tempReting[4].getRating() + "%");

                                MainUtil.setProgressAnimate(today, tempReting[0].getRating(), context);
                                MainUtil.setProgressAnimate(yesterday, tempReting[1].getRating(), context);
                                MainUtil.setProgressAnimate(yesterday1, tempReting[2].getRating(), context);
                                MainUtil.setProgressAnimate(yesterday2, tempReting[3].getRating(), context);
                                MainUtil.setProgressAnimate(yesterday3, tempReting[4].getRating(), context);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    Log.i("Snapped Item Position:", "" + personPosition);
                }
            }
        });

        recyclerViewEvents.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View centerView = helper.findSnapView(layoutManagerEvent);
                eventPosition = layoutManagerEvent.getPosition(centerView);
            }

        });

    }

    private void updateDataRecycler() {
        peopleSet = new ArrayList<>();
        Person[] people = mainScreanDataByTitle.getPeople();
        Collections.addAll(peopleSet, people);

//        eventSet = new ArrayList<>();
//        final Event[] titles = mainScreanDataByTitle.getEvents();
//        Collections.addAll(eventSet, titles);

        recyclerViewPeople.swapAdapter(adapterPerson, true);
        adapterPerson = new MainAdapterPerson(items, peopleSet, this, this, sessionManager.getKeyLogged(), eventSet.get(eventPosition));
        recyclerViewPeople.setAdapter(adapterPerson);

//        recyclerViewEvents.swapAdapter(adapterEvents, true);
//        adapterEvents = new MainAdapterTitle(eventSet, this);
//        adapterEvents.setOnItemClickListener(new MainAdapterTitle.ClickListener() {
//            @Override
//            public void onLanguageClick(int position, View v) {
//                new AsyncPersonsViaTitle(context, sessionManager, eventSet.get(position).getIdEvent(), onLanguageCountryLoader).execute();
//            }
//        });
//        recyclerViewEvents.setAdapter(adapterEvents);

        firstName.setText(peopleSet.get(0).getFirstName());
        secondName.setText(peopleSet.get(0).getSecondName());
//        middleName.setText(peopleSet.get(0).getMiddleName());

        if (sessionManager.getKeyLogged()) {
            String rating = items.get(Const.LABEL_PI_TODAY) + " " + peopleSet.get(0).getRating() + "%";
            todayRating.setText(rating);
        } else {

        }

    }

    @Override
    public void onLocaleResult(HashMap items) {
        MainActivity.items = items;
        updateData();
    }

    public class myWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            if (url.contains(Const.REDIRECT_ADDRESS_VK) || url.contains(Const.REDIRECT_ADDRESS_FB)) {
                mUrl = url;
                webView.setVisibility(View.GONE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mainHandler.obtainMessage(Const.HANLDER_START_AUTH).sendToTarget();
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        @Override
        public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        public void onPageFinished(android.webkit.WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private void makeHelp() {
        final int[] count = {1, 0, 0}; // экраны помощи
        View mView = getLayoutInflater().inflate(R.layout.help_dialog, null);
        final TextView title = mView.findViewById(R.id.tv_title_help);
        final TextView info = mView.findViewById(R.id.tv_info_help);
        final ImageView imageView = mView.findViewById(R.id.iv_help);
        final Button mAccept = mView.findViewById(R.id.btn_next);
        final Button mDecline = mView.findViewById(R.id.btn_skip);

        final AlertDialog.Builder popupDialog = new AlertDialog.Builder(context);
        popupDialog.setView(mView);

        final AlertDialog dialog = popupDialog.create();

        String titleString = "";
        String textString1 = "";
        String textString2 = "";
        String textString3 = "";
        String applyButton = "";
        String backButton = "";

        for (String value : items.keySet()) {
            switch (value) {
                case Const.HELLO_TITLE:
                    titleString = items.get(value);
                    break;
                case Const.HELP1_TEXT:
                    textString1 = items.get(value);
                    break;
                case Const.HELP2_TEXT:
                    textString2 = items.get(value);
                    break;
                case Const.HELP3_TEXT:
                    textString3 = items.get(value);
                    break;
                case Const.APPLY_BUTTON:
                    applyButton = items.get(value);
                    break;
                case Const.BACK_BUTTON:
                    backButton = items.get(value);
                    break;
            }
        }
        mAccept.setText(applyButton);
        mDecline.setText(backButton);

        title.setText(titleString);
        info.setText(textString1);
        imageView.setImageResource(R.mipmap.help1);

        final String finalTextString = textString2;
        final String finalTextString1 = textString3;
        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count[1] == 0) {
                    count[1] = 1;
                    title.setVisibility(View.GONE);
                    info.setText(finalTextString);
                    imageView.setImageResource(R.mipmap.help2);
                } else if (count[2] == 0) {
                    count[2] = 1;
                    title.setVisibility(View.GONE);
                    info.setText(finalTextString1);
                    imageView.setImageResource(R.mipmap.help3);
                } else if (count[1] == 1 && count[2] == 1) {
                    dialog.dismiss();
                }
            }
        });

        mDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void updateData() {
        // Подгрузка данных
        new AsyncMainData(this, this).execute();
        setValues();

    }

    private void updateLocaleChangeMainData() {
        peopleSet = new ArrayList<>();
        Person[] people = mainScreanData.getPeople();
        Collections.addAll(peopleSet, people);

        eventSet = new ArrayList<>();
        final Event[] events = mainScreanData.getEvents();
        Collections.addAll(eventSet, events);

        recyclerViewPeople.swapAdapter(adapterPerson, true);
        adapterPerson = new MainAdapterPerson(items, peopleSet, this, this, sessionManager.getKeyLogged(), eventSet.get(eventPosition));
        recyclerViewPeople.setAdapter(adapterPerson);

        recyclerViewEvents.swapAdapter(adapterEvents, true);
        adapterEvents = new MainAdapterTitle(eventSet, this);
        adapterEvents.setOnItemClickListener(new MainAdapterTitle.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                new AsyncPersonsViaTitle(context, sessionManager, eventSet.get(position).getIdEvent(), onClickTitleLoader).execute();
            }
        });
        recyclerViewEvents.setAdapter(adapterEvents);

        firstName.setText(peopleSet.get(0).getFirstName());
        secondName.setText(peopleSet.get(0).getSecondName());
//        middleName.setText(peopleSet.get(0).getMiddleName());

        if (sessionManager.getKeyLogged()) {
            String rating = items.get(Const.LABEL_PI_TODAY) + " " + peopleSet.get(0).getRating() + "%";
            todayRating.setText(rating);
        } else {

        }
    }
}
