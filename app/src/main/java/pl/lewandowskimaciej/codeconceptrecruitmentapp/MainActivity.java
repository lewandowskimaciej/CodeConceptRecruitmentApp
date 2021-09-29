package pl.lewandowskimaciej.codeconceptrecruitmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private String url = "https://gorest.co.in/public/v1/users";
    private String id;
    //private RequestQueue queue;
    private JSONObject jsonBody;

    private String TAG = "MyLog";
    protected int requestMethod;

    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void searchUser(View view) {
//        Intent intent = new Intent(this, SearchUserActivity.class);
//        startActivity(intent);
    }

    public void createNewUser(View view) throws JSONException {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);

    }


    public void openListView(View view) {
        Intent intent = new Intent(this, ListViewActivity.class);
        intent.putExtra("openURL", url);
        startActivity(intent);
    }

}