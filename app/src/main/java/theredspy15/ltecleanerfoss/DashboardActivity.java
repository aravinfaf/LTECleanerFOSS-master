package theredspy15.ltecleanerfoss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import theredspy15.ltecleanerfoss.scan.MainScan;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout scanfileLL, sensorsLL,threatLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        scanfileLL = findViewById(R.id.scanfileLL);
        sensorsLL = findViewById(R.id.sensorsLL);
        threatLL = findViewById(R.id.threatLL);

        scanfileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            }
        });

        sensorsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, SensorDashboardActivity.class));
            }
        });
        threatLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MainScan.class));
            }
        });
    }
}
