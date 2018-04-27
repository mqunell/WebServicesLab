package edu.tacoma.uw.css.mqunell.webserviceslab.course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {

    // Web service JSON names
    public static final String ID = "id";
    public static final String SHORT_DESC = "shortDesc";
    public static final String LONG_DESC = "longDesc";
    public static final String PRE_REQS = "preReqs";

    private String mCourseId;
    private String mShortDescription;
    private String mLongDescription;
    private String mPreReqs;

    public Course(String courseId, String shortDesc, String longDesc, String preReqs) {
        this.mCourseId = courseId;
        this.mShortDescription = shortDesc;
        this.mLongDescription = longDesc;
        this.mPreReqs = preReqs;
    }

    public static List<Course> parseCourseJSON(String courseJSON) throws JSONException {
        List<Course> courseList = new ArrayList<Course>();

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

    public void setCourseId(String courseId) {
        this.mCourseId = courseId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.mShortDescription = shortDescription;
    }

    public String getLongDescription() {
        return mLongDescription;
    }

    public void setLongDescription(String longDescription) {
        this.mLongDescription = longDescription;
    }

    public String getPreReqs() {
        return mPreReqs;
    }

    public void setPreReqs(String preReqs) {
        this.mPreReqs = preReqs;
    }
}
