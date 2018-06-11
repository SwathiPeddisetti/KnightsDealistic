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

import ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;


public class SecurityQuestion extends ActionBarActivity {
    EditText securityquestiontxt, securityanswertxt;
    Button submit;
    StoreDataSource storeDataSource;
    DealDataSource dealDataSource;
    UserSessionManager session;
    long storeId;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_security_question);

        session = new UserSessionManager(getApplicationContext());
        storeDataSource = new StoreDataSource(getApplicationContext());
        securityquestiontxt = (EditText) findViewById(R.id.txtSecurityQuestion);
        securityanswertxt = (EditText) findViewById(R.id.txtSecurityAnswer);

        storeId = Long.parseLong(getIntent().getStringExtra("StoreId"));
        submit = (Button) findViewById(R.id.btnSubmitSQ);
        final Button btnCancel = (Button) findViewById(R.id.btnCancelSQ);
        final Button btnBack = (Button) findViewById(R.id.btnBackSQ);
        storeDataSource=new StoreDataSource(this);
        try {
            store = storeDataSource.getStoreById(storeId);
        }catch(Exception e){
            e.printStackTrace();
        }

        this.securityquestiontxt.setText(store.getSecurityQuestion());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String securityquestion = securityquestiontxt.getText().toString();
                String securityanswer = securityanswertxt.getText().toString();
                Store store = null;
                try {
                    store= storeDataSource.getSecurityAnswerByQuestion(storeId, securityquestion);
                    //System.out.println("DB Passsword "+store.getStorePassword()+"storePassword"+storePassword+"store.isApproved()"+store.isApproved());
                    if(!store.isApproved()){
                        Toast.makeText(getApplicationContext(), "Approval for store Registration is still pending with the admin", Toast.LENGTH_SHORT).show();
                    }else if(store.getSecurityQuestion().equals(securityquestion) && store.getSecurityAnswer().equals(securityanswer)){
                        session.createUserLoginSession(securityanswer,String.valueOf(store.getStoreId()));
                        Intent intent=new Intent(SecurityQuestion.this,NewPassword.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Security answer incorrect", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityanswertxt.setText("");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(SecurityQuestion.this, FirstScreen.class);
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
        getMenuInflater().inflate(R.menu.menu_security_question, menu);
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
