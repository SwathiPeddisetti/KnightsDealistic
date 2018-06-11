package ucf.knightsdealistic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;

/**d
 * Created by Swathi on 4/8/2015.
 */
public class TabStudentDealsOfTheDay extends Activity {
    ListView listDealsOfTheDay;
    LinearLayout layoutDealsOfTheDay;
    DealDataSource dealdatasource ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        View b = findViewById(R.id.logo);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.gridLayoutHeader);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.bigdeallayout);
        b.setVisibility(View.GONE);
        dealdatasource = new DealDataSource(getApplicationContext());
        listDealsOfTheDay = (ListView) this.findViewById(R.id.listDealsOfTheDay);
        layoutDealsOfTheDay = (LinearLayout) this.findViewById(R.id.tabdealsoftheday);
        List<Deal> deals=null;
        try {
        //    deals = createDealList();
          deals = dealdatasource.getDealsOfTheDay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    ArrayList<Map<String, String>> dealsMap = new ArrayList<Map<String, String>>();
        LinkedHashMap<String, String> map =null;
        for (Deal deal:deals) {
            map = new LinkedHashMap<String, String>();
            map.put("dealName",deal.getDealName() );
            map.put("dealDesc",deal.getDealDescription() );
            map.put("dealEndDate",DateUtility.dateToString(deal.getDealActiveTo()));
            dealsMap.add(map);
        }
      //  Collections.sort(deals, new SortByDueDate());
        if (deals.size() > 0) {
            int to[] = { R.id.dealName, R.id.dealDesc,
                    R.id.dealEndDate };
            String from[] = { "dealName", "dealDesc", "dealEndDate" };
            SimpleAdapter ca = new SimpleAdapter(this, dealsMap,
                    R.layout.studentdealoftheday, from, to);
            listDealsOfTheDay.setAdapter(ca);
            layoutDealsOfTheDay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static List<Deal> createDealList() {
        Admin admin = new Admin(1, "a3", "p3", "a3@gmail.com");
        Store store = new Store(1L, admin, "storeName", "storeDesc", "storePassword", "location", "emailId", 9L, false, "securityQuestion", "securityAnswer", null, null, null);
        List<Deal> deals = new ArrayList<Deal>();
        String date = "4/1/2015";
        String fromdate = "4/3/2015";
        String todate = "10/3/2015";
        Deal d = new Deal(1L, "pizza", "25%", DateUtility.stringToDate(date), DateUtility.stringToDate(fromdate), DateUtility.stringToDate(todate), store);
        deals.add(d);
        d = new Deal(2L, "coke", "50%", DateUtility.stringToDate(date), DateUtility.stringToDate(fromdate), DateUtility.stringToDate(todate), store);
        deals.add(d);
        d = new Deal(3L, "fries", "1+1", DateUtility.stringToDate(date), DateUtility.stringToDate(fromdate), DateUtility.stringToDate(todate), store);
        deals.add(d);
        d = new Deal(4L, "drink", "$1", DateUtility.stringToDate(date), DateUtility.stringToDate(fromdate), DateUtility.stringToDate(todate), store);
        deals.add(d);
        d = new Deal(5L, "chips", "1+1", DateUtility.stringToDate(date), DateUtility.stringToDate(fromdate), DateUtility.stringToDate(todate), store);
        deals.add(d);
        d = new Deal(6L, "candy", "$1", DateUtility.stringToDate(date), DateUtility.stringToDate(fromdate), DateUtility.stringToDate(todate), store);
        deals.add(d);
        return deals ;

    }

}
