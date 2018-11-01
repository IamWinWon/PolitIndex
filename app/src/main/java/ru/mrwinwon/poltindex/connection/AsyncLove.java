package ru.mrwinwon.poltindex.connection;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Figure;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.util.Const;

import static ru.mrwinwon.poltindex.util.MainUtil.parseLove;

public class AsyncLove extends AsyncTask<Void, Void, Figure> {

    private SessionManager sessionManager;
    private Person person;
    private Event event;
    private int idFigure;
    private int idEvent;
    private int isLike;
    public OnFigureLoveLoader onFigureLoveLoader;


    public interface OnFigureLoveLoader {
        void asyncLovePeople(Figure figure);
    }

    public AsyncLove(SessionManager sessionManager, Person person, OnFigureLoveLoader figure, Event event, int idFigure, int idEvent, int isLike) {
        this.sessionManager = sessionManager;
        this.person = person;
        this.onFigureLoveLoader = figure;
        this.event = event;
        this.idFigure = idFigure;
        this.idEvent = idEvent;
        this.isLike = isLike;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Figure doInBackground(Void[] v) {
        Figure figure = null;
        String result = HttpHandler.makeLove(Const.LOVE_ADDRESS,
                sessionManager.getKeyCurrentLocale(),
                sessionManager.getKeyIdToken(),
                sessionManager.getKeyIdUser(),
                sessionManager.getKeyToken(),
                idFigure,
                idEvent,
                isLike);
        try {
            figure = parseLove(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return figure;
    }

    @Override
    protected void onPostExecute(Figure figure) {
        super.onPostExecute(figure);
        onFigureLoveLoader.asyncLovePeople(figure);
    }
}
