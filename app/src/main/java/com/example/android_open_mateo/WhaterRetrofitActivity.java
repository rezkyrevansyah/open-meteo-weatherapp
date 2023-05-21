package com.example.android_open_mateo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class WhaterRetrofitActivity extends AppCompatActivity {
    private Button btn_back;
    private TextView tvKota, tvTemperature, tvWeathercode, tvWindspeed, tvLongitude, tvLatitude, tvLibrary, tvTanggalSekarang;
    private TextView tvTanggal1, tvTanggal2, tvTanggal3, tvTanggal4, tvTanggal5, tvTanggal6;
    private TextView tvWeathercode1, tvWeathercode2, tvWeathercode3, tvWeathercode4, tvWeathercode5, tvWeathercode6;
    private ImageView ramalan1, ramalan2, ramalan3, ramalan4, ramalan5, ramalan6;
    private ImageView iconCuaca;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informasi_cuaca_volley_retrofit);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WhaterRetrofitActivity.this, MainActivity.class);
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

        getWeather();




    }
    public class WeatherData {

        @SerializedName("current_weather")
        private CurrentWeather currentweather;
        @SerializedName("daily")
        private Daily daily;
        private String latitude, windspeed;
        private String longitude;
        private int[] weathercode;
        // Tambahkan atribut lainnya sesuai kebutuhan



        public String getLatitude() {
            return latitude;
        }

        public String getLongitude () {
            return longitude;
        }

        public String getWindspeed(){
            return windspeed;
        }

        public CurrentWeather getCurrent_weather() {
            return currentweather;
        }

        public Daily getDaily() {
            return daily;
        }

        // Tambahkan getter/setter untuk atribut lainnya
    }
    public class Daily {
        @SerializedName("time")
        private List<String> time;

        @SerializedName("weathercode")
        private List<String> wheatercode;
        public List<String> getTime() {
            return time;
        }

        public List<String> getWheatercode() {
            return wheatercode;
        }
    }
    public class CurrentWeather {
        @SerializedName("windspeed")
        private String windspeed;

        @SerializedName("temperature")
        private String temperature;

        @SerializedName("weathercode")
        private String weathercode;

        public String getWindspeed() {
            return windspeed;
        }

        public String getTemperature() {
            return temperature;
        }

        public String getWeathercode() {
            return weathercode;
        }
    }
    public interface WeatherService {
        @GET("forecast") // Sesuaikan dengan endpoint API Open-Meteo
        Call<WeatherData> getWeatherData(@Query("latitude") double latitude, @Query("longitude") double longitude,
                                         @Query("daily") String daily, @Query("current_weather") boolean currentWeather,
                                         @Query("timezone") String timezone);
    }
//    Buat method untuk menampung data
public void getWeather(){
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/") // Ganti dengan base URL API Open-Meteo
            .addConverterFactory(GsonConverterFactory.create())
            .build();



    WeatherService service = retrofit.create(WeatherService.class);
    double latitude = -7.98; // Ganti dengan nilai latitude yang diinginkan
    double longitude = 112.63; // Ganti dengan nilai longitude yang diinginkan
    String daily = "weathercode"; // Ganti dengan parameter daily yang diinginkan
    boolean currentWeather = true; // Ganti dengan nilai current_weather yang diinginkan
    String timezone = "auto"; // Ganti dengan timezone yang diinginkan
    Call<WeatherData> call = service.getWeatherData(latitude, longitude, daily, currentWeather, timezone);

    call.enqueue(new Callback<WeatherData>() {
        @Override
        public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
            if (response.isSuccessful()) {
                WeatherData weatherData = response.body();
                // Menyimpan data cuaca ke dalam TextView
                tvLatitude.setText(response.body().getLatitude());
                tvLongitude.setText(response.body().getLongitude());
                tvWindspeed.setText(response.body().getCurrent_weather().getWindspeed() + " knot");
                tvTemperature.setText(response.body().getCurrent_weather().getTemperature() + "\u00B0" + "C");
                tvTanggalSekarang.setText(response.body().getDaily().getTime().get(0));
                handleIcon(response.body().getCurrent_weather().getWeathercode(),0);

                for (int i = 1; i <= 6; i++) {
                    String code = response.body().getDaily().getWheatercode().get(i);
                    handleIcon(code, i);
                    String time = response.body().getDaily().getTime().get(i);
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
                tvLibrary.setText("Library by Retrofit");

            } else {
                // Handle response error

                tvLongitude.setText("String.valueOf(latitude)");
            }
        }

        @Override
        public void onFailure(Call<WeatherData> call, Throwable t) {
            // Handle network failure

        }
    });
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






