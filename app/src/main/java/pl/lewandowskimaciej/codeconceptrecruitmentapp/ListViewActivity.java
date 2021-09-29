package pl.lewandowskimaciej.codeconceptrecruitmentapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListViewActivity extends Activity {

    private String url;
    private ListViewAdapter adapter = null;
    private ListView myList = null;
    private JSONArray items = new JSONArray();

    private String firstPage;
    private String previousPage;
    private String nextPage;
    private String lastPage;

    private EditText editTexPageNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        intentInit();
        myList = (ListView) findViewById(R.id.list);
        ViewGroup footer = (ViewGroup) getLayoutInflater().inflate(R.layout.listviewfooter, myList, false);
        myList.addFooterView(footer);
        footerInit();

        new HttpRequest(this, Request.Method.GET, url, (new JSONObject())) {
            @Override
            public void callbackResponse() {
                setMetaDataOfApi(getResponse());
                loadFields(getResponse());

            }

        };

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Toast.makeText(getApplicationContext(),"Place Your " + position + " Option Code", Toast.LENGTH_SHORT).show();
                Log.d("MyLog", adapter.getItem(position).toString());
                try {
                    JSONObject jsonObject = new JSONObject(adapter.getItem(position).toString());
                    Intent intent = new Intent(getApplicationContext(), UpdateUserActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("id", jsonObject.getString("id"));
                    intent.putExtra("name", jsonObject.getString("name"));
                    intent.putExtra("email", jsonObject.getString("email"));
                    intent.putExtra("gender", jsonObject.getString("gender"));
                    intent.putExtra("status", jsonObject.getString("status"));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void loadFields(String obj) {
        try {
            items = new JSONObject(obj).getJSONArray("data");
        } catch (JSONException e) {
            //TODO
        }
        adapter = new ListViewAdapter(ListViewActivity.this, items);
        myList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void footerInit() {
        TextView textViewGoFirstPage = (TextView) findViewById(R.id.textViewGoFirstPage);
        TextView textViewPreviousPage = (TextView) findViewById(R.id.textViewPreviousPage);
        TextView textViewNextPage = (TextView) findViewById(R.id.textViewNextPage);
        TextView textViewLastPage = (TextView) findViewById(R.id.textViewLastPage);
        Button buttonGo = findViewById(R.id.buttonGo);
        editTexPageNumber  = findViewById(R.id.editTexPageNumber);
        textViewGoFirstPage.setText("<<");
        textViewPreviousPage.setText(" < ");
        textViewNextPage.setText(" > ");
        textViewLastPage.setText(" >> ");
    }

    public void goFirstPage (View v) {
        goPage("first");
    }

    public void goPreviousPage (View v) {
        goPage("previous");
    }

    public void goNextPage (View v) {
        goPage("next");
    }

    public void goLastPage (View v) {
        goPage("last");
    }

    public void goPage (View v) {
        goPage(editTexPageNumber.getText().toString());
    }

    private void goPage(String s) {
        String url = s.equals("first") ? "https://gorest.co.in/public/v1/users?page=1" :
                (s.equals("previous") ? previousPage :
                        (s.equals("next") ? nextPage :
                                (s.equals("last") ? lastPage : "https://gorest.co.in/public/v1/users?page="+s)));
        Intent intent = new Intent(this, ListViewActivity.class);
        intent.putExtra("firstPage", firstPage);
        intent.putExtra("previousPage", previousPage);
        intent.putExtra("nextPage", nextPage);
        intent.putExtra("lastPage", lastPage);
        intent.putExtra("url", this.url);
        intent.putExtra("openURL", url);
        startActivity(intent);
    }

    private void setMetaDataOfApi(String s) {
        try {
            String meta = (new JSONObject(s).get("meta")).toString();
                String pagination = (new JSONObject(meta).get("pagination")).toString();
                String links = (new JSONObject(pagination).get("links")).toString();
                previousPage = (new JSONObject(links).get("previous")).toString();
                nextPage = (new JSONObject(links).get("next")).toString();
                lastPage = "https://gorest.co.in/public/v1/users?page=" +  (new JSONObject(pagination).get("pages")).toString();

                Log.d("mytag", lastPage);


        } catch (JSONException e) { Log.d("mylog", e.toString());};
    }

    private void intentInit() {
        Intent intent = getIntent();
        this.url = intent.getStringExtra("url");
        url = intent.getStringExtra("openURL");
        firstPage = intent.getStringExtra("firstPage");
        previousPage = intent.getStringExtra("previousPage");
        nextPage = intent.getStringExtra("nextPage");
        lastPage = intent.getStringExtra("lastPage");
    }

}