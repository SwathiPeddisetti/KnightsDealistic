package ucf.knightsdealistic;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Swathi on 4/8/2015.
 */
public class Utility  {

    public static void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }
            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }
}
