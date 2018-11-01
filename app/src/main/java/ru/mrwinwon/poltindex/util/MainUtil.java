package ru.mrwinwon.poltindex.util;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Biography;
import ru.mrwinwon.poltindex.model.Commentary;
import ru.mrwinwon.poltindex.model.Country;
import ru.mrwinwon.poltindex.model.Event;
import ru.mrwinwon.poltindex.model.Figure;
import ru.mrwinwon.poltindex.model.Graph;
import ru.mrwinwon.poltindex.model.Language;
import ru.mrwinwon.poltindex.model.MainScreanData;
import ru.mrwinwon.poltindex.model.Person;
import ru.mrwinwon.poltindex.model.Timeline;
import ru.mrwinwon.poltindex.model.Translete;
import ru.mrwinwon.poltindex.model.User;

import static android.content.Context.WINDOW_SERVICE;

public class MainUtil {
    /**
     * @return App versioan eg 1.1
     */
    public static String getAppVersion(Context context) {
        try {
            if (context != null) {
                PackageInfo _info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return _info.versionName;
            } else {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param context
     * @return current phone locale
     */
    public static String getAppLocale(Context context) {
        Locale current = context.getResources().getConfiguration().locale;
        return current.getCountry();
    }


    /**
     * @param is input stream
     * @return String result
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * Hash code
     *
     * @param st - hashing string
     * @return hash coded string
     */
    public static String md5(String st) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(st.getBytes());

        byte byteData[] = md.digest();

        //конвертируем байт в шестнадцатеричный формат вторым способом
        StringBuffer hexString = new StringBuffer();
        for (byte aByteData : byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Custom method to get screen width in dp/dip using Context object
    public static int getScreenWidthInDPs(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);
        return widthInDP;
    }

    // Custom method to get screen height in dp/dip using Context object
    public static int getScreenHeightInDPs(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int heightInDP = Math.round(dm.heightPixels / dm.density);
        return heightInDP;
    }

    /**
     * @param activity
     * @return
     */
    public static int getPersonRecylerHeight(Activity activity, Context context) {
        int height = getScreenHeightInDPs(context);
        int x = (int) (height * 0.39);
        return (int) convertDpToPixel(height - x, context);
    }

    /**
     * @param activity
     * @return
     */
    public static int getPersonRecylerHeightLogged(Activity activity, Context context) {
        int height = getScreenHeightInDPs(context);
        int x = (int) (height * 0.49);
        return (int) convertDpToPixel(height - x, context);
    }

    /**
     * @param activity
     * @return screen size - 200 px
     */
    public static int kScreanHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;
    }

    /**
     * @param height screans height in px
     * @return width of person image
     */
    public static int kWidth(int height) {
        return (int) (height / 1.410);
    }

    /**
     * @param activity Activity to get resources and device specific display metrics
     * @return needed height of persons image )scren size - 445 px
     */
    public static int kHeightPersonsPics(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height - 445;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) dp;
    }

    /**
     * @param pb  Progress bar
     * @param max maximum of it
     */
    public static void setProgressMax(ProgressBar pb, int max) {
        pb.setMax(max * 100);
    }

    /**
     * @param pb         Progress bar
     * @param progressTo from 0 to it (0-100)
     */
    public static void setProgressAnimate(ProgressBar pb, int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    /**
     * @param pb         progress bar
     * @param progressTo from 0 to it (0-100)
     * @param context
     */
    public static void setProgressAnimate(ProgressBar pb, int progressTo, Context context) {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        Drawable draw;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (progressTo > 50)
                draw = context.getDrawable(R.drawable.verticalprogressbar_positive);
            else draw = context.getDrawable(R.drawable.verticalprogressbar_negative);
        } else {
            if (progressTo > 50)
                draw = context.getResources().getDrawable(R.drawable.verticalprogressbar_positive);
            else draw = context.getResources().getDrawable(R.drawable.verticalprogressbar_negative);
        }
        pb.setProgressDrawable(draw);
        animation.start();
    }

    /**
     * @param jsonObject for parse main data
     * @return main data
     */
    public static MainScreanData parseData(JSONObject jsonObject) {
        MainScreanData mainScreanData = null;
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            int titleCounts = data.getInt("itemCount");
            int personCounts = data.getInt("figureCount");
            JSONArray titles = data.getJSONArray("items");
            JSONArray persons = data.getJSONArray("figures");

            Event[] titles1 = new Event[titleCounts];
            for (int x = 0; x < titles.length(); x++) {
                JSONObject title = titles.getJSONObject(x);
                int idEvent = title.getInt("idEvent");
                String sTitle = title.getString("title");
                String icon = title.getString("icon");
                titles1[x] = new Event(idEvent, sTitle, icon);
            }

            Person[] people = new Person[personCounts];
            for (int x = 0; x < persons.length(); x++) {
                JSONObject personsJSONObject = persons.getJSONObject(x);
                int idFigure = personsJSONObject.getInt("idFigure");
                String firstname = personsJSONObject.getString("firstname");
                String middlename = personsJSONObject.getString("middlename");
                String lastname = personsJSONObject.getString("lastname");
                String avatar = personsJSONObject.getString("avatar");
                String avatarSmall = personsJSONObject.getString("avatarSmall");
                int isLiked = personsJSONObject.getInt("isLiked");

                // today
                int like = personsJSONObject.getJSONObject("today").getInt("like");
                int dislike = personsJSONObject.getJSONObject("today").getInt("dislike");
                int rating = personsJSONObject.getJSONObject("today").getInt("rating");

                // Graph
                Graph[] graphs = new Graph[5];
                JSONArray graphArray = personsJSONObject.getJSONObject("graph").getJSONArray("items");
                for (int z = 0; z < 5; z++) {
                    String date = graphArray.getJSONObject(z).getString("date");
                    int likeGraph = graphArray.getJSONObject(z).getInt("like");
                    int dislikeGraph = graphArray.getJSONObject(z).getInt("dislike");
                    int ratingGraph = graphArray.getJSONObject(z).getInt("rating");
                    String fulldate = graphArray.getJSONObject(z).getString("fulldate");
                    int comments = graphArray.getJSONObject(z).getInt("comments");
                    graphs[z] = new Graph(date, likeGraph, ratingGraph, comments, fulldate, dislikeGraph);
                }

                people[x] = new Person(idFigure, firstname, middlename, lastname, avatar, avatarSmall, isLiked,
                        like, dislike, rating,
                        graphs);

            }

            mainScreanData = new MainScreanData(titleCounts, personCounts, people, titles1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainScreanData;
    }

    public static Biography parseBiography(JSONObject jsonObject, Person person) {
        Biography biography = null;
        try {
            if (jsonObject.getString(Const.STATUS).equals(Const.OK)) {
                String bio = jsonObject.getString(Const.BIO);
                String birth = jsonObject.getString(Const.BIRTH);
                biography = new Biography(person.getIdPerson(), bio, birth);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return biography;
    }

    public static Translete parseLocale(JSONObject jsonObject) {
        Translete translete = null;
        try {
            if (jsonObject.getString(Const.STATUS).equals(Const.OK)) {
                JSONObject data = jsonObject.getJSONObject(Const.DATA);
                JSONArray countriesJSON = data.getJSONArray(Const.COUNTRIES);
                JSONArray languagesJSON = data.getJSONArray(Const.LANGUAGES);

                ArrayList<Country> countries = new ArrayList<>();
                for (int i = 0; i < countriesJSON.length(); i++) {
                    int idCountry = countriesJSON.getJSONObject(i).getInt(Const.ID_COUNTRY);
                    String top = countriesJSON.getJSONObject(i).getString(Const.NAME);
                    String bottom = countriesJSON.getJSONObject(i).getString(Const.SNAME);
                    String localeString = countriesJSON.getJSONObject(i).getString(Const.LOCALE);
                    countries.add(new Country(idCountry, top, bottom, localeString));
                }

                ArrayList<Language> languages = new ArrayList<>();
                for (int i = 0; i < languagesJSON.length(); i++) {
                    int idCountry = languagesJSON.getJSONObject(i).getInt(Const.ID_LANGUAGE);
                    String top = languagesJSON.getJSONObject(i).getString(Const.NAME);
                    String bottom = languagesJSON.getJSONObject(i).getString(Const.SNAME);
                    String localeString = languagesJSON.getJSONObject(i).getString(Const.LOCALE);
                    languages.add(new Language(idCountry, top, bottom, localeString));
                }

                translete = new Translete(countries, languages);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return translete;
    }

    public static void makeHelp(Activity activity, Context context, SessionManager sessionManager, final HashMap<String, String> items) {
        final HashMap<String, String> map = items;
        final int[] count = {1, 0, 0}; // экраны помощи
        View mView = activity.getLayoutInflater().inflate(R.layout.help_dialog, null);
        final TextView title = mView.findViewById(R.id.tv_title_help);
        final TextView info = mView.findViewById(R.id.tv_info_help);
        final ImageView imageView = mView.findViewById(R.id.iv_help);
        final Button mAccept = mView.findViewById(R.id.btn_next);
        final Button mDecline = mView.findViewById(R.id.btn_skip);

        final AlertDialog.Builder popupDialog = new AlertDialog.Builder(context);
        popupDialog.setView(mView);

        final AlertDialog dialog = popupDialog.create();

        title.setText(map.get("hello_title"));
        info.setText(map.get(Const.HELP1_TEXT));
        imageView.setImageResource(R.mipmap.help1);

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count[1] == 0) {
                    count[1] = 1;
                    title.setVisibility(View.GONE);
                    info.setText(map.get("help2_text"));
                    imageView.setImageResource(R.mipmap.help2);
                } else if (count[2] == 0) {
                    count[2] = 1;
                    title.setVisibility(View.GONE);
                    info.setText(map.get("help3_text"));
                    imageView.setImageResource(R.mipmap.help3);
                } else if (count[1] == 1 && count[2] == 1) {
                    dialog.dismiss();
                }
            }
        });

        mDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        dialog.show();

    } // todo ?

    public static User parseUser (JSONObject jsonObject) {
        User user = null;
        try {
            if (jsonObject.getString(Const.STATUS).equals(Const.OK)) {
                JSONObject data = jsonObject.getJSONObject(Const.DATA);
                String avatar = data.getString(Const.AVATAR);
                String name = data.getString(Const.NAME);
                String telegram = data.getString(Const.TELEGRAM);
                String email = data.getString(Const.EMAIL);
                String birth = data.getString(Const.BIRTH);
                String sex = data.getString(Const.SEX);
                String profession = data.getString(Const.PROFESSION);

                user = new User(
                        name,
                        email,
                        profession,
                        telegram,
                        birth,
                        avatar,
                        sex);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<Commentary> parseCommentaries(JSONObject jsonObject) {
        ArrayList<Commentary> commentaries = new ArrayList<>();
        try {
            JSONObject data = jsonObject.getJSONObject(Const.DATA);
            JSONArray jsonArray = data.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                int idComment = jsonArray.getJSONObject(i).getInt("idComment");
                String fullDate = jsonArray.getJSONObject(i).getString("fulldate");
                String text = jsonArray.getJSONObject(i).getString("text");
                String avatar = jsonArray.getJSONObject(i).getString("avatar");
                String name = jsonArray.getJSONObject(i).getString("name");
                boolean itSelf = jsonArray.getJSONObject(i).getBoolean("isSelf");

                int itemCount = data.getInt("itemCount");
                JSONObject figure = data.getJSONObject("figure");
                int likes = figure.getInt("like");
                int dislikes = figure.getInt("dislike");
                int rating = figure.getInt("rating");
                int isLiked = figure.getInt("isLiked");
                ArrayList<Figure> figures = new ArrayList<>();
                figures.add(new Figure(likes, dislikes, rating, isLiked));
                commentaries.add(new Commentary(idComment, fullDate, text, avatar, name, itSelf, itemCount, figures));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentaries;
    }

    public static ArrayList<Graph> parseGraph(JSONObject jsonObject) {
        ArrayList<Graph> graphs = new ArrayList<>();
        try {
            JSONObject data = jsonObject.getJSONObject(Const.DATA);
            JSONArray jsonArray = data.getJSONArray("items");

            int itemCount = data.getInt("itemCount");

            for (int i = 0; i < jsonArray.length(); i++) {

                int likes = jsonArray.getJSONObject(i).getInt("like");
                int dislikes = jsonArray.getJSONObject(i).getInt("dislike");
                int rating = jsonArray.getJSONObject(i).getInt("rating");
                int comments = jsonArray.getJSONObject(i).getInt("comments");
                String fullDate = jsonArray.getJSONObject(i).getString("fulldate");
                String date = jsonArray.getJSONObject(i).getString("date");

                graphs.add(new Graph(date, likes, rating, comments, fullDate, dislikes));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return graphs;
    }


    public static ArrayList<Timeline> parseTimeline (JSONObject jsonObject) {
        ArrayList<Timeline> timelines = new ArrayList<>();
        try {
            JSONObject data = jsonObject.getJSONObject(Const.DATA);
            JSONArray jsonArray = data.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {

                int idUser = jsonArray.getJSONObject(i).getInt("idUser");
                String name  = jsonArray.getJSONObject(i).getString("name");
                String avatar = jsonArray.getJSONObject(i).getString("avatar");
                int type = jsonArray.getJSONObject(i).getInt("type");
                String text = jsonArray.getJSONObject(i).getString("text");
                int like = jsonArray.getJSONObject(i).getInt("like");
                String fullDate = jsonArray.getJSONObject(i).getString("fulldate");
                boolean isYours = jsonArray.getJSONObject(i).getBoolean("isYour");

                timelines.add(new Timeline(idUser, name, avatar, type, text, like, fullDate, isYours));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timelines;
    }


    public static Figure parseLove(JSONObject jsonObject) {
        Figure figure = null;
        try {
            int likes = jsonObject.getInt("like");
            int dislikes = jsonObject.getInt("dislike");
            int rating = jsonObject.getInt("rating");
            int isLiked = jsonObject.getInt("isLiked");
            figure = new Figure(likes, dislikes, rating, isLiked);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return figure;
    }

    /**
     *
     * @param s deletes all appearence of '\\n' and add normal '\n'
     * @return
     */
    public static String supstituteString(String s) {
        return s.replace("\\n", "\n");
    }

    // TODO: 20.01.2018
    public static int calculateRating(int likes, int dislikes) {
        return (likes/(likes+dislikes))*100;
    }

    /**
     *
     * @param bitmap make it cyrcle
     * @return
     */
    public static Bitmap getBitmapClippedCircle(Bitmap bitmap) {

        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        final Path path = new Path();
        path.addCircle(
                (float)(width / 2)
                , (float)(height / 2)
                , (float) Math.min(width, (height / 2))
                , Path.Direction.CCW);

        final Canvas canvas = new Canvas(outputBitmap);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, null);
        return outputBitmap;
    }


    /**
     *
     * @param email verify email
     * @return
     */
    public static boolean isValidEmail(String email)
    {
        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }

    public static File convertToFile(Bitmap bitmap, Context context) throws IOException {
        String name = "AvatarPolitindex" + new Date().getTime();
        //create a file to write bitmap data
        File f = new File(context.getCacheDir(), name);
        f.createNewFile();

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        return f;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    /**
     *
     * @param time - выбранная дата
     * @param months - месяцы, что приходят
     * @return
     */
    public static String makeTimeHeader(String time, String months) {  // 2018-01-20T08:30:13+00:00  Янв,Фев,Мар,Апр,Мая,Июн,Июл,Авг,Сен,Окт,Ноя,Дек
        String tempTime = time.substring(5,7);
        String result = months.substring(0, 3);
        switch (tempTime) {
            case "01":
                result = months.substring(0, 3);
                break;
            case "02":
                result = months.substring(4, 7);
                break;
            case "03":
                result = months.substring(8, 11);
                break;
            case "04":
                result = months.substring(12, 15);
                break;
            case "05":
                result = months.substring(16, 19);
                break;
            case "06":
                result = months.substring(20, 23);
                break;
            case "07":
                result = months.substring(24, 27);
                break;
            case "08":
                result = months.substring(28, 31);
                break;
            case "09":
                result = months.substring(32, 35);
                break;
            case "10":
                result = months.substring(36, 39);
                break;
            case "11":
                result = months.substring(40, 43);
                break;
            case "12":
                result = months.substring(44, 47);
                break;
        }
        return result;
    }


}
