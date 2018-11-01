package ru.mrwinwon.poltindex.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.connection.AsyncForSms;
import ru.mrwinwon.poltindex.connection.AsyncSmsSend;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.util.Const;

public class VerificationActivity extends AppCompatActivity
        implements
        View.OnClickListener,
        AsyncSmsSend.OnSmsRequestResult,
        AsyncForSms.OnAsyncRequestForSmsResult {

    private Context context;
    private EditText sms1, sms2, sms3, sms4, phoneNumber;
    private TextView smsMessage, sendSms, textPhone;
    private HashMap<String, String> items;
    private ImageButton btnSendSms;
    private SessionManager sessionManager;
    AsyncSmsSend.OnSmsRequestResult onSmsRequestResultl;
    private TextView title;

    private String confirmSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        init();
        setValues();
        setEditTextListener();

    }

    private void init() {
        context = this;
        sms1 = findViewById(R.id.et_sms_1);
        sms2 = findViewById(R.id.et_sms_2);
        sms3 = findViewById(R.id.et_sms_3);
        sms4 = findViewById(R.id.et_sms_4);
        phoneNumber = findViewById(R.id.ed_phone_number);
        smsMessage = findViewById(R.id.tv_sms_info);
        sendSms = findViewById(R.id.tv_send_sms_code);
        btnSendSms = findViewById(R.id.ib_send_sms);
        textPhone = findViewById(R.id.tv_phone_number);
        btnSendSms.setOnClickListener(this);
        onSmsRequestResultl = this;
        sessionManager = new SessionManager(this);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            items = (HashMap<String, String>) bundle.getSerializable(Const.APPLICATION_ITEMS);
        }

        Toolbar toolbar = findViewById(R.id.toolbar_verification);
        title = toolbar.findViewById(R.id.tv_toolbar_title_verification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        title.setText(items.get(Const.TITLE_AUTH));

        textPhone.setText(items.get(Const.LABEL_TYPE_PHONE_NUM));
        confirmSMS = "";
    }

    private void setValues() {
        sendSms.setText(items.get(Const.BTN_SEND_SMS));
        smsMessage.setText(items.get(Const.TEXT_SMS_RULE));
    }

    private void sendSms() {
        new AsyncForSms(this, 7 + phoneNumber.getText().toString(), this).execute(); // todo коды стран потом доделать
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_send_sms) {
            if (phoneNumber.getText().toString().length() == 10) {
                sendSms();
            }
        }
    }

    private void setEditTextListener() {
        sms1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmSMS += charSequence;
                sms2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sms2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmSMS += charSequence;
                sms3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sms3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmSMS += charSequence;
                sms4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sms4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmSMS += charSequence;
                new AsyncSmsSend(confirmSMS, context, onSmsRequestResultl).execute();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onSmsResult(boolean b) {
        if (b) {
            findViewById(R.id.ll_sms_edts).setVisibility(View.VISIBLE);
            smsMessage.setVisibility(View.GONE);
            title.setText(items.get(Const.TITLE_AUTH));
        } else {
            // todo
        }
    }

    @Override
    public void asyncSmsResult(boolean b) {
        if (b) {
            sessionManager.setKeyIsLogged(true);
            finish();
        } else {
            // todo если с смс все плохо - спросить
        }
    }
}
