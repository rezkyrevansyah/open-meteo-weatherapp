package com.example.android_open_mateo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class WheaterVollyActivity extends AppCompatActivity {

    private TextView tvKota, tvTemperature, tvWeathercode, tvWindspeed, tvLongitude, tvLatitude, tvLibrary, tvTanggalSekarang;
    private TextView tvTanggal1, tvTanggal2, tvTanggal3, tvTanggal4, tvTanggal5, tvTanggal6;
    private TextView tvWeathercode1, tvWeathercode2, tvWeathercode3, tvWeathercode4, tvWeathercode5, tvWeathercode6;
    private ImageView ramalan1, ramalan2, ramalan3, ramalan4, ramalan5, ramalan6;
    private ImageView iconCuaca;
    private Button btn_back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informasi_cuaca_volley_retrofit);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WheaterVollyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tvKota = findViewById(R.id.tvKota);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvWeathercode = findViewById(R.id.tvWeathercode);
        tvWindspeed = findViewById(R.id.tvWindspeed);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        iconCuaca = findViewById(R.id.iconCuaca);
        tvLibrary = findViewById(R.id.tvLibrary);
        tvTanggalSekarang = findViewById(R.id.tvTanggalSekarang);
//        Ramalan Tanggal
        tvTanggal1 = findViewById(R.id.tvTanggal1);
        tvTanggal2 = findViewById(R.id.tvTanggal2);
        tvTanggal3 = findViewById(R.id.tvTanggal3);
        tvTanggal4 = findViewById(R.id.tvTanggal4);
        tvTanggal5 = findViewById(R.id.tvTanggal5);
        tvTanggal6 = findViewById(R.id.tvTanggal6);
//        Ramalan Image
        ramalan1 = findViewById(R.id.ramalan1);
        ramalan2 = findViewById(R.id.ramalan2);
        ramalan3 = findViewById(R.id.ramalan3);
        ramalan4 = findViewById(R.id.ramalan4);
        ramalan5 = findViewById(R.id.ramalan5);
        ramalan6 = findViewById(R.id.ramalan6);
