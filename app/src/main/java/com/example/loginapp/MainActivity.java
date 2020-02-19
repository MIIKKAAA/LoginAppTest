package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    private EditText salasana;
    private EditText tunnus;
    private Button hyvaksy;
    private TextView testi;
    private TextView rekisteroi;
    private String hashSalasana;
    private DBHandler db;
    private String roskis = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openOrCreateDatabase("kayttajatilit.db", MODE_PRIVATE, null);

        salasana = (EditText) findViewById(R.id.passwordInput);
        tunnus = (EditText) findViewById(R.id.tunnusInput);
        hyvaksy = (Button) findViewById(R.id.loginBtn);
        testi = (TextView) findViewById(R.id.testiTextView);
        rekisteroi = (TextView) findViewById(R.id.rekisterointiTextView);
        db = new DBHandler(this);

        hyvaksy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // tyhjien kenttien check
                if (Helpers.isEmpty(tunnus) || Helpers.isEmpty(salasana)) {
                    testi.setText("TYHJIA KENTTIA");
                } else {
                    openOrCreateDatabase("kayttajatilit.db", MODE_PRIVATE, null);

                    //haetaan tunnuksen perusteella tili, sen hashattusalasana seka salt
                    Kayttajatili tili = db.getKayttajatili(tunnus.getText().toString());
                    String haettuHashSalasana = tili.getSalasana();
                    String haettuSalt = tili.getSalt();

                    // salasanan varmennus
                    if (PasswordEncryption.verifyPassword(salasana.getText().toString(), haettuHashSalasana, haettuSalt)){
                        testi.setText("Oikein");
                    }
                    else{
                        testi.setText("Väärin");
                    }

                    // tyhjennetaan tallennetut tiedot tietoturvan vuoksi
                    haettuHashSalasana = roskis;
                    haettuSalt = roskis;
                    db.close();
                }
            }
        });

        rekisteroi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserRegistration.class));
            }
        });

    }


}
