package edu.tacoma.uw.css.mqunell.webserviceslab;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseAddFragment extends Fragment {

    public static final String TAG = "CourseAddFragment";

    private static final String COURSE_ADD_URL =
            "http://mqunell.000webhostapp.com/web_services_lab/addCourse.php?";

    private CourseAddListener mListener;
    private EditText mCourseIdEditText;
    private EditText mCourseShortDescEditText;
    private EditText mCourseLongDescEditText;
    private EditText mCoursePrereqsEditText;


    public CourseAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course_add, container, false);

        mCourseIdEditText =         v.findViewById(R.id.add_course_id);
        mCourseShortDescEditText =  v.findViewById(R.id.add_course_short_desc);
        mCourseLongDescEditText =   v.findViewById(R.id.add_course_long_desc);
        mCoursePrereqsEditText =    v.findViewById(R.id.add_course_prereqs);

        // Hide the FAB
        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        Button addCourseButton = v.findViewById(R.id.button_add_course);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.addCourse(url);
            }
        });


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CourseAddListener) {
            mListener = (CourseAddListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement CourseAddListener");
        }
    }

    public interface CourseAddListener {
        void addCourse(String url);
    }

    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(COURSE_ADD_URL);

        try {
            String courseId = mCourseIdEditText.getText().toString();
            sb.append("id=");
            sb.append(URLEncoder.encode(courseId, "UTF-8"));

            String courseShortDesc = mCourseShortDescEditText.getText().toString();
            sb.append("&shortDesc=");
            sb.append(URLEncoder.encode(courseShortDesc, "UTF-8"));

            String courseLongDesc = mCourseLongDescEditText.getText().toString();
            sb.append("&longDesc=");
            sb.append(URLEncoder.encode(courseLongDesc, "UTF-8"));

            String coursePrereqs = mCoursePrereqsEditText.getText().toString();
            sb.append("&prereqs=");
            sb.append(URLEncoder.encode(coursePrereqs, "UTF-8"));

            Log.v(TAG, sb.toString());
        }
        catch(Exception e) {
            Log.v("WebServices", "CourseAddFragment - Something wrong witht he url: " + e.getMessage());
        }

        return sb.toString();
    }
}
