package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Commentary;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.util.Const;

import static ru.mrwinwon.poltindex.util.MainUtil.parseCommentaries;

public class AsyncFigure extends AsyncTask<Void, Void, ArrayList<Commentary>> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    private Person person;
    private Event event;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progressBar;
    public OnFigureLoader onFigureLoader;


    public interface OnFigureLoader {
        void asyncEventPeople(ArrayList<Commentary> commentaries);
    }

    public AsyncFigure(Context context, SessionManager sessionManager, Person person, OnFigureLoader figure, Event event, ProgressBar progressBar) {
        this.context = context;
        this.sessionManager = sessionManager;
        this.person = person;
        this.onFigureLoader = figure;
        this.event = event;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Commentary> doInBackground(Void[] v) {
        ArrayList<Commentary> commentaries = new ArrayList<>();
        String result = HttpHandler.makePostMainScrean(Const.MAIN_SCREAN_ADDRESS + event.getIdEvent() + "/" + person.getIdPerson() + Const.FIGURE,
                sessionManager.getKeyCurrentLocale(),
                sessionManager.getKeyIdToken(),
                sessionManager.getKeyIdUser(),
                sessionManager.getKeyToken());
        try {
            commentaries = parseCommentaries(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentaries;
    }

    @Override
    protected void onPostExecute(ArrayList<Commentary> commentaries) {
        super.onPostExecute(commentaries);
        progressBar.setVisibility(View.GONE);
        onFigureLoader.asyncEventPeople(commentaries);
    }
}
