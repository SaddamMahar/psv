package com.example.psvinformationmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VehicleSearchActivity extends AppCompatActivity {

    private EditText text_input_veh_no, text_input_driver_no;
    private RadioGroup radioGroupLicenseType;
    private Button btn_submit_search_data;
    JsonObject objDriver, objVehicle;
    String driverResp, vehicleResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_search);

//        Object objDriver = new Object();
//        Object objVehicle = new Object();

        text_input_veh_no = findViewById(R.id.text_input_veh_no);
        text_input_driver_no = findViewById(R.id.text_input_driver_no);

        radioGroupLicenseType = findViewById(R.id.radioGroupLicenseType);

        btn_submit_search_data = findViewById(R.id.btn_submit_search_data);

        btn_submit_search_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cnicOrLicense = getRadioGroupValue(radioGroupLicenseType);

                String driverNo = text_input_driver_no.getText().toString();
                String vehNo = text_input_veh_no.getText().toString();

                try{
                    VehicleSearchActivity.OkHttpHandler okHttpHandler = new VehicleSearchActivity.OkHttpHandler();
                    if (cnicOrLicense.equalsIgnoreCase("cnic")) {
                        okHttpHandler.execute("http://10.0.2.2:8000/v1/driver/cnic/"+driverNo, "driver").get();
                    } else {
                        okHttpHandler.execute("http://10.0.2.2:8000/v1/driver/license_no/"+driverNo, "driver").get();
                    }
                    VehicleSearchActivity.OkHttpHandler okHttpHandler2 = new VehicleSearchActivity.OkHttpHandler();
                    okHttpHandler2.execute("http://10.0.2.2:8000/v1/vehicle/vehicle_registration_number/"+vehNo, "vehicle").get();

                } catch (Exception ex) {System.out.println(ex.getMessage());} finally {
                    searchDriverAndVehicle();
                }
            }
        });
    }

    public void searchDriverAndVehicle() {
        if((driverResp == null) && (vehicleResp == null)){
            printAlert("Please provide correct data, No match found with entered data for Driver & Vehicle.");
        } else if(driverResp == null){
            printAlert("Please provide correct data, No match found with entered data for Driver.");
        } else if(vehicleResp == null){
            printAlert("Please provide correct data, No match found with entered data for Vehicle.");
        } else {
            Intent vehicleInspectionActivity = new Intent(this, VehicleInspectionActivity.class);
            vehicleInspectionActivity.putExtra("driver", driverResp);
            vehicleInspectionActivity.putExtra("vehicle", vehicleResp);
            startActivity(vehicleInspectionActivity);
        }
    }

    public void printAlert(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public String getRadioGroupValue(RadioGroup rg) {
        int radioButtonID = rg.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) rg.findViewById(radioButtonID);
        return (String) radioButton.getText();
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
                if (response.code() > 300) {
                    return null;
                }

                if (objects[1].toString().equalsIgnoreCase("driver")) {
                    driverResp = r;
                } else {
                    vehicleResp = r;
                }
                return r;
            }catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
}