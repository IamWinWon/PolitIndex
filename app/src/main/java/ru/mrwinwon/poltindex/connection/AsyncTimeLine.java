package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.model.Timeline;
import ru.mrwinwon.poltindex.model.User;
import ru.mrwinwon.poltindex.util.Const;

import static ru.mrwinwon.poltindex.util.MainUtil.parseTimeline;
import static ru.mrwinwon.poltindex.util.MainUtil.parseUser;

public class AsyncTimeLine extends AsyncTask<Void, Void, ArrayList<Timeline>> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    private OnTimelineDataLoaded onTimelineDataLoaded;

    private String date;
    private Bitmap bitmap;
    private Person person;
    private Event event;

    public interface OnTimelineDataLoaded {
        void asyncTimelineData(ArrayList<Timeline> timelines);
    }

    public AsyncTimeLine(Context context, Event event, Person person, String date, OnTimelineDataLoaded onTimelineDataLoaded) {
        super();
        this.context = context;
        sessionManager = new SessionManager(context);
        this.date = date;
        this.event = event;
        this.person = person;
        this.onTimelineDataLoaded = onTimelineDataLoaded;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Timeline> doInBackground(Void[] v) {
        ArrayList<Timeline> timelines = new ArrayList<>();
        String result = null;
        result = HttpHandler.makePostMainScrean(Const.MAIN_SCREAN_ADDRESS + event.getIdEvent() + "/" + person.getIdPerson() + "/" + date + Const.TIMELINE,
                sessionManager.getKeyCurrentLocale(),
                sessionManager.getKeyIdToken(),
                sessionManager.getKeyIdUser(),
                sessionManager.getKeyToken());
        try {
            timelines = parseTimeline(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timelines;
    }

    @Override
    protected void onPostExecute(ArrayList<Timeline> timelines) {
        super.onPostExecute(timelines);
        onTimelineDataLoaded.asyncTimelineData(timelines);
    }
}
