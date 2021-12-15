package com.bmi.bmi;

import com.bmi.bmi.Prevalent.UserPrevalent;

public class CalculateBMI {
    public static String bmiStatus;

    public static String CalcBMI(String weight, String length) {

        double w = Double.parseDouble(weight);
        double l = Double.parseDouble(length);
        double bmi = (w/(l*l))* UserPrevalent.agePercent;

        if (bmi<18.5)
            bmiStatus = "Under weight";
        else if (18.5<= bmi && bmi<25)
            bmiStatus = "healthy weight";
        else if (25 <= bmi && bmi<30)
            bmiStatus = "Overweight";
        else if (bmi>=30)
            bmiStatus = "Obesity";

        return bmiStatus;
    }

 }
