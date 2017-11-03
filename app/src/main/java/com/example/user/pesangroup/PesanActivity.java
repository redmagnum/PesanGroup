package com.example.user.pesangroup;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PesanActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor ed;
    EditText inputNama, inputPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        inputNama = (EditText)findViewById(R.id.inpNama);
        inputPesan = (EditText)findViewById(R.id.inpPesan);
        preferences = getSharedPreferences(MainActivity.mainPref,0);
        ed = preferences.edit();
    }

    public void sendPesan(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nama",inputNama.getText().toString());
            jsonObject.put("pesan",inputPesan.getText().toString());
            jsonObject.put("waktu",new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            jsonObject.put("foto",R.drawable.a);
        }catch (JSONException e){
            e.printStackTrace();
        }

        if (preferences.contains("message")){
            String dataPesan = preferences.getString("message","");
            try {
                JSONArray arrayJson = new JSONArray(dataPesan);
                arrayJson.put(jsonObject);
                ed.putString("message",arrayJson.toString());
                ed.apply();
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);
            ed.putString("message", jsonArray.toString());
            ed.apply();
        }
        finish();
    }
}
