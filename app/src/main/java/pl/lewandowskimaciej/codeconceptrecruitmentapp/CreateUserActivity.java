package pl.lewandowskimaciej.codeconceptrecruitmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateUserActivity extends AppCompatActivity {

    private String url = "https://gorest.co.in/public/v1/users";
    private TextView id;
    private String idString;
    private EditText name;
    private EditText email;
    private EditText gender;
    private EditText status;

    Button buttonDelette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


        id = (TextView) findViewById(R.id.textViewID);
        name = (EditText) findViewById(R.id.inputTextName);
        email = (EditText) findViewById(R.id.inputTextEmail);
        gender = (EditText) findViewById(R.id.inputTextGender);
        status = (EditText) findViewById(R.id.inputTextStatus);
        Button buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelette = (Button) findViewById(R.id.buttonDelette);
        buttonUpdate.setText("Create user");
        buttonDelette.setActivated(false);
        buttonDelette.setVisibility(View.INVISIBLE);

        setDefaultTextValue(getIntent());

    }

    private void setDefaultTextValue(Intent intent) {
        id.setText(id.getText());
        name.setText(name.getText());
        email.setText(email.getText());
        gender.setText(gender.getText());
        status.setText(status.getText());
    }

    public void updateUser(View view) throws JSONException {
        JSONObject jsonBody = new JSONObject("{\"name\":\""+ name.getText() +"\", "+
                "\"gender\":\""+ gender.getText() +"\", " +
                "\"email\":\""+ email.getText() + "\", " +
                "\"status\":\"" + status.getText() + "\"}");
        new HttpRequest(this, 1, url, jsonBody) {
            @Override
            public void callbackResponse() {
                try {
                    idString = (new JSONObject(getResponse()).get("data")).toString();
                    idString = (new JSONObject(idString).get("id")).toString();
                    id.setText(idString);
                } catch (JSONException e) { }
            }
        };

        buttonDelette.setVisibility(View.VISIBLE);
    }

    public void cancelEditUserData(View view) {
        setDefaultTextValue(getIntent());
    }

    public void deleteUser(View view) {
        new HttpRequest(this, 3, (url+"/" + idString), new JSONObject());
    }
}