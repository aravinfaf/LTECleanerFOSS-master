package theredspy15.ltecleanerfoss;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    private ListView appListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        appListView = findViewById(R.id.appListView);

        final List<AppInfo> appInfoList = AppInfo.getAppInfoList(this);
        AppListAdapter adapter = new AppListAdapter(this, R.layout.app_info_item, appInfoList);
        appListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(ActivityMain.this, ""+appInfoList.get(i).packageName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", appInfoList.get(i).packageName, null);
                intent.setData(uri);
                startActivityForResult(intent, 101);

            }
        });
    }
}
