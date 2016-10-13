package tjanssen.companionapp;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tops on 13-10-2016.
 */

public class JSONgradeTask extends AsyncTask<String, Void, ArrayList<GradeItem>> {

    private MainActivity parent;
    JsonReader reader;
    String Token;
    String RequestType;

    public JSONgradeTask(MainActivity parent){
        this.parent = parent;
    }
    @Override
    protected ArrayList<GradeItem> doInBackground(String... params) {
        HttpHandler handler = new HttpHandler();
        ArrayList<GradeItem> result = new ArrayList<>();
        this.Token = params[0];

        String jsonStr = handler.makeServiceCall("https://api.fhict.nl/grades/me", Token);
        if (jsonStr != null) {
            try {
                JSONArray grades = new JSONArray(jsonStr);
                for(int i = 0; i < grades.length(); i++){
                    JSONObject w = grades.getJSONObject(i);
                    String date = w.getString("date").substring(0,10);
                    String item = w.getString("item");
                    double grade = w.getDouble("grade");
                    GradeItem g = new GradeItem(date, item, grade);
                    result.add(g);
                }
            }
            catch(JSONException e){
                Log.e("JSON ", "Exception: " + e);
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<GradeItem> result)
    {
        parent.grades = result;
        ListView gradeList = (ListView) parent.findViewById(R.id.GradesList);
        gradeList.setAdapter(new GradeListAdapter(parent, result));


    }
}
