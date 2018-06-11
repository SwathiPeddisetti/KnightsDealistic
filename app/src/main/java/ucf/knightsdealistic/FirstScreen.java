package ucf.knightsdealistic;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class FirstScreen extends TabActivity {
    int selectedTab=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        try {
            selectedTab = Integer.parseInt(getIntent().getStringExtra("selectedTab"));
        }catch(Exception e){
            e.printStackTrace();
        }
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third tab");
        tab1.setIndicator("Student");
        tab1.setContent(new Intent(this,StudentLogin.class));

        tab2.setIndicator("Store");
        tab2.setContent(new Intent(this,StoreLogin.class));

        tab3.setIndicator("Admin");
        tab3.setContent(new Intent(this,AdminLogin.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.setCurrentTab(selectedTab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first_screen, menu);
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
