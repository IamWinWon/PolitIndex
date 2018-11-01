package ru.mrwinwon.poltindex.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.util.RoundedCornersTransformation;
import ru.mrwinwon.poltindex.connection.AsyncUpdateUserInfo;
import ru.mrwinwon.poltindex.connection.AsyncUserInfo;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.User;
import ru.mrwinwon.poltindex.util.BlurTransform;
import ru.mrwinwon.poltindex.util.Const;
import ru.mrwinwon.poltindex.util.MainUtil;
import ru.mrwinwon.poltindex.util.UnderlinedTextView;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class AccountActivity extends AppCompatActivity
        implements
        View.OnClickListener,
        AsyncUserInfo.OnUserDataLoaded {

    private RelativeLayout ll_name, ll_email, ll_profession, ll_telegram, ll_date;
    private EditText tv_name, tv_email, tv_profession, tv_telegram, tv_date;
    private TextView tv_name_bottom, tv_email_bottom, tv_profession_bottom, tv_telegram_bottom, tv_date_bottom;
    private ImageView roundAvatar, smoothAvatar;
    private HashMap<String, String> items;
    private TextView title;
    private UnderlinedTextView progile;

    private static final int CAM_REQUEST = 1313;
    private AlertDialog dialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        init();

        new AsyncUserInfo(this, this).execute();
    }

    private void init() {
        context = this;
        // Данные приложения
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            items = (HashMap<String, String>) bundle.getSerializable(Const.APPLICATION_ITEMS);
        }


        Toolbar toolbar = findViewById(R.id.lk_toolbar_account_activity);
        title = toolbar.findViewById(R.id.lk_toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);

        progile = findViewById(R.id.tv_profile);
        progile.setText(items.get(Const.LABEL_PROFILE));

        ll_name = findViewById(R.id.item_name_account);
        ll_email = findViewById(R.id.item_email_account);
        ll_date = findViewById(R.id.item_birth_date_account);
        ll_telegram = findViewById(R.id.item_telegram_account);
        ll_profession = findViewById(R.id.item_profession_account);

        tv_name = ll_name.findViewById(R.id.tv_top_label);
        tv_profession = ll_profession.findViewById(R.id.tv_top_label);
        tv_email = ll_email.findViewById(R.id.tv_top_label);
        tv_date = ll_date.findViewById(R.id.tv_top_label);
        tv_telegram = ll_telegram.findViewById(R.id.tv_top_label);

        tv_name_bottom = ll_name.findViewById(R.id.tv_bottom_label);
        tv_profession_bottom = ll_profession.findViewById(R.id.tv_bottom_label);
        tv_email_bottom = ll_email.findViewById(R.id.tv_bottom_label);
        tv_date_bottom = ll_date.findViewById(R.id.tv_bottom_label);
        tv_telegram_bottom = ll_telegram.findViewById(R.id.tv_bottom_label);

        tv_name_bottom.setText("Ваше имя");
        tv_email_bottom.setText("Почта");
        tv_telegram_bottom.setText("Телеграм профиль");
        tv_profession_bottom.setText("Ваша профессия");
        tv_date_bottom.setText("Дата рождения");

        tv_name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new AsyncUpdateUserInfo(context, "name", tv_name.getText().toString()).execute();
                    return true;
                }
                return false;
            }
        });

        tv_profession.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new AsyncUpdateUserInfo(context, "profession", tv_profession.getText().toString()).execute();
                    return true;
                }
                return false;
            }
        });

        tv_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new AsyncUpdateUserInfo(context, "email", tv_email.getText().toString()).execute();
                    return true;
                }
                return false;
            }
        });

        tv_telegram.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new AsyncUpdateUserInfo(context, "telegram", tv_telegram.getText().toString()).execute();
                    return true;
                }
                return false;
            }
        });

        tv_date.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new AsyncUpdateUserInfo(context, "birth", tv_date.getText().toString()).execute();
                    return true;
                }
                return false;
            }
        });

//        tv_name.setOnKeyListener(this);
//        tv_profession.setOnKeyListener(this);
//        tv_email.setOnKeyListener(this);
//        tv_date.setOnKeyListener(this);
//        tv_telegram.setOnKeyListener(this);

        tv_name_bottom = ll_name.findViewById(R.id.tv_bottom_label);
        tv_email_bottom = ll_email.findViewById(R.id.tv_bottom_label);
        tv_profession_bottom = ll_profession.findViewById(R.id.tv_bottom_label);
        tv_telegram_bottom = ll_telegram.findViewById(R.id.tv_bottom_label);
        tv_date_bottom = ll_date.findViewById(R.id.tv_bottom_label);

        smoothAvatar = findViewById(R.id.iv_smooth_avatar);
        roundAvatar = findViewById(R.id.iv_round_avatar);

        smoothAvatar.setOnClickListener(this);

