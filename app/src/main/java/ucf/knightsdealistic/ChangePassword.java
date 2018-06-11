package ucf.knightsdealistic;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import ucf.knightsdealistic.database.DatabaseHandler;
import ucf.knightsdealistic.database.datasource.AdminDataSource;
import ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;



public class ChangePassword extends ActionBarActivity {

    EditText storeNameTxt,oldPasswordTxt,newPasswordTxt,confirmnewPasswordTxt;
    //StoreDataSource storeDataSource;
    //Button btnUpdate;
    DatabaseHandler dbhandler;
    List<Store> stores=new ArrayList<Store>();
    Long storeId=null;
    Store store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_password);

        storeNameTxt = (EditText) findViewById(R.id.txtStoreName);
        oldPasswordTxt = (EditText) findViewById(R.id.txtOldPassword);
        newPasswordTxt = (EditText) findViewById(R.id.txtNewPassword);
        confirmnewPasswordTxt = (EditText) findViewById(R.id.txtConfirmNewPassword);
        dbhandler = new DatabaseHandler(getApplicationContext());

        final Button  UpdateBtn= (Button) findViewById(R.id.btnUpdate);
        UpdateBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String storeName = storeNameTxt.getText().toString();
                String oldPassword = oldPasswordTxt.getText().toString();
                String newPassword = newPasswordTxt.getText().toString();

                StoreDataSource s = new StoreDataSource(getApplicationContext());
                boolean changed=true;

                try {
                    changed = s.updateStorePassword(storeName, oldPassword, newPassword);
                    if(changed){
                        Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Passwords/StoreName does not match", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error occurred.Please try again", Toast.LENGTH_LONG).show();
                }

            }
        });

        final Button backbtn = (Button) findViewById(R.id.btnBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(
                        ChangePassword.this,
                        FirstScreen.class);

                startActivity(i);
                finish();
            }
        });

        final Button cancelbtn = (Button) findViewById(R.id.btnCancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                storeNameTxt.setText("");
                oldPasswordTxt.setText("");
                newPasswordTxt.setText("");
                confirmnewPasswordTxt.setText("");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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
