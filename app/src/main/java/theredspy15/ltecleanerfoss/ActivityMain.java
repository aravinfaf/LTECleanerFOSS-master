package theredspy15.ltecleanerfoss;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

                Toast.makeText(ActivityMain.this, ""+appInfoList.get(i).versionName, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
