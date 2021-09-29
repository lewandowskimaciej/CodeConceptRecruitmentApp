package pl.lewandowskimaciej.codeconceptrecruitmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUserActivity extends AppCompatActivity {

    private String url;
    private TextView id;
    private EditText name;
    private EditText email;
    private EditText gender;
    private EditText status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


        url = getIntent().getStringExtra("url");
        id = (TextView) findViewById(R.id.textViewID);
        name = (EditText) findViewById(R.id.inputTextName);
        email = (EditText) findViewById(R.id.inputTextEmail);
        gender = (EditText) findViewById(R.id.inputTextGender);
        status = (EditText) findViewById(R.id.inputTextStatus);

        setDefaultTextValue(getIntent());

    }

    private void setDefaultTextValue(Intent intent) {
        id.setText("ID: " + intent.getStringExtra("id"));
        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        gender.setText(intent.getStringExtra("gender"));
        status.setText(intent.getStringExtra("status"));
    }

    public void updateUser(View view) throws JSONException {
        String url = getIntent().getStringExtra("url") + "/" + getIntent().getStringExtra("id");
        Log.d("TAG", id.toString());
        JSONObject jsonBody = new JSONObject("{\"name\":\""+ name.getText() +"\", "+
                 "\"gender\":\""+ gender.getText() +"\", " +
                "\"email\":\""+ email.getText() + "\", " +
                "\"status\":\"" + status.getText() + "\"}");
        new HttpRequest(this, 7, url, jsonBody);
    }

    public void cancelEditUserData(View view) {
        setDefaultTextValue(getIntent());
    }

    public void deleteUser(View view) {
        new HttpRequest(this, 3, url+"/" + getIntent().getStringExtra("id"), new JSONObject());
    }
}