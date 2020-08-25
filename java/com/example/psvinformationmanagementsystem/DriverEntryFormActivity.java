package com.example.psvinformationmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DriverEntryFormActivity extends AppCompatActivity {
    private TextInputLayout textInputLayoutDriverDob, textInputLicenseExp;
    DatePickerDialog.OnDateSetListener setListenerDOB;
    DatePickerDialog.OnDateSetListener setListenerExp;
    Calendar calendar;
    private int year;
    private int month;
    private int day;
    Button btnSubmitDriverData;
    EditText textDriverName, textContact, textAddress, textCNIC, textDisability,
            textLicenseNo, textLicenseIssuingAuthority, textTransportCompany;
    RadioGroup eyeSightRG, licenseTypeRG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_entry_form);

        calendar = Calendar.getInstance();
        year = calendar.get(calendar.YEAR);
        month = calendar.get(calendar.MONTH);
        day = calendar.get(calendar.DAY_OF_MONTH);

        textDriverName = findViewById(R.id.text_input_driver_name);
        textContact = findViewById(R.id.text_input_driver_phone);
        textAddress = findViewById(R.id.text_input_driver_address);
        textCNIC = findViewById(R.id.text_input_driver_cnic);
        textDisability = findViewById(R.id.text_input_driver_disablity);
        textLicenseNo = findViewById(R.id.text_input_driver_license_no);
        textLicenseIssuingAuthority = findViewById(R.id.text_input_license_issuing_authority);
        textTransportCompany = findViewById(R.id.text_input_transport_company);
        eyeSightRG = findViewById(R.id.radioGroupEyeSight);
        licenseTypeRG = findViewById(R.id.radioGroupLicenseType);
        btnSubmitDriverData = findViewById(R.id.btn_submit_driver_data);
        textInputLicenseExp = findViewById(R.id.text_input_license_expiry_date);
        textInputLayoutDriverDob = findViewById(R.id.text_input_driver_dob);
        textInputLayoutDriverDob.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    return;
                Log.d("DATE CLICKED", "onClick: Clicked");
                openDobDatePicker();
            }
        });
        textInputLicenseExp.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    return;
                Log.d("DATE CLICKED", "onClick: Clicked");
                openExpDatePicker();
            }
        });
        textInputLayoutDriverDob.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDobDatePicker();
            }
        });
        textInputLicenseExp.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExpDatePicker();
            }
        });
        setListenerDOB = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + dayOfMonth;
                textInputLayoutDriverDob.getEditText().setText(date);
            }
        };
        setListenerExp = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + dayOfMonth;
                textInputLicenseExp.getEditText().setText(date);
            }
        };

        btnSubmitDriverData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openMainActivity() {

        DriverEntryFormActivity.OkHttpHandler okHttpHandler= new DriverEntryFormActivity.OkHttpHandler();
        okHttpHandler.execute("http://10.0.2.2:8000/v1/driver");
        Intent intentMainActivity = new Intent(this, MainActivity.class);
        startActivity(intentMainActivity);
    }

    public String getRadioGroupValue(RadioGroup rg) {
        int radioButtonID = rg.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) rg.findViewById(radioButtonID);
        return (String) radioButton.getText();
    }

    private void openDobDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(DriverEntryFormActivity.this, android.R.style.Theme_Material_Dialog, setListenerDOB, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorNhmpBlue)));
        datePickerDialog.show();
    }

    private void openExpDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(DriverEntryFormActivity.this, android.R.style.Theme_Material_Dialog, setListenerExp, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorNhmpBlue)));
        datePickerDialog.show();
    }

    public class OkHttpHandler extends AsyncTask {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                String dName = textDriverName.getText().toString();
                String contact = textContact.getText().toString();
                String address = textAddress.getText().toString();
                String cnic = textCNIC.getText().toString();
                String disability = textDisability.getText().toString();
                String licenseNo = textLicenseNo.getText().toString();
                String licenseIssuingAuth = textLicenseIssuingAuthority.getText().toString();
                String transportCompany = textTransportCompany.getText().toString();
                String eyeSight = getRadioGroupValue(eyeSightRG);
                String licenseType = getRadioGroupValue(licenseTypeRG);

                JsonObject jo = new JsonObject();
                jo.addProperty("driverName", dName);
                jo.addProperty("contact", contact);
                jo.addProperty("address", address);
                jo.addProperty("cnic", cnic);
                jo.addProperty("disability", disability);
                jo.addProperty("licenseNo", licenseNo);
                jo.addProperty("licenseExpiry", textInputLicenseExp.getEditText().getText().toString());
                jo.addProperty("licenseType", licenseType);
                jo.addProperty("licenseIssuingAuthority", licenseIssuingAuth);
                jo.addProperty("transportCompany", transportCompany);
                jo.addProperty("eyeSight", eyeSight);
                jo.addProperty("dateOfBirth", textInputLayoutDriverDob.getEditText().getText().toString());

                String j = jo.toString();
                final MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, j);
                Request request = new Request.Builder()
                        .url(objects[0].toString())
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                String r = response.body().string();
                return r;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}