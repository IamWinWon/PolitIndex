package ru.mrwinwon.poltindex.util;

public class Const {
    // Addresses
    // Test for post string
    public static final String TEST_ADDRESS = "http://politindex.nowords.ru/test.php";
    public static final String LOCALE_ADDRESS = "http://politindex.nowords.ru/v1/locale.api";
    public static final String USER_ADDRESS = "http://politindex.nowords.ru/v1/user.api";
    public static final String LOCALE_SCREAN_ADDRESS = "http://politindex.nowords.ru/v1/location.api";
    public static final String SMS_AUTH_ADDRESS = "http://politindex.nowords.ru/v1/sms/auth.api";
    public static final String PHONE_AUTH_ADDRESS = "http://politindex.nowords.ru/v1/phone/auth.api";
    public static final String SEND_COMMENT_ADDRESS = "http://politindex.nowords.ru/v1/comment.api";
    public static final String LOVE_ADDRESS = "http://politindex.nowords.ru/v1/love.api";
//    public static final String MAIN_SCREAN_ADDRESS = "http://politindex.nowords.ru/v1/ru/event.api";
    public static final String MAIN_SCREAN_ADDRESS = "http://politindex.nowords.ru/v1/";
    public static final String BIGRAPHY_ADDRESS = "http://politindex.nowords.ru/v1/";
    public static final String MAIN_SCREAN_EVENT = "/event.api";
    public static final String BIOGRAPHY = "/bio.api";
    public static final String FIGURE = "/figure.api";
    public static final String GRAPH = "/graph.api";
    public static final String TIMELINE = "/timeline/api";
    public static final String REDIRECT_ADDRESS_VK = "http://politindex.nowords.ru/v1/vk/auth.api?code=";
    public static final String REDIRECT_ADDRESS_FB = "http://politindex.nowords.ru/v1/fb/auth.api?code=";
    public static final String VK_AUTH = "https://oauth.vk.com/authorize?client_id=6219905&display=page&redirect_uri=http://politindex.nowords.ru/v1/vk/auth.api&response_type=code&v=5.68";
    public static final String FB_AUTH = "https://www.facebook.com/v2.10/dialog/oauth?client_id=122584085092280&redirect_uri=http://politindex.nowords.ru/v1/fb/auth.api";

    // "HashMap"
    public static final String APPLICATION_ITEMS = "itemsPolitindex";
    public static final String APPLICATION_PEOPLE = "peoplePolitindex";
    public static final String APPLICATION_PERSON = "personPolitindex";
    public static final String TITLE = "titlePoliindex";
    public static final String SELECTED_ITEM_POSITION = "selectedItemPositiomPolitindex";
    public static final String SELECTED_EVENT = "selectedEventPolitindex";
    public static final String APPLICATION_DATE = "dateChronicsPolitindex";


    public static final String ARG_LANGUAGES = "arg_languages";
    public static final String ARG_COUNTRIES = "arg_countries";

    // Parse request
    public static final String OK = "OK";
    public static final String STATUS = "status";
    public static final String DATA = "data";
    public static final String ITEMS = "items";
    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String CURRENT_LOCALE = "currentLocale";
    public static final String CURRENT_COUNTRY = "currentCountry";
    public static final String TOKEN = "token";
    public static final String ID = "id";
    public static final String ID_TOKEN = "idToken";
    public static final String VERIGICATION = "verification";
    public static final String FAIL = "fail";
    public static final String ERROR = "error";
    public static final String CHARSET = "UTF-8";
    public static final String LINE_FEED = "\r\n";
    public static final String USER_ID = "idUser";
    public static final String COUNTRIES = "countries";
    public static final String ID_COUNTRY = "idCountry";
    public static final String NAME = "name";
    public static final String SNAME = "sname";
    public static final String LOCALE = "locale";
    public static final String LANGUAGES = "languages";
    public static final String ID_LANGUAGE = "idLanguage";
    public static final String BIO = "bio";
    public static final String BIRTH = "birth";
    public static final String AVATAR = "avatar";
    public static final String TELEGRAM = "telegram";
    public static final String SEX = "sex";
    public static final String PROFESSION = "profession";
    public static final String EMAIL = "email";


