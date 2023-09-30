package com.example.jsonlab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.jsonlab.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<String> fetchlist;
    ArrayAdapter<String> listAdapter;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeUserlist();
        binding.fetchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new fetchData(MainActivity.this).start();

            }
        });

    }

    private void initializeUserlist() {

        fetchlist = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fetchlist);
        binding.fetchlist.setAdapter(listAdapter);


    }


    static class fetchData extends Thread {
        private final MainActivity mainActivity;
        String data = "";

        public fetchData(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }


        @Override
        public void run() {

            mainActivity.mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    mainActivity.progressDialog = new ProgressDialog(mainActivity);
                    mainActivity.progressDialog.setMessage("Fetching Data");
                    mainActivity.progressDialog.setCancelable(false);
                    mainActivity.progressDialog.show();

                }
            });
        try {
                URL url = new URL("https://api.npoint.io/1d1c629a26ce481028ca");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {

                    data = data + line;
                }

                if (!data.isEmpty()) {

                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray users = jsonObject.getJSONArray("Users");
                    mainActivity.fetchlist.clear();
                    for (int i = 0; i < users.length(); i++) {

                        JSONObject names = users.getJSONObject(i);
                        String name = names.getString("name");
                        mainActivity.fetchlist.add(name);


                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mainActivity.mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mainActivity.progressDialog.isShowing())
                        mainActivity.progressDialog.dismiss();
                    mainActivity.listAdapter.notifyDataSetChanged();

                }
            });
        }
    }
}