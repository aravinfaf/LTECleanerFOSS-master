package theredspy15.ltecleanerfoss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailPhoneActivity extends AppCompatActivity {

    Button loginBV;
    EditText email,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_phone);

        loginBV=findViewById(R.id.loginBV);
        email=findViewById(R.id.etUsername);
        mobile=findViewById(R.id.etPassword);

        loginBV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().length()==0){
                    Toast.makeText(EmailPhoneActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }else if(mobile.getText().toString().length()!=10){
                    Toast.makeText(EmailPhoneActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }else{
                 PrefUtils.saveToPrefs(EmailPhoneActivity.this,"email",email.getText().toString());
                 PrefUtils.saveToPrefs(EmailPhoneActivity.this,"mobile",mobile.getText().toString());

                 startActivity(new Intent(EmailPhoneActivity.this,DashboardActivity.class));
                }
            }
        });
    }
}
