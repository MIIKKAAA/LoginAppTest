package com.example.loginapp;

import android.widget.EditText;

public class Helpers {

    // apumetodi syotekenttien tyhjyyden tarkistamiseen
    public static boolean isEmpty(EditText kentta) {
        if (kentta.getText().toString().trim().length() > 0){
            return false;
        }
        else
        {
            return true;
        }
    }
}
