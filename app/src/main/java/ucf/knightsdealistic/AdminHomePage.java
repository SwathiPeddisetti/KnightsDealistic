package ucf.knightsdealistic;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;


public class AdminHomePage extends TabActivity
{
    int selectedTab=0;
    UserSessionManager session;
    TextView  logouttxt,txthelloAdmin;
    String adminName;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        session = new UserSessionManager(getApplicationContext());
        if(!session.isLogggedIn())
            finish();
        HashMap<String, String> user = session.getUserDetails();
        adminName = user.get(UserSessionManager.KEY_NAME);
        logouttxt = (TextView) findViewById(R.id.txtlogout);
        logouttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomePage.this, FirstScreen.class);
                session.logoutUser();
                startActivity(intent);
                finish();
            }
        });

        try {
            selectedTab = Integer.parseInt(getIntent().getStringExtra("selectedTab"));
        }catch(Exception e){
            e.printStackTrace();
        }
        txthelloAdmin = (TextView) findViewById(R.id.txthelloAdmin);
        txthelloAdmin.setText("Hello, "+ adminName);
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second tab");
        tab1.setIndicator("Pending Store Requests");
        Intent intent=new Intent(this,TabPendingStoreReq.class);
        tab1.setContent(intent);

        tab2.setIndicator("Pending Big Deal Requests");
        tab2.setContent(new Intent(this,TabPendingBigDealReq.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(selectedTab);

    }


}
