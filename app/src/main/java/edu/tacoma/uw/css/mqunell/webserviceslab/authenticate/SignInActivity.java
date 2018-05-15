package edu.tacoma.uw.css.mqunell.webserviceslab.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.tacoma.uw.css.mqunell.webserviceslab.CourseActivity;
import edu.tacoma.uw.css.mqunell.webserviceslab.R;

public class SignInActivity extends AppCompatActivity
        implements LoginFragment.LoginInteractionListener {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGED_IN), false)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.signin_fragment_container, new LoginFragment())
                    .commit();
        }
        else {
            startCourseActivity();
        }
    }

    @Override
    public void login(String email, String pwd) {
        mSharedPreferences.edit()
                .putBoolean(getString(R.string.LOGGED_IN), true)
                .apply();

        startCourseActivity();
    }

    private void startCourseActivity() {
        Intent i = new Intent(this, CourseActivity.class);
        startActivity(i);
        finish();
    }
}
