package com.akashbutar.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //Declaring Variables
    TextView mTextView;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Assigning the variables with buttons and editText
        mTextView = (TextView) findViewById(R.id.tv_textview);
        mEditText = findViewById(R.id.et_edit_text1);

    }
   //when button is pressed
    public void bu_button(View view){
        Log.d("weatherApp","Button is pressed");
        //Toast.makeText(this, "Fetching", Toast.LENGTH_SHORT).show();

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+ mEditText.getText().toString() +"&appid=c6a454bddc886530f4d5d61304d6f908";
       // sending this url to MyAsyncTaskNews
        Log.d("weatherApp","URL is working");
        new MainActivity.MyAsyncTaskgetNews().execute(url);
        Log.d("weatherApp","Main is working");
        //Going to the next activity
    }

    // get news from server
    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        //only this method need to be changed most of the time for API calling
        protected void onProgressUpdate(String... progress) {

            try {
                    String s2="Hello";
                    mTextView.setText(s2);
                Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();
                //All the JSON data
               // JSONObject json = new JSONObject(progress[0]);

              //  JSONArray jsonArray = json.getJSONArray("weather");

          //      JSONObject desc = (JSONObject) jsonArray.getJSONObject("description");



              //  mTextView.setText(s2);



                //display response data
             //   Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String  result2){


        }




    }

    // this method convert any stream to string
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }

}


