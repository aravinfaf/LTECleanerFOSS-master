package theredspy15.ltecleanerfoss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.Spinner;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    String isEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isEmpty = PrefUtils.getFromPrefs(Splash.this, "email", "");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (isEmpty.length() == 0) {
                    Intent mainIntent = new Intent(Splash.this, EmailPhoneActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                } else {
                    Intent mainIntent = new Intent(Splash.this, DashboardActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
