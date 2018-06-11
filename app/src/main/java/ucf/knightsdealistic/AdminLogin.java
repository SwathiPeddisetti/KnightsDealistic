package ucf.knightsdealistic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ucf.knightsdealistic.database.datasource.AdminDataSource;
import ucf.knightsdealistic.database.model.Admin;

public class AdminLogin extends ActionBarActivity {
    UserSessionManager session;
    EditText adminNameTxt,adminPasswordTxt;
    Button btnAdminSignIn,btnAdminCancel;
    AdminDataSource adminDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_admin_login);
        session = new UserSessionManager(getApplicationContext());
        adminNameTxt=(EditText) findViewById(R.id.txtAdminName);
        adminPasswordTxt = (EditText) findViewById(R.id.txtAdminPassword);
        btnAdminSignIn = (Button) findViewById(R.id.btnAdminSignIn);
        btnAdminCancel = (Button) findViewById(R.id.btnAdminCancel);
        adminDataSource=new AdminDataSource(this);
        btnAdminSignIn.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(View v) {
             String adminName = adminNameTxt.getText().toString();
             String adminPassword = adminPasswordTxt.getText().toString();
             Admin admin=null;
             try {
                 admin= adminDataSource.getAdminByName(adminName);
                 if(admin.getAdminPassword().equals(adminPassword)){
                     session.createUserLoginSession(adminName,String.valueOf(admin.getAdminId()));
                     Intent intent=new Intent(
                             AdminLogin.this,
                             AdminHomePage.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     intent.putExtra("selectedTab",new String("0"));
                     startActivity(intent);
                     finish();
                 }else{
                     Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                 }
             }catch(Exception e){
                 System.out.println(e);
                 Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
             }
          }
        });

        btnAdminCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup group = (ViewGroup) findViewById(R.id.adminLoginLayout);
                Utility.clearForm(group);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
