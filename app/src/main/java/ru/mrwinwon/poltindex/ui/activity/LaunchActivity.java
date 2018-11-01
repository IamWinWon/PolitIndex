package ru.mrwinwon.poltindex.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.connection.AsyncAuth;
import ru.mrwinwon.poltindex.connection.util.Internet;
import ru.mrwinwon.poltindex.util.Const;
import ru.mrwinwon.poltindex.util.MainUtil;

public class LaunchActivity extends AppCompatActivity
        implements AsyncAuth.OnLocaleRequest {

    private static final String LOG_TAG = LaunchActivity.class.getSimpleName();
    private TextView mVersion;
    private Handler mainHandler;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
        new AsyncAuth(this, this).execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mVersion.setText(MainUtil.getAppVersion(this));
    }

    private void init() {
        context = this;
        mVersion = findViewById(R.id.tvVersion);
    }

    @SuppressLint("HandlerLeak")
    private void initHanler () {
//        mainHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == Const.HANLDER_CHECK_INTERNET) {
//                    if (!new Internet(context).checkInternetConnection()) {
//
//                    }
//                }
//            }
//        };

        mainHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!new Internet(context).checkInternetConnection()) {
                    // TODO: 19.01.2018
                }
                mainHandler.postDelayed(this, 5000);
            }
        };

        mainHandler.post(runnable);

    }

    @Override
    public void onLocaleResult(HashMap items) {
        if (items.size() > 0) {
            HashMap<String, String> map = items;
            for(Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                map.put(key, MainUtil.supstituteString(map.get(key)));
            }
            Intent intent = new Intent(this, MainActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(Const.APPLICATION_ITEMS, items);
            intent.putExtras(extras);
            startActivity(intent);
            finish();
        }
    }
}
