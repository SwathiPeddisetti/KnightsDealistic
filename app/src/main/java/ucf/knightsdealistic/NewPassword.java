package ucf.knightsdealistic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Store;


public class NewPassword extends ActionBarActivity {

    EditText newpasswordtxt;
    Button submit;
    StoreDataSource storeDataSource;
    DealDataSource dealDataSource;
    UserSessionManager session;
    long storeId;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        session = new UserSessionManager(getApplicationContext());
        storeDataSource = new StoreDataSource(getApplicationContext());
        newpasswordtxt = (EditText) findViewById(R.id.txtNewPassword);

        storeId = Long.parseLong(getIntent().getStringExtra("StoreId"));
        submit = (Button) findViewById(R.id.btnNewPasswordSubmit);
        storeDataSource=new StoreDataSource(this);
        try {
            store = storeDataSource.getStoreById(storeId);
        }catch(Exception e){
            e.printStackTrace();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpasswordtxt1 = newpasswordtxt.getText().toString();
                try {
                    boolean updated= storeDataSource.enterNewPassword(storeId,newpasswordtxt1);
                    if(updated){
                        Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Password Not Updated", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_password, menu);
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
