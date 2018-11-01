package com.example.chomy.newdatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDBOpenHelper dbHelper;
    SQLiteDatabase mdb;
    Button buttonInsert, buttonRead, buttonUpdate, buttonDelete;
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDBOpenHelper(this, "awe.db", null, 1);
        mdb = dbHelper.getWritableDatabase();

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonRead = findViewById(R.id.buttonRead);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete =findViewById(R.id.buttonDelete);
        textViewResult = findViewById(R.id.textViewResult);
        buttonInsert.setOnClickListener(this);
        buttonRead.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        EditText countryEditText = findViewById(R.id.editTextCountry);
        EditText cityEditText = findViewById(R.id.editTextCity);
        String country = countryEditText.getText().toString();
        String city = cityEditText.getText().toString();
        switch (v.getId()){
            case R.id.buttonInsert:
                mdb.execSQL("INSERT INTO awe_country VALUES(null, '"+country+"','"+city+"')");
                break;
            case R.id.buttonRead:
                String query = "SELECT*FROM awe_country ORDER BY _id DESC";
                Cursor cursor = mdb.rawQuery(query,null);
                String str ="";
                while (cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    country = cursor.getString(cursor.getColumnIndex("country"));
                    city = cursor.getString(cursor.getColumnIndex("capital"));
                    str+=(id+":"+country+"-"+city+"\n");
                }
                if(str.length()>0){
                    textViewResult.setText(str);
                }else {
                    Toast.makeText(getApplicationContext(),"Warning Empty DB",Toast.LENGTH_LONG).show();
                    textViewResult.setText("");
                }
                break;
            case  R.id.buttonUpdate:
                mdb.execSQL("UPDATE awe_country SET capital='"+city+"'WHERE country='"+country+"'");
                break;
            case R.id.buttonDelete:
                mdb.execSQL("DELETE FROM awe_country WHERE country = '"+country+"'");

        }


    }
}
