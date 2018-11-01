package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Translete;
import ru.mrwinwon.poltindex.util.Const;

import static ru.mrwinwon.poltindex.util.MainUtil.parseLocale;


public class AsyncLocale extends AsyncTask<Void, Void, Translete> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    public OnLanguageCountryLoader onLanguageCountryLoader;


    public interface OnLanguageCountryLoader {
        void asyncLocale(Translete mainScreanData);
    }

    public AsyncLocale(Context context, SessionManager sessionManager, OnLanguageCountryLoader onLanguageCountryLoader) {
        this.context = context;
        this.sessionManager = sessionManager;
        this.onLanguageCountryLoader = onLanguageCountryLoader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Translete doInBackground(Void[] v) {
        Translete translete = null;
        String result = HttpHandler.makePostMainScrean(Const.LOCALE_SCREAN_ADDRESS,
                sessionManager.getKeyCurrentLocale(),
                sessionManager.getKeyIdToken(),
                sessionManager.getKeyIdUser(),
                sessionManager.getKeyToken());
        try {
            translete = parseLocale(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return translete;
    }

    @Override
    protected void onPostExecute(Translete translete) {
        super.onPostExecute(translete);
        onLanguageCountryLoader.asyncLocale(translete);
    }
}
