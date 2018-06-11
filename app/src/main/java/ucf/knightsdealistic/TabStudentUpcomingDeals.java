package ucf.knightsdealistic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import ucf.knightsdealistic.database.datasource.DealDataSource;

/**
 * Created by Swathi on 4/8/2015.
 */
public class TabStudentUpcomingDeals extends Activity {
    ListView listDealsOfTheDay;
    LinearLayout layoutDealsOfTheDay;
    DealDataSource dealdatasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
    }
}
