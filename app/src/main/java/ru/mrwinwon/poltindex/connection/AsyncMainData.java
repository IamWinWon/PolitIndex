package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.MainScreanData;
import ru.mrwinwon.poltindex.util.Const;

import static ru.mrwinwon.poltindex.util.MainUtil.parseData;

public class AsyncMainData extends AsyncTask<Void, Void, MainScreanData> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    private OnMainDataLoaded onMainDataLoaded;

    public interface OnMainDataLoaded {
        void asyncMainData(MainScreanData mainScreanData);
    }

    public AsyncMainData(Context context, OnMainDataLoaded onMainDataLoaded) {
        super();
        this.context = context;
        sessionManager = new SessionManager(context);
        this.onMainDataLoaded = onMainDataLoaded;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainScreanData doInBackground(Void[] v) {
        MainScreanData mainScreanData = null;
        String result = HttpHandler.makePostMainScrean(Const.MAIN_SCREAN_ADDRESS + sessionManager.getKeyCurrentLocale() + Const.MAIN_SCREAN_EVENT,
                sessionManager.getKeyCurrentLocale(),
                sessionManager.getKeyIdToken(),
                sessionManager.getKeyIdUser(),
                sessionManager.getKeyToken());
        try {
            mainScreanData = parseData(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainScreanData;
    }

    @Override
    protected void onPostExecute(MainScreanData mainScreanData) {
        super.onPostExecute(mainScreanData);
        onMainDataLoaded.asyncMainData(mainScreanData);
    }
}
