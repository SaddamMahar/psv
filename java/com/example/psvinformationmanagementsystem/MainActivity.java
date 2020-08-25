package com.example.psvinformationmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btn_driver_entry_form;
    Button btn_vehicle_entry_form;
    Button btn_vehicle_inspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_driver_entry_form = (Button) findViewById(R.id.btn_driver_entry_form);
        btn_vehicle_entry_form = (Button) findViewById(R.id.btn_vehicle_entry_form);
        btn_vehicle_inspection = (Button) findViewById(R.id.btn_vehicle_inspection);
        btn_driver_entry_form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OkHttpHandler okHttpHandler= new OkHttpHandler();
                okHttpHandler.execute("http://10.0.2.2:8000/v1/driver");
                openDriverEntryFormActivity();
            }
        });

        btn_vehicle_entry_form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openVehicleEntryFormActivity();
            }
        });

        btn_vehicle_inspection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openVehicleInspectionActivity();
            }
        });
    }

    public void openDriverEntryFormActivity() {
        Intent intentDriverEntryForm = new Intent(this, DriverEntryFormActivity.class);
        startActivity(intentDriverEntryForm);
    }

    public void openVehicleEntryFormActivity() {
        Intent intentVehicleEntryForm = new Intent(this, VehicleEntryFormActivity.class);
        startActivity(intentVehicleEntryForm);
    }

    public void openVehicleInspectionActivity() {
        Intent intentVehicleInspection = new Intent(this, VehicleInspectionActivity.class);
        startActivity(intentVehicleInspection);
    }

    public class OkHttpHandler extends AsyncTask {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected Object doInBackground(Object[] objects) {
            Request.Builder builder = new Request.Builder();
            builder.url(objects[0].toString());
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                String r = response.body().string();
                System.out.println(r);
                return r;
            }catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
}