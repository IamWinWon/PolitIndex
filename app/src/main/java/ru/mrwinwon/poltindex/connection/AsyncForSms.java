package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.util.Const;

public class AsyncForSms extends AsyncTask<Void, Void, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String charset;
    private SessionManager sessionManager;
    private String number;
    private OnAsyncRequestForSmsResult onAsyncRequestForSmsResult;

    public interface OnAsyncRequestForSmsResult {
        void onSmsResult(boolean b);
    }

    public AsyncForSms(Context context, String number, OnAsyncRequestForSmsResult onAsyncRequestForSmsResult) {
        super();
        this.context = context;
        sessionManager = new SessionManager(context);
        this.number = number;
        this.onAsyncRequestForSmsResult = onAsyncRequestForSmsResult;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean result = false;
        try {
            JSONObject resultSms = new JSONObject(HttpHandler.makePostNumber(Const.PHONE_AUTH_ADDRESS, sessionManager.getKeyCurrentLocale(), number, sessionManager.getKeyIdUser(), sessionManager.getKeyToken()));
            if (resultSms.getString(Const.STATUS).equals(Const.OK)) {
                sessionManager.setKeyToken(resultSms.getString(Const.TOKEN));
                result = true;
            } else {
                // todo
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean o) {
        super.onPostExecute(o);
        onAsyncRequestForSmsResult.onSmsResult(o);
    }
}
