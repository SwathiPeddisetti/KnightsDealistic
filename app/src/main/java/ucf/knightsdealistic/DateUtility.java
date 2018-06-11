package ucf.knightsdealistic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Swathi on 4/4/2015.
 */
public class DateUtility {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

    public static Date stringToDate(String stringDate){
        Date date=null;
        try {
            date= simpleDateFormat.parse(stringDate);
        }catch(Exception e){
            System.out.print(e);
        }
        return date;
    }

    public static String dateToString(Date date) {
        String stringDate=null;
        try {
            stringDate= simpleDateFormat.format(date);
        }catch(Exception e){
            System.out.print(e);
        }
        return stringDate;
    }

    public static String getNow(){
        try {
            return simpleDateFormat.format(Calendar.getInstance().getTime());
        }catch(Exception e){
            System.out.print(e);
            return null;
        }
    }

    public static String addDaysToDate(String date,int noofDays){
        try {
            Calendar c=Calendar.getInstance();
            c.setTime(simpleDateFormat.parse(date));
            c.add(Calendar.DATE, noofDays);
            return simpleDateFormat.format(c.getTime());
        }catch(Exception e){
            System.out.print(e);
            return null;
        }
    }

    public static boolean canBigDealBeDeleted(Date toBePosted){
        try {
            if(toBePosted.after(Calendar.getInstance().getTime())){
              if(toBePosted.compareTo(stringToDate(addDaysToDate(getNow(),3)))<= 0){
                  return false;
              }
            }
        }catch(Exception e){
            System.out.print(e);
            return false;
        }
        return true;
    }



    public static String add3DaysToCurrentDate(){
        try {
            Calendar c=Calendar.getInstance();
            c.add(Calendar.DATE, 3);
            return simpleDateFormat.format(c.getTime());
        }catch(Exception e){
            System.out.print(e);
            return null;
        }
    }



    public static boolean Inrange(String start, String end){
        try {
            String today = simpleDateFormat.format(Calendar.getInstance().getTime());

            if((Calendar.getInstance().getTime().after(stringToDate(start)) &&
                    Calendar.getInstance().getTime().before(stringToDate(end))  ) ||
                    Calendar.getInstance().getTime().compareTo(stringToDate(start)) ==0    ) {
                System.out.println("Inrange  "+ start+"  " + today + "  " + end);
                return true;
            }
            System.out.println("NOPE  "+ start+"  " + today + "  " + end);
            return false;

        }catch(Exception e){
            System.out.print(e);
            return false;
        }
    }

    public static boolean after(String end){
        try {
            String today = simpleDateFormat.format(Calendar.getInstance().getTime());

            if(stringToDate(end).after(Calendar.getInstance().getTime())){
                System.out.println("After  " + today + "  " + end+"   "+stringToDate(end)+"  "+Calendar.getInstance().getTime()  );

                return true;
            }
            return false;
        }catch(Exception e){
            System.out.print(e);
            return false;
        }
    }



}
