package ru.mrwinwon.poltindex.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ru.mrwinwon.poltindex.connection.util.HttpHandler;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.util.Const;

public class AsyncAuth extends AsyncTask {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    private OnLocaleRequest onLocaleRequest;
    private boolean asyncResponceUserExisting;


    public interface OnLocaleRequest {
        void onLocaleResult(HashMap items);
    }

    public AsyncAuth(Context context, OnLocaleRequest onLocaleRequest) {
        super();
        this.context = context;
        sessionManager = new SessionManager(context);
        this.onLocaleRequest = onLocaleRequest;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HashMap<String, String> doInBackground(Object[] objects) {
        String responce = HttpHandler.makeServiceCallAuth(Const.LOCALE_ADDRESS, sessionManager.getKeyCurrentLocale());
        return languageParser(responce);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        onLocaleRequest.onLocaleResult((HashMap) o);

    }

    private HashMap<String, String> languageParser(String result) {
        HashMap<String, String> itemsThing = new HashMap<>();
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.getString(Const.STATUS).equals(Const.OK)) {
                    JSONArray items = jsonObject.getJSONObject(Const.DATA).getJSONArray(Const.ITEMS);
                    if (items.length() > 0) {

                        for (int x = 0; items.length() > x; x++) {
                            itemsThing.put(items.getJSONObject(x).getString("key"), items.getJSONObject(x).getString("value"));
                        }

//                            itemsThing.put(Const.ITEMS_ARRAY[x], items.getJSONObject(x).getString(Const.VALUE));

                        sessionManager.saveData(itemsThing);
                        asyncResponceUserExisting = true;
                    }
                } else {
                    asyncResponceUserExisting = false;
                    // todo status НЕ ОК
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return itemsThing;
    }
}
