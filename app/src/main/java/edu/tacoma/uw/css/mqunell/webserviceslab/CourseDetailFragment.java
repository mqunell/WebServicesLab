package edu.tacoma.uw.css.mqunell.webserviceslab;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.tacoma.uw.css.mqunell.webserviceslab.course.Course;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseDetailFragment extends Fragment {

    public final static String COURSE_ITEM_SELECTED = "course_selected";

    private TextView mCourseIdTextView;
    private TextView mCourseShortDescTextView;
    private TextView mCourseLongDescTextView;
    private TextView mCoursePrereqsTextView;


    public CourseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_course_detail, container, false);
        mCourseIdTextView =         view.findViewById(R.id.course_item_id);
        mCourseShortDescTextView =  view.findViewById(R.id.course_short_desc);
        mCourseLongDescTextView =   view.findViewById(R.id.course_long_desc);
        mCoursePrereqsTextView =    view.findViewById(R.id.course_prereqs);

        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
        floatingActionButton.show();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args != null) {
            // Set course information based on argument passed
            updateView((Course) args.getSerializable(COURSE_ITEM_SELECTED));
        }

    }

    public void updateView(Course course) {
        if (course != null) {
            mCourseIdTextView.setText(course.getCourseId());
            mCourseShortDescTextView.setText(course.getShortDescription());
            mCourseLongDescTextView.setText(course.getLongDescription());
            mCoursePrereqsTextView.setText(course.getPrereqs());
        }
    }
}
