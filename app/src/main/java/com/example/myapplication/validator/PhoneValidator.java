package com.example.myapplication.validator;

import android.util.Log;

public class PhoneValidator {

    public String phoneValidator(String  phone) {
        String phoneValidation;
        if (!phone.isEmpty()){
             phoneValidation=phone.replaceAll("\\D", "");
        }

        else{
            phoneValidation="";
        }

        return phoneValidation;

    }
}
