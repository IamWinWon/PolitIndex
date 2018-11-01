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

public class AsyncSendComment extends AsyncTask {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SessionManager sessionManager;
    private OnSendComment onSendComment;
    private int idEvent;
    private int idPerson;
    private String text;
    private boolean asyncResponceUserExisting;


    public interface OnSendComment {
        void onCommentResult(HashMap items);
    }

    public AsyncSendComment(Context context, OnSendComment onSendComment, int idEvent, int idPerson, String text) {
        super();
        this.context = context;
        sessionManager = new SessionManager(context);
        this.onSendComment = onSendComment;
        this.idEvent = idEvent;
        this.idPerson = idPerson;
        this.text = text;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HashMap<String, String> doInBackground(Object[] objects) {
        String responce = HttpHandler.makeComment(Const.SEND_COMMENT_ADDRESS, sessionManager.getKeyCurrentLocale(), sessionManager.getKeyIdToken(), sessionManager.getKeyIdUser(), sessionManager.getKeyToken(), idEvent, idPerson, text);
        return languageParser(responce);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        onSendComment.onCommentResult((HashMap) o);
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
