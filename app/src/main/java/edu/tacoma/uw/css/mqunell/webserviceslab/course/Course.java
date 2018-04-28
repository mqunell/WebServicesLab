package edu.tacoma.uw.css.mqunell.webserviceslab.course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {

    // Web service JSON names
    private static final String ID = "id";
    private static final String SHORT_DESC = "shortDesc";
    private static final String LONG_DESC = "longDesc";
    private static final String PRE_REQS = "prereqs";

    private String mCourseId;
    private String mShortDescription;
    private String mLongDescription;
    private String mPrereqs;

    public Course(String courseId, String shortDesc, String longDesc, String prereqs) {
        this.mCourseId = courseId;
        this.mShortDescription = shortDesc;
        this.mLongDescription = longDesc;
        this.mPrereqs = prereqs;
    }

    public static List<Course> parseCourseJSON(String courseJSON) throws JSONException {
        List<Course> courseList = new ArrayList<>();

        if (courseJSON != null) {
            JSONArray arr = new JSONArray(courseJSON);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Course course = new Course(obj.getString(Course.ID),
                        obj.getString(SHORT_DESC),
                        obj.getString(LONG_DESC),
                        obj.getString(Course.PRE_REQS));

                courseList.add(course);
            }
        }

        return courseList;
    }

    public String getCourseId() {
        return mCourseId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getLongDescription() {
        return mLongDescription;
    }

    public String getPrereqs() {
        return mPrereqs;
    }
}