    // Items
    public static final String BTN_LOGIN = "btn_login";
    public static final String LABEL_DECISION = "lbl_your_decision";
    public static final String TITLE_LOCATION = "title_location";
    public static final String BTN_SELECT_COUNTRY = "btn_select_country";
    public static final String BTN_SELECT_LANGUGE = "btn_select_language";
    public static final String LOGIN_FORM_TEXT = "loginform_text";
    public static final String LOGIN_FORM_LAW_TEXT = "loginform_law_text";
    public static final String LOGIN_FORM_LAW_LINK = "loginform_law_link";
    public static final String BACK_BUTTON = "back_button";
    public static final String APPLY_BUTTON = "apply_button";
    public static final String LOCALE_EMAIL_TEXT = "locale_email_text";
    public static final String LABLE_LOADING = "lbl_loading";
    public static final String TITLE_AUTH = "title_auth";
    public static final String TITLE_PHONE = "title_phone";
    public static final String TITLE_SMS = "title_sms";
    public static final String BTN_SEND_SMS = "btn_send_sms";
    public static final String LABEL_TYPE_PHONE_NUM = "lbl_type_phone_number";
    public static final String TEXT_SMS_RULE = "text_sms_rule";
    public static final String SMS_ERROR_PHONE_FORMAT = "sms_error_phone_format";
    public static final String SMS_ERROR_DAIL = "sms_error_fail";
    public static final String GLOBAL_ERROR = "global_error";
    public static final String SMS_ERROR_LIMIT = "sms_error_limit";
    public static final String SEARCH_PLACEHOLDER = "search_placeholder";
    public static final String LABEL_PI_TODAY = "lbl_pi_today";
    public static final String MONTH = "months";
    public static final String LABEL_PROFILE = "lbl_profie";
    public static final String LABEL_HISTORY = "lbl_history";
    public static final String ERROR_EMPTY_NAME = "errorEdit_emptyname";
    public static final String ERROR_WRONG_EMAIL = "errorEdit_wrongemail";
    public static final String ERROR_ALREADY_EMAIL = "errorEdit_alreadyemail";
    public static final String ERROR_TO_SHORT = "errorEdit_toshort";
    public static final String ERROR_FORMAT = "error_Format";
    public static final String HELLO_TITLE = "hello_title";
    public static final String HELP1_TEXT = "help1_text";
    public static final String HELP2_TEXT = "help2_text";
    public static final String HELP3_TEXT = "help3_text";

    public static final String[] ITEMS_ARRAY = {
            BTN_LOGIN,
            LABEL_DECISION,
            TITLE_LOCATION,
            BTN_SELECT_COUNTRY,
            BTN_SELECT_LANGUGE,
            LOGIN_FORM_TEXT,
            LOGIN_FORM_LAW_TEXT,
            LOGIN_FORM_LAW_LINK,
            BACK_BUTTON,
            APPLY_BUTTON,
            LOCALE_EMAIL_TEXT,
            LABLE_LOADING,
            TITLE_AUTH,
            TITLE_PHONE,
            TITLE_SMS,
            BTN_SEND_SMS,
            LABEL_TYPE_PHONE_NUM,
            TEXT_SMS_RULE,
            SMS_ERROR_PHONE_FORMAT,
            SMS_ERROR_DAIL,
            GLOBAL_ERROR,
            SMS_ERROR_LIMIT,
            SEARCH_PLACEHOLDER,
            LABEL_PI_TODAY,
            MONTH,
            LABEL_PROFILE,
            LABEL_HISTORY,
            ERROR_EMPTY_NAME,
            ERROR_WRONG_EMAIL,
            ERROR_ALREADY_EMAIL,
            ERROR_TO_SHORT
    };


    public static int HANLDER_START_AUTH = 300;
    public static int HANLDER_CHECK_INTERNET = 301;
    public static int HANDLER_HIDE_TOOLBAR = 302;
}
