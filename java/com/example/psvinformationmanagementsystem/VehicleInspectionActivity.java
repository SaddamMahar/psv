package com.example.psvinformationmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VehicleInspectionActivity extends AppCompatActivity {

    JsonObject driverObj, vehicleObj;
    TextView inspected_by, last_inspection_date, last_inspection_status, vehicle_reg_no;
    TextView vehicle_make, vehicle_model, vehicle_color, vehicle_type, seating_capacity,
            route_cities, route_expiry, fitness_expiry, next_tyre_check_date,
            fire_extinguisher_expiry, transport_company;
    TextView driver_name, driver_cnic_no, driver_contact_no, driver_license_no, driver_license_type,
            driver_license_expiry_date, driver_licence_issuing_authority, driver_license_verification;
    ImageView img_check_driver_license_valid, img_check_vehicle_route_permit_valid, img_check_vehicle_fitness_certificate_valid, img_check_vehicle_tyres_condition, img_check_vehicle_fire_extinguisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_inspection);

        Bundle bundle = getIntent().getExtras();
        String driver = bundle.getString("driver");
        String vehicle = bundle.getString("vehicle");

        driverObj = new JsonParser().parse(driver).getAsJsonObject();
        vehicleObj = new JsonParser().parse(vehicle).getAsJsonObject();

        driver_license_expiry_date = (TextView) findViewById(R.id.driver_license_expiry_date);
        driver_licence_issuing_authority = (TextView) findViewById(R.id.driver_licence_issuing_authority);
        driver_license_verification = (TextView) findViewById(R.id.driver_license_verification);
        driver_name = (TextView) findViewById(R.id.driver_name);
        driver_cnic_no = (TextView) findViewById(R.id.driver_cnic_no);
        driver_contact_no = (TextView) findViewById(R.id.driver_contact_no);
        driver_license_no = (TextView) findViewById(R.id.driver_license_no);
        driver_license_type = (TextView) findViewById(R.id.driver_license_type);

        img_check_driver_license_valid = findViewById(R.id.img_check_driver_license_valid);
        img_check_vehicle_route_permit_valid = findViewById(R.id.img_check_vehicle_route_permit_valid);
        img_check_vehicle_fitness_certificate_valid = findViewById(R.id.img_check_vehicle_fitness_certificate_valid);
        img_check_vehicle_tyres_condition = findViewById(R.id.img_check_vehicle_tyres_condition);
        img_check_vehicle_fire_extinguisher = findViewById(R.id.img_check_vehicle_fire_extinguisher);

        inspected_by = (TextView) findViewById(R.id.inspected_by);
        last_inspection_date = (TextView) findViewById(R.id.last_inspection_date);
        last_inspection_status = (TextView) findViewById(R.id.last_inspection_status);
        vehicle_reg_no = (TextView) findViewById(R.id.vehicle_reg_no);
        vehicle_make = (TextView) findViewById(R.id.vehicle_make);
        vehicle_model = (TextView) findViewById(R.id.vehicle_model);
        vehicle_color = (TextView) findViewById(R.id.vehicle_color);
        vehicle_type = (TextView) findViewById(R.id.vehicle_type);
        seating_capacity = (TextView) findViewById(R.id.seating_capacity);
        route_cities = (TextView) findViewById(R.id.route_cities);
        route_expiry = (TextView) findViewById(R.id.route_expiry);
        fitness_expiry = (TextView) findViewById(R.id.fitness_expiry);
        next_tyre_check_date = (TextView) findViewById(R.id.next_tyre_check_date);
        fire_extinguisher_expiry = (TextView) findViewById(R.id.fire_extinguisher_expiry);
        transport_company = (TextView) findViewById(R.id.transport_company);

        driver_name.setText(driverObj.get("driverName").getAsString().toString());
        driver_cnic_no.setText(driverObj.get("cnic").getAsString().toString());
        driver_contact_no.setText(driverObj.get("contact").getAsString().toString());
        driver_license_no.setText(driverObj.get("licenseNo").getAsString().toString());
        driver_license_type.setText(driverObj.get("licenseType").getAsString().toString());
        driver_license_expiry_date.setText(driverObj.get("licenseExpiry").getAsString().toString());
        driver_licence_issuing_authority.setText(driverObj.get("licenseIssuingAuthority").getAsString().toString());
        driver_license_verification.setText(driverObj.get("licenseVerification").getAsString().toString());

        inspected_by.setText(vehicleObj.get("managerName").getAsString().toString());
        last_inspection_date.setText(vehicleObj.get("fitnessCertificateExpiry").getAsString().toString());
        last_inspection_status.setText(vehicleObj.get("vechicleTyreCondition").getAsString().toString());
        vehicle_reg_no.setText(vehicleObj.get("vehicleRegistrationNumber").getAsString().toString());
        vehicle_make.setText(vehicleObj.get("vehicleMake").getAsString().toString());
        vehicle_model.setText(vehicleObj.get("vehicleModel").getAsString().toString());
        vehicle_color.setText(vehicleObj.get("vehicleColor").getAsString().toString());
        vehicle_type.setText(vehicleObj.get("vehicleType").getAsString().toString());
        seating_capacity.setText(vehicleObj.get("seatingCapacity").getAsString().toString());
        route_cities.setText(vehicleObj.get("routeCity").getAsString().toString());
        if (!(vehicleObj.get("routePermitExpiry") == null)) {
            route_expiry.setText(vehicleObj.get("routePermitExpiry").getAsString().toString());
        }
        fitness_expiry.setText(vehicleObj.get("fitnessCertificateExpiry").getAsString().toString());
        next_tyre_check_date.setText(vehicleObj.get("nextTyreCheckingDate").getAsString().toString());
        fire_extinguisher_expiry.setText(vehicleObj.get("fireExtinguisherExpiry").getAsString().toString());
        transport_company.setText(vehicleObj.get("vechicleTransportCompany").getAsString().toString());

        if (driverObj.get("licenseNo").getAsString().toString() != "") {
            img_check_driver_license_valid.setBackgroundResource(R.drawable.ic_baseline_check_24);
        } else {
            img_check_driver_license_valid.setBackgroundResource(R.drawable.ic_baseline_clear_24);
        }

        if (vehicleObj.get("routePermitAuthority").getAsString().toString() != "") {
            img_check_vehicle_route_permit_valid.setBackgroundResource(R.drawable.ic_baseline_check_24);
        } else {
            img_check_vehicle_route_permit_valid.setBackgroundResource(R.drawable.ic_baseline_clear_24);
        }

        if (vehicleObj.get("routePermitAuthority").getAsString().toString() != "") {
            img_check_vehicle_fitness_certificate_valid.setBackgroundResource(R.drawable.ic_baseline_check_24);
        } else {
            img_check_vehicle_fitness_certificate_valid.setBackgroundResource(R.drawable.ic_baseline_clear_24);
        }

        if (vehicleObj.get("vechicleTyreCondition").getAsString().toString() != "") {
            img_check_vehicle_tyres_condition.setBackgroundResource(R.drawable.ic_baseline_check_24);
        } else {
            img_check_vehicle_tyres_condition.setBackgroundResource(R.drawable.ic_baseline_clear_24);
        }

        if (vehicleObj.get("fireExtinguisherExpiry").getAsString().toString() != "") {
            img_check_vehicle_fire_extinguisher.setBackgroundResource(R.drawable.ic_baseline_check_24);
        } else {
            img_check_vehicle_fire_extinguisher.setBackgroundResource(R.drawable.ic_baseline_clear_24);
        }

    }
}