//        Ramalan Weather
        tvWeathercode1 = findViewById(R.id.tvWeathercode1);
        tvWeathercode2 = findViewById(R.id.tvWeathercode2);
        tvWeathercode3 = findViewById(R.id.tvWeathercode3);
        tvWeathercode4 = findViewById(R.id.tvWeathercode4);
        tvWeathercode5 = findViewById(R.id.tvWeathercode5);
        tvWeathercode6 = findViewById(R.id.tvWeathercode6);






        fetchWeatherData();
    }


    private void fetchWeatherData() {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=-7.98&longitude=112.63&daily=weathercode&current_weather=true&timezone=auto";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            Ambil data cuaca
                            String derajat = "\u00B0";
                            String koordinat_latitude = response.getString("latitude");
                            String koordinat_longitude = response.getString("longitude");
                            JSONObject current_weather = response.getJSONObject("current_weather");
                            String temperature = current_weather.getString("temperature");
                            String windspeed = current_weather.getString("windspeed");
                            String weathercode = current_weather.getString("weathercode");
                            String tanggal_sekarang = current_weather.getString("time");

                            tvTemperature.setText(temperature + derajat + "C");
                            tvWindspeed.setText(windspeed + " knot");
                            tvLatitude.setText(koordinat_latitude);
                            tvLongitude.setText(koordinat_longitude);
                            tvLibrary.setText("Library by Volley");
                            tvTanggalSekarang.setText(tanggal_sekarang.substring(0,10));

                           handleIcon(weathercode, 0);
//                            Mengammbil data dari object daily
                            JSONObject daily = response.getJSONObject("daily");
                            JSONArray tanggal = daily.getJSONArray("time");
                            JSONArray kode_weather = daily.getJSONArray("weathercode");

                            for (int i = 1; i <= kode_weather.length(); i++) {
                                String code = kode_weather.getString(i);
                                handleIcon(code, i);
                                String time = tanggal.getString(i);
                                // Process the retrieved time as needed
                                switch (i){
                                    case 1:
                                        tvTanggal1.setText(time);
                                    case 2:
                                        tvTanggal2.setText(time);
                                    case 3:
                                        tvTanggal3.setText(time);
                                    case 4:
                                        tvTanggal4.setText(time);
                                    case 5:
                                        tvTanggal5.setText(time);
                                    case 6:
                                        tvTanggal6.setText(time);
                                }

                            }
//

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Menangani kesalahan terjadi saat pengambilan data cuaca
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        Menambahkan permintaan ke antrian volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }



    private void handleIcon(String code, int i){
        List<String> hujan = Arrays.asList("51", "53", "55", "56", "57", "61", "63", "65", "66", "67");
        List<String> berawan = Arrays.asList("45","48");
        List<String> badai = Arrays.asList("80", "81" , "82", "95", "96", "99");

        if (berawan.contains(code)){
            switch (i){
                case 0:
                    tvWeathercode.setText("Berawan");
                    iconCuaca.setImageResource(R.drawable.fog_45_48);
                case 1:
                    tvWeathercode1.setText("Berawan");
                    ramalan1.setImageResource(R.drawable.fog_45_48);
                case 2:
                    tvWeathercode2.setText("Berawan");
                    ramalan2.setImageResource(R.drawable.fog_45_48);
                case 3:
                    tvWeathercode3.setText("Berawan");
                    ramalan3.setImageResource(R.drawable.fog_45_48);
                case 4:
                    tvWeathercode4.setText("Berawan");
                    ramalan4.setImageResource(R.drawable.fog_45_48);
                case 5:
                    tvWeathercode5.setText("Berawan");
                    ramalan5.setImageResource(R.drawable.fog_45_48);
                case 6:
                    tvWeathercode6.setText("Berawan");
                    ramalan6.setImageResource(R.drawable.fog_45_48);
            }
        } else  if (code.equals("2") || code.equals("3") ){
            switch (i){
                case 0:
                    tvWeathercode.setText("Cerah");
                    iconCuaca.setImageResource(R.drawable.partly_cloud_2);
                case 1:
                    tvWeathercode1.setText("Cerah");
                    ramalan1.setImageResource(R.drawable.partly_cloud_2);
                case 2:
                    tvWeathercode2.setText("Cerah ");
                    ramalan2.setImageResource(R.drawable.partly_cloud_2);
                case 3:
                    tvWeathercode3.setText("Cerah ");
                    ramalan3.setImageResource(R.drawable.partly_cloud_2);
                case 4:
                    tvWeathercode4.setText("Cerah ");
                    ramalan4.setImageResource(R.drawable.partly_cloud_2);
                case 5:
                    tvWeathercode5.setText("Cerah ");
                    ramalan5.setImageResource(R.drawable.partly_cloud_2);
                case 6:
                    tvWeathercode6.setText("Cerah ");
                    ramalan6.setImageResource(R.drawable.partly_cloud_2);
            }
        } else  if (code.equals("1") || code.equals("0") ){
            switch (i){
                case 0:
                    tvWeathercode.setText("Cerah bgt");
                    iconCuaca.setImageResource(R.drawable.mainly_clear_1);
                case 1:
                    tvWeathercode1.setText("Cerah bgt");
                    ramalan1.setImageResource(R.drawable.mainly_clear_1);
                case 2:
                    tvWeathercode2.setText("Cerah bgt ");
                    ramalan2.setImageResource(R.drawable.mainly_clear_1);
                case 3:
                    tvWeathercode3.setText("Cerah bgt ");
                    ramalan3.setImageResource(R.drawable.mainly_clear_1);
                case 4:
                    tvWeathercode4.setText("Cerah bgt ");
                    ramalan4.setImageResource(R.drawable.mainly_clear_1);
                case 5:
                    tvWeathercode5.setText("Cerah bgt ");
                    ramalan5.setImageResource(R.drawable.mainly_clear_1);
                case 6:
                    tvWeathercode6.setText("Cerah bgt ");
                    ramalan6.setImageResource(R.drawable.mainly_clear_1);
            }
        }else  if (hujan.contains(code)){
            switch (i){
                case 0:
                    tvWeathercode.setText("Hujan");
                    iconCuaca.setImageResource(R.drawable.rain_51_53_55_56_57_61_63_65_66_67);
                case 1:
                    tvWeathercode1.setText("Hujan");
                    ramalan1.setImageResource(R.drawable.rain_51_53_55_56_57_61_63_65_66_67);
                case 2:
                    tvWeathercode2.setText("Hujan");
                    ramalan2.setImageResource(R.drawable.rain_51_53_55_56_57_61_63_65_66_67);
                case 3:
                    tvWeathercode3.setText("Hujan");
                    ramalan3.setImageResource(R.drawable.rain_51_53_55_56_57_61_63_65_66_67);
                case 4:
                    tvWeathercode4.setText("Hujan");
                    ramalan4.setImageResource(R.drawable.rain_51_53_55_56_57_61_63_65_66_67);
                case 5:
                    tvWeathercode5.setText("Hujan");
                    ramalan5.setImageResource(R.drawable.rain_51_53_55_56_57_61_63_65_66_67);
                case 6:
                    tvWeathercode6.setText("Hujan");
                    ramalan6.setImageResource(R.drawable.rain_51_53_55_56_57_61_63_65_66_67);
            }
        }else  if (badai.contains(code)){
            switch (i){
                case 0:
                    tvWeathercode.setText("Badai");
                    iconCuaca.setImageResource(R.drawable.thunder_80_81_82_95_96_99);
                case 1:
                    tvWeathercode1.setText("Badai");
                    ramalan1.setImageResource(R.drawable.thunder_80_81_82_95_96_99);
                case 2:
                    tvWeathercode2.setText("Badai");
                    ramalan2.setImageResource(R.drawable.thunder_80_81_82_95_96_99);
                case 3:
                    tvWeathercode3.setText("Badai");
                    ramalan3.setImageResource(R.drawable.thunder_80_81_82_95_96_99);
                case 4:
                    tvWeathercode4.setText("Badai");
                    ramalan4.setImageResource(R.drawable.thunder_80_81_82_95_96_99);
                case 5:
                    tvWeathercode5.setText("Badai");
                    ramalan5.setImageResource(R.drawable.thunder_80_81_82_95_96_99);
                case 6:
                    tvWeathercode6.setText("Badai");
                    ramalan6.setImageResource(R.drawable.thunder_80_81_82_95_96_99);
            }
        } else {
            switch (i){
                case 0:
                    tvWeathercode.setText("Salju");
                    iconCuaca.setImageResource(R.drawable.snow_71_73_75_77_85_86);
                case 1:
                    tvWeathercode1.setText("Salju");
                    ramalan1.setImageResource(R.drawable.snow_71_73_75_77_85_86);
                case 2:
                    tvWeathercode2.setText("Salju");
                    ramalan2.setImageResource(R.drawable.snow_71_73_75_77_85_86);
                case 3:
                    tvWeathercode3.setText("Salju");
                    ramalan3.setImageResource(R.drawable.snow_71_73_75_77_85_86);
                case 4:
                    tvWeathercode4.setText("Salju");
                    ramalan4.setImageResource(R.drawable.snow_71_73_75_77_85_86);
                case 5:
                    tvWeathercode5.setText("Salju");
                    ramalan5.setImageResource(R.drawable.snow_71_73_75_77_85_86);
                case 6:
                    tvWeathercode6.setText("Salju");
                    ramalan6.setImageResource(R.drawable.snow_71_73_75_77_85_86);
            }
        }
    }







}
