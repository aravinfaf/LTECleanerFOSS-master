package theredspy15.ltecleanerfoss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import theredspy15.ltecleanerfoss.scan.MainScan;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout scanfileLL, sensorsLL,threatLL,freqLL;
    TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        user_name = findViewById(R.id.user_name);
        scanfileLL = findViewById(R.id.scanfileLL);
        sensorsLL = findViewById(R.id.sensorsLL);
        threatLL = findViewById(R.id.threatLL);
        freqLL = findViewById(R.id.freqLL);

        PrefUtils.saveToPrefs(DashboardActivity.this,"model",Build.MODEL);

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
        freqLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, FreqActivity.class));
            }
        });

        StringBuffer infoBuffer = new StringBuffer();

        infoBuffer.append("-------------------------------------\n");
        infoBuffer.append("Model :" + Build.MODEL + "\n");//The end-user-visible name for the end product.
        infoBuffer.append("Device: " + Build.DEVICE + "\n");//The name of the industrial design.
        infoBuffer.append("Manufacturer: " + Build.MANUFACTURER + "\n");//The manufacturer of the product/hardware.
        infoBuffer.append("Board: " + Build.BOARD + "\n");//The name of the underlying board, like "goldfish".
        infoBuffer.append("Brand: " + Build.BRAND + "\n");//The consumer-visible brand with which the product/hardware will be associated, if any.
        infoBuffer.append("Serial: " + Build.SERIAL + "\n");
        infoBuffer.append("-------------------------------------\n");

        Toast.makeText(DashboardActivity.this, ""+infoBuffer.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
