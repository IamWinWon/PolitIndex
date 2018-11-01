package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.User;
import ru.mrwinwon.poltindex.util.Const;
import ru.mrwinwon.poltindex.util.MainUtil;

import static ru.mrwinwon.poltindex.util.MainUtil.parseUser;

public class AsyncUpdateUserInfo extends AsyncTask<Void, Void, User> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
//    private OnUserDataLoaded onUserDataLoaded;

    private String key, value;
    private Bitmap bitmap;

//    public interface OnUserDataLoaded {
//        void asyncMainData(User user);
//    }

    public AsyncUpdateUserInfo(Context context, String key, String value) {
        super();
        this.context = context;
        sessionManager = new SessionManager(context);
//        this.onUserDataLoaded = onUserDataLoaded;
        this.key = key;
        this.value = value;
    }

    public AsyncUpdateUserInfo(Context context,String key, Bitmap bitmap) {
        this.context = context;
        sessionManager = new SessionManager(context);
        this.key = key;
        this.value = value;
        this.bitmap = bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected User doInBackground(Void[] v) {
        User user = null;
        String result = null;
        if (bitmap != null) {
            try {
                result = HttpHandler.makePostUserAvatar(Const.USER_ADDRESS, // todo
                        sessionManager.getKeyCurrentLocale(),
                        sessionManager.getKeyIdToken(),
                        sessionManager.getKeyIdUser(),
                        sessionManager.getKeyToken(),
                        key, MainUtil.convertToFile(bitmap, context));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            result = HttpHandler.makePostUserData(Const.USER_ADDRESS,
                    sessionManager.getKeyCurrentLocale(),
                    sessionManager.getKeyIdToken(),
                    sessionManager.getKeyIdUser(),
                    sessionManager.getKeyToken(),
                    key, value);
        }
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
//        onUserDataLoaded.asyncMainData(user);
    }
}
