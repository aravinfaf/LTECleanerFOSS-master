package theredspy15.ltecleanerfoss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import theredspy15.ltecleanerfoss.sensors.AccelerometerSensorActivity;
import theredspy15.ltecleanerfoss.sensors.CameraActivity;
import theredspy15.ltecleanerfoss.sensors.FingerprintSensorActivity;
import theredspy15.ltecleanerfoss.sensors.FlashLightActivity;
import theredspy15.ltecleanerfoss.sensors.MotionSensorActivity;
import theredspy15.ltecleanerfoss.sensors.TouchSensorActivity;

public class SensorDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    CardView allowed_permissionCV, listsensorsCV, motionsensorCV, accelerometerCV, flashlightCV, touchCV, cameraCV, fingerprintCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_dashboard);

        allowed_permissionCV = findViewById(R.id.allowed_permissionCV);
        motionsensorCV = findViewById(R.id.motionsensorCV);
        accelerometerCV = findViewById(R.id.accelerometerCV);
        flashlightCV = findViewById(R.id.flashlightCV);
        listsensorsCV = findViewById(R.id.listsensorsCV);
        touchCV = findViewById(R.id.touchCV);
        cameraCV = findViewById(R.id.cameraCV);
        fingerprintCV = findViewById(R.id.fingerprintCV);

        allowed_permissionCV.setOnClickListener(this::onClick);
        motionsensorCV.setOnClickListener(this::onClick);
        accelerometerCV.setOnClickListener(this::onClick);
        flashlightCV.setOnClickListener(this::onClick);
        listsensorsCV.setOnClickListener(this::onClick);
        touchCV.setOnClickListener(this::onClick);
        cameraCV.setOnClickListener(this::onClick);
        fingerprintCV.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.allowed_permissionCV:
                startActivity(new Intent(SensorDashboardActivity.this, ActivityMain.class));
                break;
            case R.id.listsensorsCV:
                startActivity(new Intent(SensorDashboardActivity.this, ListSensorsActivity.class));
                break;
            case R.id.motionsensorCV:
                startActivity(new Intent(SensorDashboardActivity.this, MotionSensorActivity.class));
                break;
            case R.id.accelerometerCV:
                startActivity(new Intent(SensorDashboardActivity.this, AccelerometerSensorActivity.class));
                break;
            case R.id.flashlightCV:
                startActivity(new Intent(SensorDashboardActivity.this, FlashLightActivity.class));
                break;
            case R.id.touchCV:
                startActivity(new Intent(SensorDashboardActivity.this, TouchSensorActivity.class));
                break;
            case R.id.cameraCV:
                startActivity(new Intent(SensorDashboardActivity.this, CameraActivity.class));
                break;
            case R.id.fingerprintCV:
                startActivity(new Intent(SensorDashboardActivity.this, FingerprintSensorActivity.class));
                break;
        }
    }
}
