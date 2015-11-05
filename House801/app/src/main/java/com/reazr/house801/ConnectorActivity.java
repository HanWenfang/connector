package com.reazr.house801;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class ConnectorActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private RecordTask mRecordTask = null;

    // UI references.
    private EditText mHostView;
    private EditText mPortView;
    private View mProgressView;
    private View mConnectorFormView;
    private CheckBox tcpCheckBox;
    private CheckBox webCheckBox;
    private TextView typeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connector);

        mHostView = (EditText) findViewById(R.id.host);
        mPortView = (EditText) findViewById(R.id.cport);
        mConnectorFormView = findViewById(R.id.connector_form);
        mProgressView = findViewById(R.id.progress);
        tcpCheckBox = (CheckBox) findViewById(R.id.tcpCheckBox);
        webCheckBox = (CheckBox) findViewById(R.id.webCheckBox);
        typeText = (TextView) findViewById(R.id.typeText);

        Button mConnectorFormButton = (Button) findViewById(R.id.connector_form_button);
        mConnectorFormButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                record();
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ConnectorActivity.class);
        context.startActivities(new Intent[]{intent});
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void record() {
        if (mRecordTask != null) {
            return;
        }

        // Reset errors.
        mHostView.setError(null);
        mPortView.setError(null);

        // Store values at the time of the login attempt.
        String host = mHostView.getText().toString();
        String port = mPortView.getText().toString();
        int portNumber = -1;
        int type = -1;

        if(tcpCheckBox.isChecked()) {
            type = 1;
        } else if (webCheckBox.isChecked()) {
            type = 3;
        }

        boolean cancel = false;
        View focusView = null;

        try {
             portNumber = Integer.valueOf(port).intValue();
        } catch (Exception e) {
            mPortView.setError("port error");
            focusView = mPortView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(host)) {
            mHostView.setError("Host is require");
            focusView = mHostView;
            cancel = true;
        }

        if (type < 0) {
            tcpCheckBox.setError("type error");
            focusView = tcpCheckBox;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mRecordTask = new RecordTask(host, portNumber, type);
            mRecordTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mConnectorFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mConnectorFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mConnectorFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mConnectorFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class RecordTask extends AsyncTask<Void, Void, Boolean> {

        private final String mHost;
        private final int mPort;
        private final int mType;

        RecordTask(String host, int port, int type) {
            mHost= host;
            mPort = port;
            mType = type;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRecordTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mHostView.setError("some error happens.");
                mHostView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mRecordTask = null;
            showProgress(false);
        }
    }
}

