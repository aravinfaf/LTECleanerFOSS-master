package theredspy15.ltecleanerfoss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ListSensorsActivity extends AppCompatActivity {

    ListView lv;
    SensorManager smm;
    List<Sensor> sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sensors);

        lv=findViewById(R.id.lv);

        smm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = smm.getSensorList(Sensor.TYPE_ALL);
        lv.setAdapter(new ArrayAdapter<Sensor>(this, android.R.layout.simple_list_item_1,  sensor));
    }
}
