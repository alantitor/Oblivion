package ntou.cs.lab505.oblivion.activitites;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ntou.cs.lab505.oblivion.Parameters.Record;
import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.sqlite.UAAdapter;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void playMethod(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    public void settingMethod(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void testMethod(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void infoMethod(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView tv = new TextView(this);
        tv.setText("Wrong!");
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        builder.setCustomTitle(tv);
        builder.setMessage("Logout?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                clearData();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialong = builder.show();
    }

    public void clearData() {
        UAAdapter uaAdapter = new UAAdapter(this.getApplicationContext());
        uaAdapter.open();
        uaAdapter.logout();
        uaAdapter.close();
    }
}