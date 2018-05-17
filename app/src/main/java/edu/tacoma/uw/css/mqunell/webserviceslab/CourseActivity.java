package edu.tacoma.uw.css.mqunell.webserviceslab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.css.mqunell.webserviceslab.authenticate.SignInActivity;
import edu.tacoma.uw.css.mqunell.webserviceslab.course.Course;

public class CourseActivity extends AppCompatActivity implements
        CourseListFragment.OnListFragmentInteractionListener,
        CourseAddFragment.CourseAddListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.pizza);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // FAB onClick listener
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseAddFragment courseAddFragment = new CourseAddFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, courseAddFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Start CourseListFragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new CourseListFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences sharedPreferences =
                        getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean(getString(R.string.LOGGED_IN), false).apply();

                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;

            case R.id.action_about:
                Toast.makeText(this, "Showing courses in CSS", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_animations:
                startActivity(new Intent(this, AnimationsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListFragmentInteraction(Course item) {
        CourseDetailFragment courseDetailFragment = new CourseDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(CourseDetailFragment.COURSE_ITEM_SELECTED, item);

        courseDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, courseDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addCourse(String url) {
        new AddCourseTask().execute(new String[]{url});

        // Takes you back to the previous fragment by popping the current fragment out
        getSupportFragmentManager().popBackStackImmediate();
    }


    private class AddCourseTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder response = new StringBuilder();
            HttpURLConnection urlConnection = null;

            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s;
                    while ((s = buffer.readLine()) != null) {
                        response.append(s);
                    }

                }
                catch (Exception e) {
                    response = new StringBuilder("Unable to add course. "
                            + e.getMessage());
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }

            return response.toString();
        }

        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was
         * successful. If not, it displays the exception.
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");

                if (status.equals("success")) {
                    Log.v("WebServices", "CourseActivity - Course successfully added");
                }
                else {
                    Log.v("WebServices", "CourseActivity - Failed to add: " + jsonObject.get("error"));
                }
            }
            catch (JSONException e) {
                Log.v("WebServices", "CourseActivity - Something wrong with the data: " + e.getMessage());
            }
        }
    }

}
