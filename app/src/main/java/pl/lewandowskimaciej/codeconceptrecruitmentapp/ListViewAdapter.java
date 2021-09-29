package pl.lewandowskimaciej.codeconceptrecruitmentapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context = null;
    private List<String> fields = null;

    public ListViewAdapter(Context context, JSONArray arr) {
        this.context = context;
        this.fields = new ArrayList<String>();
        for (int i=0; i<arr.length(); ++i) {
            try {
                fields.add(arr.getJSONObject(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getCount() {
        return fields.size();
    }

    @Override
    public Object getItem(int position) {
        return fields.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.itemlist, null,true);
        TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
        TextView textViewId = (TextView) rowView.findViewById(R.id.textViewId);
        TextView textViewEmail = (TextView) rowView.findViewById(R.id.textViewEmail);

//        //textView.setText(fields.get(position));

        try {
            JSONObject jsonObject = new JSONObject(fields.get(position));

            textViewId.setText("ID: " + jsonObject.getString("id") + " |  ");
            textViewName.setText(jsonObject.getString("name"));
            textViewEmail.setText(jsonObject.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("MyLog", " Position  " + position);
        return rowView;
    }

}
