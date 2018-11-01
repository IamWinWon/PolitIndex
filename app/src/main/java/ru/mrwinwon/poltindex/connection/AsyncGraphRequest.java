package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Commentary;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Graph;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.util.Const;

import static ru.mrwinwon.poltindex.util.MainUtil.parseGraph;

public class AsyncGraphRequest extends AsyncTask<Void, Void, ArrayList<Graph>> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    private Person person;
    private Event event;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progressBar;
    public OnGraphLoader onGraphLoader;


    public interface OnGraphLoader {
        void asyncEventPeople(ArrayList<Graph> graphs);
    }

    public AsyncGraphRequest(Context context, Person person, Event event, OnGraphLoader onGraphLoader) {
        this.context = context;
        this.sessionManager = new SessionManager(context);
        this.person = person;
        this.onGraphLoader = onGraphLoader;
        this.event = event;
//        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Graph> doInBackground(Void[] v) {
        ArrayList<Graph> graphs = new ArrayList<>();
        String result = HttpHandler.makePostMainScrean(Const.MAIN_SCREAN_ADDRESS + event.getIdEvent() + "/" + person.getIdPerson() + Const.GRAPH,
                sessionManager.getKeyCurrentLocale(),
                sessionManager.getKeyIdToken(),
                sessionManager.getKeyIdUser(),
                sessionManager.getKeyToken());
        try {
            graphs = parseGraph(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return graphs;
    }

    @Override
    protected void onPostExecute(ArrayList<Graph> graphs) {
        super.onPostExecute(graphs);
//        progressBar.setVisibility(View.GONE);
        onGraphLoader.asyncEventPeople(graphs);
    }
}
