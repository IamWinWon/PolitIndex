package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Biography;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.util.Const;

import static ru.mrwinwon.poltindex.util.MainUtil.parseBiography;

public class AsyncBiography extends AsyncTask<Void, Void, Biography> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    private Person person;
    public OnBigraphyLoader onBigraphyLoader;


    public interface OnBigraphyLoader {
        void asyncTitlesPeople(Biography biography);
    }

    public AsyncBiography(Context context, SessionManager sessionManager, Person person, OnBigraphyLoader biography) {
        this.context = context;
        this.sessionManager = sessionManager;
        this.person = person;
        this.onBigraphyLoader = biography;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Biography doInBackground(Void[] v) {
        Biography biography = null;
        String result = HttpHandler.makePostMainScrean(Const.BIGRAPHY_ADDRESS + person.getIdPerson() + Const.BIOGRAPHY,
                sessionManager.getKeyCurrentLocale(),
                sessionManager.getKeyIdToken(),
                sessionManager.getKeyIdUser(),
                sessionManager.getKeyToken());
        try {
            biography = parseBiography(new JSONObject(result), person);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return biography;
    }

    @Override
    protected void onPostExecute(Biography biography) {
        super.onPostExecute(biography);
        onBigraphyLoader.asyncTitlesPeople(biography);
    }
}
