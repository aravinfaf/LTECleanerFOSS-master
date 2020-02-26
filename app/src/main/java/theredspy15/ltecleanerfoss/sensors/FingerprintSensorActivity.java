package theredspy15.ltecleanerfoss.sensors;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import theredspy15.ltecleanerfoss.PrefUtils;
import theredspy15.ltecleanerfoss.R;

public class FingerprintSensorActivity extends AppCompatActivity {

    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private TextView textView;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    TextView shareTV;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_sensor);

        shareTV = findViewById(R.id.shareTV);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {


            keyguardManager =
                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            textView = (TextView) findViewById(R.id.textview);

            if (!fingerprintManager.isHardwareDetected()) {
                textView.setText("Your device doesn't support fingerprint authentication");
            }


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                textView.setText("Please enable the fingerprint permission");

            }

            if (!fingerprintManager.hasEnrolledFingerprints()) {
                textView.setText("No fingerprint configured. Please register at least one fingerprint in your device's Settings");
            }

            if (!keyguardManager.isKeyguardSecure()) {
                textView.setText("Please enable lockscreen security in your device's Settings");
            } else {
                try {

                    generateKey();
                } catch (FingerprintException e) {
                    e.printStackTrace();
                }
                if (initCipher()) {
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);

                    shareTV.setVisibility(View.VISIBLE);

                    shareTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            share();

                        }

                    });
                }
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() throws FingerprintException {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");


            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean initCipher() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    private class FingerprintException extends Exception {

        public FingerprintException(Exception e) {
            super(e);
        }
    }


    private void share() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FingerprintSensorActivity.this);
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
                        String text = "Fingerprint test on device " + PrefUtils.getFromPrefs(FingerprintSensorActivity.this, "model", "");
                        waIntent.setPackage("com.whatsapp");
                        if (waIntent != null) {
                            waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                            startActivity(Intent.createChooser(waIntent, text));
                        } else {
                            Toast.makeText(FingerprintSensorActivity.this, "WhatsApp not found", Toast.LENGTH_SHORT)
                                    .show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(FingerprintSensorActivity.this, "WhatsApp not installed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sms_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:"+PrefUtils.getFromPrefs(FingerprintSensorActivity.this,"email","")+
                        "?subject=Fingerprint test on device "+PrefUtils.getFromPrefs(FingerprintSensorActivity.this,"model",""));
                intent.setData(data);
                startActivity(intent);
            }
        });
    }
}