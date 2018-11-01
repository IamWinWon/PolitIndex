package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.User;
import ru.mrwinwon.poltindex.util.Const;

import static ru.mrwinwon.poltindex.util.MainUtil.parseUser;

public class AsyncUserInfo extends AsyncTask<Void, Void, User> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    private OnUserDataLoaded onUserDataLoaded;

    public interface OnUserDataLoaded {
        void asyncMainData(User user);
    }

    public AsyncUserInfo(Context context, OnUserDataLoaded onUserDataLoaded) {
        super();
        this.context = context;
        sessionManager = new SessionManager(context);
        this.onUserDataLoaded = onUserDataLoaded;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected User doInBackground(Void[] v) {
        User user = null;
        String result = HttpHandler.makePostMainScrean(Const.USER_ADDRESS,
                sessionManager.getKeyCurrentLocale(),
                sessionManager.getKeyIdToken(),
                sessionManager.getKeyIdUser(),
                sessionManager.getKeyToken());
        try {
            user = parseUser(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        onUserDataLoaded.asyncMainData(user);
    }
}
