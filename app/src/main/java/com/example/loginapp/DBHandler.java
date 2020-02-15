package com.example.loginapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NIMI = "kayttajatilit.db";


    public DBHandler(Context context) {

        super(context, DATABASE_NIMI, null, DATABASE_VERSION);
    }

    // luodaan taulu
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Kayttajatili.LUO_TAULU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Kayttajatili.TAULUN_NIMI);

        onCreate(db);
    }

    public long insertKayttajatili(String etunimi, String sukunimi, String sposti, int ika, String tunnus, String salasana, String salt) {
        // DB, johon halutaan kirjoittaa
        SQLiteDatabase db = this.getWritableDatabase();

        // arvot
        ContentValues values = new ContentValues();
        values.put(Kayttajatili.SARAKE_ETUNIMI, etunimi);
        values.put(Kayttajatili.SARAKE_SUKUNIMI, sukunimi);
        values.put(Kayttajatili.SARAKE_IKA, ika);
        values.put(Kayttajatili.SARAKE_SAHKOPOSTI, sposti);
        values.put(Kayttajatili.SARAKE_TUNNUS, tunnus);
        values.put(Kayttajatili.SARAKE_SALASANA, salasana);
        values.put(Kayttajatili.SARAKE_SALT, salt);


        // lisaa rivi
        long id = db.insert(Kayttajatili.TAULUN_NIMI, null, values);

        db.close();

        // palautusarvona lisatyn rivi id
        return id;
    }

    public Kayttajatili getKayttajatili(String tunnus) {

        // haetaan luettava db
        SQLiteDatabase db = this.getReadableDatabase();

        // haetaan tietokannasta tunnuksen mukainen alkio
        Cursor cursor = db.query(Kayttajatili.TAULUN_NIMI,
                new String[]{Kayttajatili.SARAKE_ETUNIMI, Kayttajatili.SARAKE_SUKUNIMI, Kayttajatili.SARAKE_SAHKOPOSTI,
                        Kayttajatili.SARAKE_IKA, Kayttajatili.SARAKE_TUNNUS, Kayttajatili.SARAKE_SALASANA, Kayttajatili.SARAKE_SALT},
                Kayttajatili.SARAKE_TUNNUS+ "=?",
                new String[]{String.valueOf(tunnus)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // uusi kayttajatili objekti kursorin tiedoilla
        Kayttajatili kayttajatili = new Kayttajatili(
                cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_ETUNIMI)),
                cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_SUKUNIMI)),
                cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_SAHKOPOSTI)),
                cursor.getInt(cursor.getColumnIndex(Kayttajatili.SARAKE_IKA)),
                cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_TUNNUS)),
                cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_SALASANA)),
                cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_SALT)));

        cursor.close();

        // kayttajatili objekti palautusarvona
        return kayttajatili;
    }


    public List<Kayttajatili> getKaikkiKayttajatilit() {
        // luodaan lista, johon haetut kayttajatilit tallennetaan
        List<Kayttajatili> kayttajatilit = new ArrayList<>();

        // valitaan kaikki
        String haeKaikki= "SELECT  * FROM " + Kayttajatili.TAULUN_NIMI + " ORDER BY " +
                Kayttajatili.SARAKE_TUNNUS + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(haeKaikki, null);

        // iteroidaan ja lisataan jokainen haettu alkio listaan
        if (cursor.moveToFirst()) {
            do {
                Kayttajatili kayttajatili = new Kayttajatili();
                kayttajatili.setIka(cursor.getInt(cursor.getColumnIndex(Kayttajatili.SARAKE_IKA)));
                kayttajatili.setEtunimi(cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_ETUNIMI)));
                kayttajatili.setSukunimi(cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_SUKUNIMI)));
                kayttajatili.setSahkoposti(cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_SAHKOPOSTI)));
                kayttajatili.setTunnus(cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_TUNNUS)));
                kayttajatili.setSalasana(cursor.getString(cursor.getColumnIndex(Kayttajatili.SARAKE_SALASANA)));

                kayttajatilit.add(kayttajatili);
            } while (cursor.moveToNext());
        }

        db.close();

        // lista palautusarvona
        return kayttajatilit;
    }

}
