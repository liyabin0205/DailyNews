package com.app.dailynews;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.ImageView;

import com.app.dailynews.model.Weather;
import com.app.dailynews.utils.HTTPUtils;
import com.google.gson.Gson;



public class HomeActivity extends AppCompatActivity {

    //public static final String WEATHER_URL = "http://api.map.baidu.com/telematics/v3/weather?location=%E4%B8%8A%E6%B5%B7&output=json&ak=mXBIDrvTOwwmYaTtN03Lo0j2";
    public static final String WEATHER_URL = "http://api.map.baidu.com/telematics/v3/weather?location=%E4%B8%8A%E6%B5%B7&output=json&ak=FK9mkfdQsloEngodbFl4FeY3";
    private TextView id_date;
    private TextView id_currentCity;
    private TextView id_pm25;
    private TextView id_weather;
    private TextView id_temperature;
    private TextView id_wind;
    private TextView id_titp;
    private TextView id_des;
    private ImageView iv_weather;
    private Weather weather;

    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    setUI();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        init();

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                String json = HTTPUtils.getStringFromNetwork(WEATHER_URL);
             //   parseJSON(json);
                Gson gson = new Gson();
                weather = gson.fromJson(json,Weather.class);
               handler.sendEmptyMessage(1);
            }
        });
        thread.start();
    }

    private void init(){
        id_date = (TextView) findViewById(R.id.id_date);
        id_currentCity = (TextView) findViewById(R.id.id_currentCity);
        id_pm25 = (TextView) findViewById(R.id.id_pm25);
        id_weather = (TextView) findViewById(R.id.id_weather);
        id_temperature = (TextView) findViewById(R.id.id_temperature);
        id_wind = (TextView) findViewById(R.id.id_wind);
        id_titp = (TextView) findViewById(R.id.id_titp);
        id_des = (TextView) findViewById(R.id.id_des);
        iv_weather = (ImageView) findViewById(R.id.iv_weather);
    }

    private void setUI(){

        id_date.setText(weather.getDate());
        id_currentCity.setText(weather.getResults().get(0).getCurrentCity());
        id_pm25.setText(weather.getResults().get(0).getPm25());
        id_weather.setText(weather.getResults().get(0).getWeather_data().get(0).getWeather());
        id_temperature.setText(weather.getResults().get(0).getWeather_data().get(0).getTemperature());
        id_wind.setText(weather.getResults().get(0).getWeather_data().get(0).getWind());
        id_des.setText(weather.getResults().get(0).getIndex().get(0).getDes());
        if(weather.getResults().get(0).getWeather_data().get(0).getWeather().equals("阵雨")){
            iv_weather.setImageResource(R.mipmap.zhenyu);
        }else if(weather.getResults().get(0).getWeather_data().get(0).getWeather().equals("阵雨转多云")){
            iv_weather.setImageResource(R.mipmap.duoyunzhuanyu1);
        }else if(weather.getResults().get(0).getWeather_data().get(0).getWeather().equals("多云")){
            iv_weather.setImageResource(R.mipmap.duoyun);
        }else if(weather.getResults().get(0).getWeather_data().get(0).getWeather().equals("晴")){
            iv_weather.setImageResource(R.mipmap.qing);
        }else if(weather.getResults().get(0).getWeather_data().get(0).getWeather().equals("阴天")){
            iv_weather.setImageResource(R.mipmap.yintian);
        }else if(weather.getResults().get(0).getWeather_data().get(0).getWeather().equals("晴转多云")){
            iv_weather.setImageResource(R.mipmap.duoyun);
        }
    }

    /*
    private void parseJSON(String json) {

        try {
            JSONObject weatherJsonObejct = new JSONObject(json);
            Weather weather = new Weather();
            weather.setError(weatherJsonObejct.getInt("error"));
            date = weatherJsonObejct.getString("date");
            weather.setDate(date);
            weather.setStatus(weatherJsonObejct.getString("status"));

            JSONArray resultArray = weatherJsonObejct.getJSONArray("results");
            List<Result> resultList = new ArrayList<>();
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                Result result = weather.new Result();
                currentCity = resultObject.getString("currentCity");
                result.setCurrentCity(currentCity);
                pm25 = resultObject.getString("pm25");
                result.setPm25(pm25);

                JSONArray indexArray = resultObject.getJSONArray("index");
                List<Index> indexList = new ArrayList<>();
                for (int j = 0; j < indexArray.length(); j++) {
                    JSONObject indexObject = indexArray.getJSONObject(j);
                    Index index = result.new Index();
                    index.setTitle(indexObject.getString("title"));
                    index.setZs(indexObject.getString("zs"));
                    index.setTipt(indexObject.getString("tipt"));
                    des = indexObject.getString("des");
                    index.setDes(des);
                    indexList.add(index);
                }
                result.setIndex(indexList);

                JSONArray weather_dataArray = resultObject.getJSONArray("weather_data");
                List<Weather_Data> weather_dataList = new ArrayList<>();
                for (int j = 0; j < weather_dataArray.length(); j++) {
                    JSONObject weather_dataObject = weather_dataArray.getJSONObject(j);
                    Weather_Data weather_data = result.new Weather_Data();
                    weather_data.setDate(weather_dataObject.getString("date"));
                    weather_data.setDayPictureUrl(weather_dataObject.getString("dayPictureUrl"));
                    weather_data.setNightPictureUrl(weather_dataObject.getString("nightPictureUrl"));
                    content_weather = weather_dataObject.getString("weather");
                    weather_data.setWeather(content_weather);
                    wind = weather_dataObject.getString("wind");
                    weather_data.setWind(wind);
                    temperature = weather_dataObject.getString("temperature");
                    weather_data.setTemperature(temperature);
                    weather_dataList.add(weather_data);
                }
                result.setWeather_data(weather_dataList);

                resultList.add(result);
            }
            weather.setResults(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
