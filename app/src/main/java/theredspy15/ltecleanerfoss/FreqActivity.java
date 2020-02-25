package theredspy15.ltecleanerfoss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class FreqActivity extends AppCompatActivity {

    TextView textView;
    ProcessBuilder processBuilder;
    String Holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream inputStream;
    Process process;
    byte[] byteArry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq);

        textView = (TextView) findViewById(R.id.CPUinfo);
        //CPUinfo.setText(ReadCPUinfo());

        byteArry = new byte[1024];

        try {
            processBuilder = new ProcessBuilder(DATA);
            process = processBuilder.start();
            inputStream = process.getInputStream();
            while (inputStream.read(byteArry) != -1) {
                Holder = Holder + new String(byteArry);
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        textView.setText(Holder);

    }

//    private String ReadCPUinfo()
//    {
//        ProcessBuilder cmd;
//        String result="";
//
//        try{
//            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
//            cmd = new ProcessBuilder(args);
//
//            Process process = cmd.start();
//            InputStream in = process.getInputStream();
//            byte[] re = new byte[1024];
//            while(in.read(re) != -1){
//                System.out.println(new String(re));
//                result = result + new String(re);
//            }
//            in.close();
//        } catch(IOException ex){
//            ex.printStackTrace();
//        }
//        return result;
//    }
}
