package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserRegistration extends AppCompatActivity {
    private EditText etunimi;
    private EditText sukunimi;
    private EditText sahkoposti;
    private EditText ika;
    private EditText tunnus;
    private EditText salasana;
    private TextView testiTextView;
    private Button luoTunnus;
    private DBHandler db;
    private String roskis = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        etunimi = (EditText) findViewById(R.id.etunimiEditText);
        sukunimi = (EditText) findViewById(R.id.sukunimiEditText);
        sahkoposti = (EditText) findViewById(R.id.spostiEditText);
        ika = (EditText) findViewById(R.id.ikaEditText);
        tunnus = (EditText) findViewById(R.id.tunnusEditText);
        salasana = (EditText) findViewById(R.id.salasanaEditText);
        testiTextView = (TextView) findViewById(R.id.testiTextView);
        luoTunnus = (Button) findViewById(R.id.luoTunnusBtn);
        db = new DBHandler(this);

        luoTunnus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // tyhjien kenttien check
                if (Helpers.isEmpty(etunimi) || Helpers.isEmpty(sukunimi) || Helpers.isEmpty(sahkoposti)
                        || Helpers.isEmpty(ika) || Helpers.isEmpty(salasana) || Helpers.isEmpty(tunnus)) {
                    testiTextView.setText("TYJHJIA KENTTIA");
                } else {
                    if (db.getKayttajatili(tunnus.getText().toString()).equals(null)) {

                        // generoidaan salt, jonka avulla hashattu salasana
                        String salt = PasswordEncryption.generateSalt(512).get();
                        String hashSalasana = PasswordEncryption.hashPassword(salasana.getText().toString(), salt).get();

                        // lisataan tietokantaan
                        db.insertKayttajatili(etunimi.getText().toString(), sukunimi.getText().toString(),
                                sahkoposti.getText().toString(), Integer.parseInt(ika.getText().toString()),
                                tunnus.getText().toString(), hashSalasana, salt);

                        // tyhjennetaan tietoturvan takia
                        salt = roskis;
                        hashSalasana = roskis;

                        db.close();
                    }
                    else{
                        testiTextView.setText("KAYTTAJA JO TIETOKANNASSA");
                    }
                }
            }
        });

    }
}
