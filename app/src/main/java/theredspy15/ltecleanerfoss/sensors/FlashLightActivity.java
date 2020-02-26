package theredspy15.ltecleanerfoss.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import theredspy15.ltecleanerfoss.PrefUtils;
import theredspy15.ltecleanerfoss.R;

public class FlashLightActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    Camera camera;
    TextView shareTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);

        toggleButton = (ToggleButton) findViewById(R.id.onOffFlashlight);
        shareTV =  findViewById(R.id.shareTV);

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

                    shareTV.setVisibility(View.VISIBLE);
                } else {

                    //ToDo something
                    camera = Camera.open();
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.stopPreview();
                    camera.release();

                }

                shareTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        share();
                    }
                });
            }
        });
    }

    private void share() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FlashLightActivity.this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
        bottomSheetDialog.show();

        LinearLayout sms_LL = bottomSheetDialog.findViewById(R.id.sms_LL);
        LinearLayout whatsapp_LL = bottomSheetDialog.findViewById(R.id.whatsapp_LL);

        whatsapp_LL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                PackageManager packageManager = getPackageManager();

                if (packageManager != null) {

                    Intent i = new Intent(Intent.ACTION_VIEW);

                    try {

                        Intent waIntent = new Intent(Intent.ACTION_SEND);
                        waIntent.setType("text/plain");
                        String text = "Flash Light test on device " + PrefUtils.getFromPrefs(FlashLightActivity.this, "model", "");
                        waIntent.setPackage("com.whatsapp");
                        if (waIntent != null) {
                            waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                            startActivity(Intent.createChooser(waIntent, text));
                        } else {
                            Toast.makeText(FlashLightActivity.this, "WhatsApp not found", Toast.LENGTH_SHORT)
                                    .show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(FlashLightActivity.this, "WhatsApp not installed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sms_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:"+PrefUtils.getFromPrefs(FlashLightActivity.this,"email","")+
                        "?subject=Flash Light test on device "+PrefUtils.getFromPrefs(FlashLightActivity.this,"model",""));
                intent.setData(data);
                startActivity(intent);
            }
        });
    }
}
