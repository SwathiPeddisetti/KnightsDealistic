package ucf.knightsdealistic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Store;


public class StoreLogin extends ActionBarActivity {

    EditText storetxt, storepasswordtxt ;
    StoreDataSource storeDataSource ;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_store_login);
        session = new UserSessionManager(getApplicationContext());
        storeDataSource = new StoreDataSource(getApplicationContext());
        storetxt = (EditText) findViewById(R.id.txtstoreusername);
        storepasswordtxt = (EditText) findViewById(R.id.txtpassword);
        Button signupbtn = (Button) findViewById(R.id.btnsignup);
        Button signinbtn = (Button) findViewById(R.id.btnsignin);


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storeName = storetxt.getText().toString();
                String storePassword = storepasswordtxt.getText().toString();
                Store store = null;
                try {
                    store= storeDataSource.getStoreByName(storeName);
                    System.out.println("DB Passsword "+store.getStorePassword()+"storePassword"+storePassword+"store.isApproved()"+store.isApproved());
                    if(!store.isApproved()){
                        Toast.makeText(getApplicationContext(), "Approval for store Registration is still pending with the admin", Toast.LENGTH_LONG).show();
                    }else if(store.getStorePassword().equals(storePassword) && store.isApproved()){
                        session.createUserLoginSession(storeName,String.valueOf(store.getStoreId()));
                        Intent intent=new Intent(StoreLogin.this,StoreHomePage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("selectedTab",new String("0"));
                       // intent.putExtra("user", storeName);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Password incorrect", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    System.out.println(e);
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

        });

       signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreLogin.this ,StoreRegistration.class) ;
                startActivity(intent);
                finish();
            }
        });

        final Button btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Intent intent = new Intent(StoreLogin.this,ChangePassword.class);
                                                     //intent.putExtra("storeName",String.valueOf(storeId));
                                                     startActivity(intent);
                                                     finish();
                                                     //Toast.makeText(EditDeal.this, "Back to home page", Toast.LENGTH_SHORT).show();

                                                 }
                                             }
        );

       final Button btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);
        btnForgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StoreLogin.this, UserName.class);
                startActivity(intent);
                finish();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_store_login, menu);
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
