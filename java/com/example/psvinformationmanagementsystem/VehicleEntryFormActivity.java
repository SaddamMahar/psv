package com.example.psvinformationmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VehicleEntryFormActivity extends AppCompatActivity {

    Button btn_submit_vehicle_data;
    EditText registeryNo, fcAuthority, type, make, model, color, chassis, engine, seating, route, city, permitAuthority, permitExpiry,
            fitnessCertNo, fitnessCertAuthority, vFitnessCertEx, vTyreCond, vNextTyreCheckDate, vFireExtingExp, vTransportComp,vMgrName, vMgrCellNo,
            vOwnerName, vOwnerCellNo;
    RadioGroup hasAcGroup, headLightsGroup, backLightsGroup, fogLightsGroup, emergencyLigthsGroup, hazardLightsGroup, frontScreenWippeGroup,
    sideMirrorGroup, standardNumberPlateGroup, hasSafetyConesGroup, hasFirstAidGroup, hasTrackingGroup, hasZeroSeatGroup, hasMovieRecorginGroup;

    String hasAcGroupS, headLightsGroupS, backLightsGroupS, fogLightsGroupS, emergencyLigthsGroupS, hazardLightsGroupS, frontScreenWippeGroupS,
    sideMirrorGroupS, standardNumberPlateGroupS, hasSafetyConesGroupS, hasFirstAidGroupS, hasTrackingGroupS, hasZeroSeatGroupS, hasMovieRecorginGroupS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_entry_form);

        registeryNo = (EditText) findViewById(R.id.veh_reg_no);
        fcAuthority = (EditText) findViewById(R.id.veh_fitness_certificate_authority);
        type = (EditText) findViewById(R.id.veh_type);
        make = (EditText) findViewById(R.id.veh_make);
        model = (EditText) findViewById(R.id.veh_model);
        color = (EditText) findViewById(R.id.veh_color);
        chassis = (EditText) findViewById(R.id.veh_chassis_no);
        engine = (EditText) findViewById(R.id.veh_engine_no);
        seating = (EditText) findViewById(R.id.veh_seating_capacity);
        route = (EditText) findViewById(R.id.veh_route_no);
        city = (EditText) findViewById(R.id.veh_route_city);
        permitAuthority = (EditText) findViewById(R.id.veh_route_permit_authority);
        permitExpiry = (EditText) findViewById(R.id.veh_route_permit_expiry);
        permitExpiry = (EditText) findViewById(R.id.veh_fitness_certificate_authority);
        fitnessCertNo = (EditText) findViewById(R.id.fitness_certificate_no);
        fitnessCertAuthority = (EditText) findViewById(R.id.veh_fitness_certificate_authority);
        vFitnessCertEx = (EditText) findViewById(R.id.veh_fitness_certificate_expiry);
        vTyreCond = (EditText) findViewById(R.id.veh_tyre_condition);
        vNextTyreCheckDate = (EditText) findViewById(R.id.veh_next_tyre_checking_date);
        vFireExtingExp = (EditText) findViewById(R.id.veh_fire_extinguisher_expiry);
        vTransportComp = (EditText) findViewById(R.id.veh_transport_company);
        vMgrName = (EditText) findViewById(R.id.veh_mgr_name);
        vMgrCellNo = (EditText) findViewById(R.id.veh_mgr_cell_no);
        vOwnerName = (EditText) findViewById(R.id.veh_owner_name);
        vOwnerCellNo = (EditText) findViewById(R.id.veh_owner_cell_no);

        hasAcGroup = (RadioGroup) findViewById(R.id.radio_group_has_ac);
        hasAcGroupS = getRadioGroupValue(hasAcGroup);

        headLightsGroup = (RadioGroup) findViewById(R.id.radio_group_headlights_condition);
        headLightsGroupS = getRadioGroupValue(headLightsGroup);

        backLightsGroup = (RadioGroup) findViewById(R.id.radio_group_backlights_condition);
        backLightsGroupS = getRadioGroupValue(backLightsGroup);

        fogLightsGroup = (RadioGroup) findViewById(R.id.radio_group_foglight_condition);
        fogLightsGroupS = getRadioGroupValue(fogLightsGroup);

        emergencyLigthsGroup = (RadioGroup) findViewById(R.id.radio_group_emergency_lights_condition);
        emergencyLigthsGroupS = getRadioGroupValue(emergencyLigthsGroup);

        hazardLightsGroup = (RadioGroup) findViewById(R.id.radio_group_hazard_lights_condition);
        hazardLightsGroupS = getRadioGroupValue(hazardLightsGroup);

        frontScreenWippeGroup = (RadioGroup) findViewById(R.id.radio_group_front_screen_viper_condition);
        frontScreenWippeGroupS = getRadioGroupValue(frontScreenWippeGroup);

        sideMirrorGroup = (RadioGroup) findViewById(R.id.radio_group_side_mirrors_condition);
        sideMirrorGroupS = getRadioGroupValue(sideMirrorGroup);

        standardNumberPlateGroup = (RadioGroup) findViewById(R.id.radio_group_standard_number_plate);
        standardNumberPlateGroupS = getRadioGroupValue(standardNumberPlateGroup);

        hasSafetyConesGroup = (RadioGroup) findViewById(R.id.radio_group_has_safety_cones);
        hasSafetyConesGroupS = getRadioGroupValue(hasSafetyConesGroup);

        hasFirstAidGroup = (RadioGroup) findViewById(R.id.radio_group_has_first_aid_box);
        hasFirstAidGroupS = getRadioGroupValue(hasFirstAidGroup);

        hasTrackingGroup = (RadioGroup) findViewById(R.id.radio_group_has_tracking);
        hasTrackingGroupS = getRadioGroupValue(hasTrackingGroup);

        hasZeroSeatGroup = (RadioGroup) findViewById(R.id.radio_group_has_zero_seat);
        hasZeroSeatGroupS = getRadioGroupValue(hasZeroSeatGroup);

        hasMovieRecorginGroup = (RadioGroup) findViewById(R.id.radio_group_has_movie_recording);
        hasMovieRecorginGroupS = getRadioGroupValue(hasMovieRecorginGroup);

        btn_submit_vehicle_data = (Button) findViewById(R.id.btn_submit_vehicle_data);

        btn_submit_vehicle_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VehicleEntryFormActivity.OkHttpHandler okHttpHandler= new VehicleEntryFormActivity.OkHttpHandler();
                okHttpHandler.execute("http://10.0.2.2:8000/v1/vehicle");
            }
        });
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
            try {
                String registryV = registeryNo.getText().toString();
                String fcaV = fcAuthority.getText().toString();
                String typeV = type.getText().toString();
                String makeV = make.getText().toString();
                String modelV = model.getText().toString();
                String colorV = color.getText().toString();
                String chassisV = chassis.getText().toString();
                String engineV = engine.getText().toString();
                String seatingV = seating.getText().toString();
                String routeV = route.getText().toString();
                String cityV = city.getText().toString();
                String pAuthorityV = permitAuthority.getText().toString();
                String expiryV = permitExpiry.getText().toString();
                String fitnessCN = fitnessCertNo.getText().toString();
                String fitnessCA = fitnessCertAuthority.getText().toString();
                String vFitnessCE = vFitnessCertEx.getText().toString();
                String vTyreC = vTyreCond.getText().toString();
                String vNextTCD = vNextTyreCheckDate.getText().toString();
                String vFireEE = vFireExtingExp.getText().toString();
                String vTransportC = vTransportComp.getText().toString();
                String vMName = vMgrName.getText().toString();
                String vMCN = vMgrCellNo.getText().toString();
                String vOwnerN = vOwnerName.getText().toString();
                String vOwnerCN = vOwnerCellNo.getText().toString();

                JsonObject jo = new JsonObject();
                jo.addProperty("vehicleRegistrationNumber", registryV);
                jo.addProperty("vehicleRegistrationAuthority", fcaV);
                jo.addProperty("vehicleType", typeV);
                jo.addProperty("vehicleMake", makeV);
                jo.addProperty("vehicleModel", modelV);
                jo.addProperty("vehicleColor", colorV);
                jo.addProperty("chassisNo", chassisV);
                jo.addProperty("engineNo", engineV);
                jo.addProperty("seatingCapacity", seatingV);
                jo.addProperty("vehicleRouteNo", routeV);
                jo.addProperty("routeCity", cityV);
                jo.addProperty("routePermitAuthority", pAuthorityV);
                jo.addProperty("routePermitExpiry", expiryV);
                jo.addProperty("fitnessCertificateNumber", fitnessCN);
                jo.addProperty("fitnessCertificateAuthority", fitnessCA);
                jo.addProperty("fitnessCertificateExpiry", vFitnessCE);
                jo.addProperty("nextTyreCheckingDate", vNextTCD);
                jo.addProperty("fireExtinguisherExpiry", vFireEE);
                jo.addProperty("vechicleTransportCompany", vTransportC);
                jo.addProperty("vechicleTyreCondition", vTyreC);
                jo.addProperty("managerName", vMName);
                jo.addProperty("managerCellNumber", vMCN);
                jo.addProperty("ownersName", vOwnerN);
                jo.addProperty("ownersCellNumber", vOwnerCN);
                jo.addProperty("vechicleHasAC", hasAcGroupS);
                jo.addProperty("headLights", headLightsGroupS);
                jo.addProperty("backLights", backLightsGroupS);
                jo.addProperty("fogLight", fogLightsGroupS);
                jo.addProperty("emergencyLight", emergencyLigthsGroupS);
                jo.addProperty("hazardLights", hazardLightsGroupS);
                jo.addProperty("frontScreenWiper", frontScreenWippeGroupS);
                jo.addProperty("sideMirror", sideMirrorGroupS);
                jo.addProperty("numberPlateAsPerPattern", standardNumberPlateGroupS);
                jo.addProperty("roadSafetyCones", hasSafetyConesGroupS);
                jo.addProperty("firstAidBox", hasFirstAidGroupS);
                jo.addProperty("tracking", hasTrackingGroupS);
                jo.addProperty("zeroSear", hasZeroSeatGroupS);
                jo.addProperty("movieRecording", hasMovieRecorginGroupS);

                String j = jo.toString();
                System.out.println("===== josn ======");
                System.out.println(j);
                System.out.println("===== josn ======");
                final MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, j);
                Request request = new Request.Builder()
                        .url(objects[0].toString())
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                String r = response.body().string();
                System.out.println(r);
                return r;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}