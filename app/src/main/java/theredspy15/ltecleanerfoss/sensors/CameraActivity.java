package theredspy15.ltecleanerfoss.sensors;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import theredspy15.ltecleanerfoss.PrefUtils;
import theredspy15.ltecleanerfoss.R;

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    TextView shareTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        this.imageView = (ImageView)this.findViewById(R.id.imageview);
        Button photoButton = (Button) this.findViewById(R.id.takephoto);
        shareTV = findViewById(R.id.shareTV);
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            shareTV.setVisibility(View.VISIBLE);

            shareTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    share();

                }
            });
        }
    }
    private void share() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CameraActivity.this);
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
                        String text = "Camera test on device " + PrefUtils.getFromPrefs(CameraActivity.this, "model", "");
                        waIntent.setPackage("com.whatsapp");
                        if (waIntent != null) {
                            waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                            startActivity(Intent.createChooser(waIntent, text));
                        } else {
                            Toast.makeText(CameraActivity.this, "WhatsApp not found", Toast.LENGTH_SHORT)
                                    .show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CameraActivity.this, "WhatsApp not installed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sms_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:"+PrefUtils.getFromPrefs(CameraActivity.this,"email","")+
                        "?subject=Camera test on device "+PrefUtils.getFromPrefs(CameraActivity.this,"model",""));
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

}