//        Picasso.with(this).load("https://www.hellomagazine.com/imagenes/celebrities/2017112544285/emma-watson-splits-william-mack-knight/0-224-559/emma%20watson%20t-t.jpg").resize(160,160).transform(new RoundedCornersTransformation(80,0, RoundedCornersTransformation.CornerType.ALL)).centerCrop().into(roundAvatar);
//        Picasso.with(this).load("https://www.hellomagazine.com/imagenes/celebrities/2017112544285/emma-watson-splits-william-mack-knight/0-224-559/emma%20watson%20t-t.jpg").transform(new BlurTransform(this)).into(smoothAvatar);

    }

    @Override
    public void asyncMainData(User user) {
        if (user != null) {
            if (!user.getAvatarUrl().equals("null")) {
                Picasso.with(this).load(user.getAvatarUrl()).resize(160, 160).transform(new RoundedCornersTransformation(80, 0, RoundedCornersTransformation.CornerType.ALL)).centerCrop().into(roundAvatar);
                Picasso.with(this).load(user.getAvatarUrl()).transform(new BlurTransform(this)).into(smoothAvatar);
            } else {

            }

            if (!user.getName().equals("null")) {
                tv_name.setText(user.getName());
                title.setText(user.getName());
            } else {
                tv_name.setText("Имя Фамилия");
                title.setText("Имя Фамилия");
            }

            if (!user.getTelegram().equals("null")) {
                tv_telegram.setText(user.getTelegram());
            } else {
                tv_telegram.setText("Телеграмм");
            }

            if (!user.getProfession().equals("null")) {
                tv_profession.setText(user.getProfession());
            } else {
                tv_profession.setText("Профессия");
            }

            if (!user.getProfession().equals("null")) {
                tv_date.setText(user.getDateBirth());
            } else {
                tv_date.setText("Дата рождения");
            }

            if (!user.getEmail().equals("null")) {
                tv_email.setText(user.getEmail());
            } else {
                tv_email.setText("Электронная почта");
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_smooth_avatar) {
            createDialog();
        } else if (view.getId() == R.id.item_name_account) {

        } else if (view.getId() == R.id.item_telegram_account) {

        } else if (view.getId() == R.id.item_email_account) {

        } else if (view.getId() == R.id.item_profession_account) {

        } else if (view.getId() == R.id.item_birth_date_account) {

        } else if (view.getId() == R.id.tv_cancel) {
            dialog.dismiss();
        } else if (view.getId() == R.id.tv_library) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
            dialog.dismiss();
        } else if (view.getId() == R.id.tv_camera) {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, 0);
            dialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    smoothAvatar.setPadding(0,0,0,0);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    roundAvatar.setImageBitmap(MainUtil.getBitmapClippedCircle(bitmap));
                    smoothAvatar.setImageBitmap(new BlurTransform(this).transform(bitmap));

//                    new AsyncUpdateUserInfo(context, "avatar", bitmap).execute();
                    loadImage(bitmap);

                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    smoothAvatar.setPadding(0,0,0,0);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    roundAvatar.setImageBitmap(MainUtil.getBitmapClippedCircle(bitmap));
                    smoothAvatar.setImageBitmap(new BlurTransform(this).transform(bitmap));

//                    new AsyncUpdateUserInfo(context, "avatar", bitmap).execute();
                    loadImage(bitmap);

                }
                break;
        }
    }

    private void loadImage(final Bitmap bitmap) { // todo
        final SessionManager sessionManager = new SessionManager(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //converting image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] imageBytes = baos.toByteArray();
//                final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //sending image to server
                StringRequest request = new StringRequest(Request.Method.POST, Const.USER_ADDRESS, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
//                        progressDialog.dismiss();
                        if(s.equals("true")){
                            Toast.makeText(AccountActivity.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(AccountActivity.this, "Some error occurred!", Toast.LENGTH_LONG).show();
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(AccountActivity.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("Encoding", sessionManager.getKeyCurrentLocale());
                        params.put("Token", String.valueOf(sessionManager.getKeyToken()));
                        try {
                            params.put("Authorization", sessionManager.getKeyIdToken() + "_" + MainUtil.md5(String.valueOf(sessionManager.getKeyIdToken()) + ":" + sessionManager.getKeyIdUser() + ":" + sessionManager.getKeyToken()));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
//                        params.put("avatar", String.valueOf(imageBytes));


                        return params;
                    }

                    //adding parameters to send
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
//                        parameters.put("avatar", imageString);
                        parameters.put("avatar", String.valueOf(imageBytes));

                        return parameters;
                    }
                };
                RequestQueue rQueue = Volley.newRequestQueue(AccountActivity.this);
                rQueue.add(request);
            }
        }).start();
    }

    private void createDialog() {
        View mView = getLayoutInflater().inflate(R.layout.dialog_avatar, null);
        TextView camera = mView.findViewById(R.id.tv_camera);
        TextView library = mView.findViewById(R.id.tv_library);
        TextView delete = mView.findViewById(R.id.tv_delete);
        TextView cancel = mView.findViewById(R.id.tv_cancel);

        camera.setOnClickListener(this);
        library.setOnClickListener(this);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);

        final AlertDialog.Builder popupDialog = new AlertDialog.Builder(this, R.style.CustomDialog);
        popupDialog.setView(mView);

        dialog = popupDialog.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }
}
