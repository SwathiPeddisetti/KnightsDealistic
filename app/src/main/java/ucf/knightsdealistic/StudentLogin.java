package ucf.knightsdealistic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ucf.knightsdealistic.database.datasource.AdminDataSource;
import ucf.knightsdealistic.database.datasource.StudentDataSource;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Student;


public class StudentLogin extends ActionBarActivity {
    UserSessionManager session;
    EditText studentNIDTxt,studentPasswordTxt;
    Button btnStudentLogin;
    StudentDataSource studentDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_student_login);
        session = new UserSessionManager(getApplicationContext());
        studentNIDTxt=(EditText) findViewById(R.id.Txtnid);
        studentPasswordTxt = (EditText) findViewById(R.id.Txtpwd);
        btnStudentLogin = (Button) findViewById(R.id.Butlogin);
        studentDataSource=new StudentDataSource(this);
        btnStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentNID = studentNIDTxt.getText().toString();
                String studentPassword = studentPasswordTxt.getText().toString();
                Student student =null;
                try {
                    student= studentDataSource.getStudentByNID(studentNID);
                    if(student.getStudentPassword().equals(studentPassword)){
                        session.createUserLoginSession(student.getStudentName(),String.valueOf(student.getNID()));
                        Intent intent=new Intent(
                                StudentLogin.this,
                                StudentHomePage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       // intent.putExtra("user", student.getStudentName());
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                    System.out.println(e);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
