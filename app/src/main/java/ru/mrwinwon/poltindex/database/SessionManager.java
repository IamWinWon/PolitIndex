package ru.mrwinwon.poltindex.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    private final static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "politIndexPrefs";

    public static final String KEY_BTN_LOGIN = "btn_login";
    public static final String KEY_LABEL_DECISION = "lbl_your_decision";
    public static final String KEY_TITLE_LOCATION = "title_location";
    public static final String KEY_BTN_SELECT_COUNTRY = "btn_select_country";
    public static final String KEY_BTN_SELECT_LANGUGE = "btn_select_language";
    public static final String KEY_LOGIN_FORM_TEXT = "login_form_text";
    public static final String KEY_LOGIN_FORM_LAW_TEXT = "login_form_law_text";
    public static final String KEY_LOGIN_FORM_LAW_LINK = "login_form_law_link";
    public static final String KEY_BACK_BUTTON = "back_button";
    public static final String KEY_APPLY_BUTTON = "apply_button";
    public static final String KEY_LOCALE_EMAIL_TEXT = "locale_email_text";
    public static final String KEY_LABLE_LOADING = "lbl_loading";
    public static final String KEY_TITLE_AUTH = "title_auth";
    public static final String KEY_TITLE_PHONE = "title_phone";
    public static final String KEY_TITLE_SMS = "title_sms";
    public static final String KEY_BTN_SEND_SMS = "btn_send_sms";
    public static final String KEY_LABEL_TYPE_PHONE_NUM = "lbl_type_phone_number";
    public static final String KEY_TEXT_SMS_RULE = "text_sms_rule";
    public static final String KEY_SMS_ERROR_PHONE_FORMAT = "sms_error_phone_format";
    public static final String KEY_SMS_ERROR_DAIL = "sms_error_fail";
    public static final String KEY_GLOBAL_ERROR = "global_error";
    public static final String KEY_SMS_ERROR_LIMIT = "sms_error_limit";
    public static final String KEY_SEARCH_PLACEHOLDER = "search_placeholder";
    public static final String KEY_LABEL_PI_TODAY = "lbl_pi_today";
    public static final String KEY_MONTH = "months";

    public static final String KEY_CURRENT_LOCALE = "currentLocale";
    public static final String KEY_CURRENT_COUNTRY = "currentCountry";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_ID_USER = "idUser";
    public static final String KEY_ID_TOKEN = "idToken";
    public static final String KEY_LOGGED = "isLogged";
    public static final String KEY_LOCALE_CHANGED = "isLocaleChanged";
    public static final String KEY_LANGUAGE_CHANGED = "isLanguageChanged";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveData(HashMap<String, String> items) {
        mEditor.putString(KEY_BTN_LOGIN, items.get(KEY_BTN_LOGIN));
        mEditor.putString(KEY_LABEL_DECISION, items.get(KEY_LABEL_DECISION));
        mEditor.putString(KEY_TITLE_LOCATION, items.get(KEY_TITLE_LOCATION));
        mEditor.putString(KEY_BTN_SELECT_COUNTRY, items.get(KEY_BTN_SELECT_COUNTRY));
        mEditor.putString(KEY_BTN_SELECT_LANGUGE, items.get(KEY_BTN_SELECT_LANGUGE));
        mEditor.putString(KEY_LOGIN_FORM_TEXT, items.get(KEY_LOGIN_FORM_TEXT));
        mEditor.putString(KEY_LOGIN_FORM_LAW_TEXT, items.get(KEY_LOGIN_FORM_LAW_TEXT));
        mEditor.putString(KEY_LOGIN_FORM_LAW_LINK, items.get(KEY_LOGIN_FORM_LAW_LINK));
        mEditor.putString(KEY_BACK_BUTTON, items.get(KEY_BACK_BUTTON));
        mEditor.putString(KEY_APPLY_BUTTON, items.get(KEY_APPLY_BUTTON));
        mEditor.putString(KEY_LOCALE_EMAIL_TEXT, items.get(KEY_LOCALE_EMAIL_TEXT));
        mEditor.putString(KEY_LABLE_LOADING, items.get(KEY_LABLE_LOADING));
        mEditor.putString(KEY_TITLE_AUTH, items.get(KEY_TITLE_AUTH));
        mEditor.putString(KEY_TITLE_PHONE, items.get(KEY_TITLE_PHONE));
        mEditor.putString(KEY_TITLE_SMS, items.get(KEY_TITLE_SMS));
        mEditor.putString(KEY_BTN_SEND_SMS, items.get(KEY_BTN_SEND_SMS));
        mEditor.putString(KEY_LABEL_TYPE_PHONE_NUM, items.get(KEY_LABEL_TYPE_PHONE_NUM));
        mEditor.putString(KEY_TEXT_SMS_RULE, items.get(KEY_TEXT_SMS_RULE));
        mEditor.putString(KEY_SMS_ERROR_PHONE_FORMAT, items.get(KEY_SMS_ERROR_PHONE_FORMAT));
        mEditor.putString(KEY_SMS_ERROR_DAIL, items.get(KEY_SMS_ERROR_DAIL));
        mEditor.putString(KEY_GLOBAL_ERROR, items.get(KEY_GLOBAL_ERROR));
        mEditor.putString(KEY_SMS_ERROR_LIMIT, items.get(KEY_SMS_ERROR_LIMIT));
        mEditor.putString(KEY_SEARCH_PLACEHOLDER, items.get(KEY_SEARCH_PLACEHOLDER));
        mEditor.putString(KEY_LABEL_PI_TODAY, items.get(KEY_LABEL_PI_TODAY));
        mEditor.putString(KEY_MONTH, items.get(KEY_MONTH));
        mEditor.apply();
    }

    public void saveLocaleAndCountry(String locale, String country) {
        mEditor.putString(KEY_CURRENT_COUNTRY, country);
        mEditor.putString(KEY_CURRENT_LOCALE, locale);
        mEditor.apply();
    }

    public String getKeyBtnLogin() {
        return mSharedPreferences.getString(KEY_BTN_LOGIN, "");
    }

    public String getKeyLoginFormText() {
        return mSharedPreferences.getString(KEY_LOGIN_FORM_TEXT, "");
    }

    public String getKeyTitleAuth() {
        return mSharedPreferences.getString(KEY_TITLE_AUTH, "");
    }

    public String getKeyLoginFormLawText() {
        return mSharedPreferences.getString(KEY_LOGIN_FORM_LAW_TEXT, "");
    }

    public String getKeyLoginFormLawLink() {
        return mSharedPreferences.getString(KEY_LOGIN_FORM_LAW_LINK, "");
    }

    public void setKeyToken(String token){
        mEditor.putString(KEY_TOKEN, token);
        mEditor.apply();
    }

    public String getKeyToken() {
        return mSharedPreferences.getString(KEY_TOKEN, "");
    }

    public void setKeyIdUser(int idUser){
        mEditor.putInt(KEY_ID_USER, idUser);
        mEditor.apply();
    }

    public int getKeyIdUser() {
        return mSharedPreferences.getInt(KEY_ID_USER, -1);
    }

    public void setKeyIdToken(int keyIdToken){
        mEditor.putInt(KEY_ID_TOKEN, keyIdToken);
        mEditor.apply();
    }

    public int getKeyIdToken() {
        return mSharedPreferences.getInt(KEY_ID_TOKEN, -1);
    }

    public void setKeyIsLogged(boolean b) {
        mEditor.putBoolean(KEY_LOGGED, b);
        mEditor.apply();
    }

    public boolean getKeyLogged() {
        return mSharedPreferences.getBoolean(KEY_LOGGED, false);
    }

    public void setKeyCurrentCountry(String s) {
        mEditor.putString(KEY_CURRENT_COUNTRY, s);
        mEditor.apply();
    }

    public String getKeyCurrentCountry() {
        return mSharedPreferences.getString(KEY_CURRENT_COUNTRY, "EN");
    }

    public void setKeyCurrentLanguage(String s) {
        mEditor.putString(KEY_CURRENT_LOCALE, s);
        mEditor.apply();
    }

    public String getKeyCurrentLocale() {
        return mSharedPreferences.getString(KEY_CURRENT_LOCALE, "EN");
    }

    public void setKeyLocaleChanged(boolean b) {
        mEditor.putBoolean(KEY_LOCALE_CHANGED, b);
        mEditor.apply();
    }

    public boolean getKeyLocaleChanged() {
        return mSharedPreferences.getBoolean(KEY_LOCALE_CHANGED, false);
    }

    public void setKeyLanguageChanged(boolean b) {
        mEditor.putBoolean(KEY_LANGUAGE_CHANGED, b);
        mEditor.apply();
    }

    public boolean getKeyLanguageChanged() {
        return mSharedPreferences.getBoolean(KEY_LANGUAGE_CHANGED, false);
    }


}
