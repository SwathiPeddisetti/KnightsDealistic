package ucf.knightsdealistic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Store;


public class UserName extends ActionBarActivity {

    EditText storetxt, storepasswordtxt ;
    StoreDataSource storeDataSource ;
    TextView forgotpasswordtxt ;
    UserSessionManager session;
    long storeId;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_name);

        session = new UserSessionManager(getApplicationContext());
        storeDataSource = new StoreDataSource(getApplicationContext());
        storetxt = (EditText) findViewById(R.id.txtUserStoreName);

        Button submitbtn = (Button) findViewById(R.id.btnSubmitUA);
        final Button btnCancel = (Button) findViewById(R.id.btnCancelUA);
        final Button btnBack = (Button) findViewById(R.id.btnBackUA);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storeName = storetxt.getText().toString();
                Store store = null;
                try {
                    store= storeDataSource.getStoreByName(storeName);
                    System.out.println("DB Passsword "+store.getStorePassword()+"store.isApproved()"+store.isApproved());
                    if(!store.isApproved()){
                        Toast.makeText(getApplicationContext(), "Approval for store Registration is still pending with the admin", Toast.LENGTH_SHORT).show();
                    }else if(store.getStoreName().equals(storeName) && store.isApproved()){
                        session.createUserLoginSession(storeName,String.valueOf(store.getStoreId()));
                        Intent intent=new Intent(UserName.this,SecurityQuestion.class);
                        intent.putExtra("StoreId",String.valueOf(store.getStoreId()));
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Password incorrect", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storetxt.setText("");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(UserName.this, FirstScreen.class);
                                           intent.putExtra("selectedTab",new String("1"));
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_name, menu);
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
