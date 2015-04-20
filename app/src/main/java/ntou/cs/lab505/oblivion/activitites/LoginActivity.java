package ntou.cs.lab505.oblivion.activitites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.sqlite.UAAdapter;

public class LoginActivity extends Activity {

    UAAdapter uaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // create database adapter.
        this.uaAdapter = new UAAdapter(this.getApplicationContext());


        // auto login
        this.uaAdapter.open();  // don't forget open database.
        if (autoLogin()) {
            // go to MenuActivity.
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        this.uaAdapter.close();

    }

    public void logIn(View view) {

        // get id from activity.
        EditText account = (EditText) findViewById(R.id.account_activity_login);
        EditText password = (EditText) findViewById(R.id.password_activity_login);
        Log.d("LoginActivity", "account: " + account.getText().toString() + ", password: " + password.getText().toString());

        // input data not complete.
        if (account.getText().length() == 0 || password.getText().length() == 0) {
            // show alert message.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TextView tv = new TextView(this);
            tv.setText("Wrong!");
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER);
            builder.setCustomTitle(tv);
            builder.setMessage("Input data not correct.");
            builder.setPositiveButton("OK", null);
            AlertDialog dialong = builder.show();
            return ;
        }

        // check user information.
        boolean state = false;
        this.uaAdapter.open();  // don't forget open database.
        state = this.uaAdapter.checkAccount(account.getText().toString(), password.getText().toString());
        this.uaAdapter.close();

        if (state == true) {  // go to MenuActivity.
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } else {
            // show alert message.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TextView tv = new TextView(this);
            tv.setText("Wrong!");
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER);
            builder.setCustomTitle(tv);
            builder.setMessage("Input data not correct.");
            builder.setPositiveButton("OK", null);
            AlertDialog dialong = builder.show();
            return ;
        }
    }

    private boolean autoLogin() {

        if (uaAdapter.autoLoginAccount()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onStop() {
        // clear EditText value.
        EditText et1 = (EditText) findViewById(R.id.account_activity_login);
        EditText et2 = (EditText) findViewById(R.id.password_activity_login);
        et1.setText("");
        et2.setText("");

        super.onStop();
    }
}