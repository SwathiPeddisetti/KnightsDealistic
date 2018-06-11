package ucf.knightsdealistic.database.model;

/**
 * Created by Swathi on 3/13/2015.
 */
public class Student {

    private String NID;
    private String studentEmailId;
    private String studentName;
    private String studentPassword;

    public Student(){

    }

    public Student(String NID, String studentEmailId, String studentName, String studentPassword) {
        this.NID = NID;
        this.studentEmailId = studentEmailId;
        this.studentName = studentName;
        this.studentPassword = studentPassword;
    }

    public static final String TABLE_STUDENT= "student";
    public static final String COLUMN_NID = "nid";
    public static final String COLUMN_STUDENTEMAILID = "studentemailid";
    public static final String COLUMN_STUDENTNAME = "studentname";
    public static final String COLUMN_STUDENTPASSWORD = "studentpassword";


    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmailId() {
        return studentEmailId;
    }

    public void setStudentEmailId(String studentEmailId) {
        this.studentEmailId = studentEmailId;
    }
}
