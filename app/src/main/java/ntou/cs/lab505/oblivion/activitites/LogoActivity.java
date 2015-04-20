package ntou.cs.lab505.oblivion.activitites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ntou.cs.lab505.oblivion.R;

public class LogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
    }

    public void changeView(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
