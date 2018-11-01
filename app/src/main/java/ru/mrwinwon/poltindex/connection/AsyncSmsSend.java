package ru.mrwinwon.poltindex.connection;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.util.Const;

public class AsyncSmsSend extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private OnSmsRequestResult onSmsRequestResult;
    private SessionManager sessionManager;
    private String sms;

    public interface OnSmsRequestResult {
        void asyncSmsResult(boolean b);
    }

    public AsyncSmsSend(String sms, Context context, OnSmsRequestResult onSmsRequestResult) {
        super();
        this.context = context;
        this.sms = sms;
        this.onSmsRequestResult = onSmsRequestResult;
        sessionManager = new SessionManager(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean b = false;
        try {
            JSONObject result = new JSONObject(HttpHandler.makePostSms(Const.SMS_AUTH_ADDRESS, sessionManager.getKeyCurrentLocale(), sms, sessionManager.getKeyIdUser(), sessionManager.getKeyToken()));
            if (result.getString(Const.STATUS).equals(Const.OK)) {
                sessionManager.setKeyToken(result.getString(Const.TOKEN));
                sessionManager.setKeyIdToken(result.getInt(Const.ID_TOKEN));
                sessionManager.setKeyIdUser(result.getInt(Const.USER_ID));
                b = true;
            } else {
                // todo если статус НЕ ок
                b = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    protected void onPostExecute(Boolean o) {
        super.onPostExecute(o);
        onSmsRequestResult.asyncSmsResult(o);
    }
}
