package ucf.knightsdealistic.database.datasource;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import ucf.knightsdealistic.database.DatabaseHandler;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Student;

/**
 * Created by Swathi on 3/28/2015.
 */
public class StudentDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public StudentDataSource(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        System.out.println("(+)open");
        if(databaseHandler!=null && databaseHandler.getWritableDatabase()!=null)
            database = databaseHandler.getWritableDatabase();
        System.out.println("(-)open");
    }

    public void close() {
        if(databaseHandler!=null)
            databaseHandler.close();
    }

    public Student getStudentByNID(String nid){
        database = databaseHandler.getReadableDatabase();
        Student student=null;
        String selectQuery = "SELECT  * FROM " + Student.TABLE_STUDENT+ " WHERE " + Student.COLUMN_NID + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { nid + "" });

        // looping through all rows and adding to list
        if (c.moveToNext()) {
            student= new Student();
            student.setStudentName(c.getString((c.getColumnIndex(Student.COLUMN_STUDENTNAME))));
            student.setStudentPassword(c.getString(c.getColumnIndex(Student.COLUMN_STUDENTPASSWORD)));
            student.setNID(c.getString(c.getColumnIndex(Student.COLUMN_NID)));
            student.setStudentEmailId(c.getString(c.getColumnIndex(Student.COLUMN_STUDENTEMAILID)));
        }
        close();
        return student;
    }
}
