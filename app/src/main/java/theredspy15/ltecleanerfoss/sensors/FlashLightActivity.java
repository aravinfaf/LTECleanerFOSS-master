package theredspy15.ltecleanerfoss.sensors;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import theredspy15.ltecleanerfoss.R;

public class FlashLightActivity extends AppCompatActivity {
    ToggleButton toggleButton;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);

        toggleButton = (ToggleButton) findViewById(R.id.onOffFlashlight);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {

                    //ToDo something
                    camera = Camera.open();
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameters);
                    camera.startPreview();

                } else {

                    //ToDo something
                    camera = Camera.open();
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.stopPreview();
                    camera.release();

                }
            }
        });

    }
}
