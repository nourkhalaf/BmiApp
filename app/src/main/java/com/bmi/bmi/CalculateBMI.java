package com.bmi.bmi;

import android.text.TextUtils;

import com.bmi.bmi.Prevalent.UserPrevalent;

public class CalculateBMI {
    public static String bmiStatus;


    public static double CalcBMI(String weight, String length) {

        double w = Double.parseDouble(weight);
        double l = Double.parseDouble(length);
        double bmi = (w / (l * l)) * UserPrevalent.agePercent;

        return bmi;
    }

    public static String CalcBMIStatus(double bmi) {
        if (bmi < 18.5)
            bmiStatus = "Under weight";
        else if (18.5 <= bmi && bmi < 25)
            bmiStatus = "Healthy weight";
        else if (25 <= bmi && bmi < 30)
            bmiStatus = "Over weight";
        else if (bmi >= 30)
            bmiStatus = "Obesity";

        return bmiStatus;
    }

    public static String calcCurrentStatus(String status1,String bmi1, String bmi2) {
         double firstBmi = Double.parseDouble(bmi1);
         double secondBmi = Double.parseDouble(bmi2);

         double difference = firstBmi-secondBmi;

         String status ="";
         if (status1.equals("Under weight")){
             if (difference<-0.3)
             {status =  "So Bad";}
             else if (difference>=-0.3 && difference<0.3)
             {status = "Little Changes";}
             else if (difference>=0.3 && difference<0.6)
             {status =  "Still Good";}
             else if (difference>=0.6)
             {status = "Go Ahead";}
         }
         else if (status1.equals("Healthy weight")){
             if (difference<-1)
             {status =  "So Bad";}
             else if (difference>=-1 && difference<-0.3)
             {status = "Be Careful";}
             else if (difference>=-0.3 && difference<0.3)
             {status =  "Still Good";}
             else if (difference>=0.3)
             {status = "Be Careful";}
         }
         else if (status1.equals("Over weight")){
             if (difference<-1)
             {status =  "Be Careful";}
             else if (difference>=-1 && difference<-0.6)
             {status = "Go Ahead";}
             else if (difference>=-0.6 && difference<-0.3)
             {status =  "Still Good";}
             else if (difference>=-0.3 && difference<0.3)
             {status = "Little Changes";}
             else if (difference>=0.3 && difference<0.6)
             {status = "Be Careful";}
             else if (difference>=0.6 )
             {status = "So Bad";}
         }
         else if (status1.equals("Obesity")){
             if (difference<-0.6)
             {status =  "Go Ahead";}
             else if (difference>=-0.6 && difference<0)
             {status = "Little Changes";}
             else if (difference>=0 && difference<0.3)
             {status =  "Be Careful";}
             else if (difference>=0.3)
             {status = "So Bad";}
         }

         return status;
    }
}
