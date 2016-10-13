package tjanssen.companionapp;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Tops on 13-10-2016.
 */

public class JSONscheduleTask extends AsyncTask<String, Void, ArrayList<ScheduleItem>> {

    private MainActivity parent;
    JsonReader reader;
    String Token;
    String RequestType;

    public JSONscheduleTask(MainActivity parent){
        this.parent = parent;
    }
    @Override
    protected ArrayList<ScheduleItem> doInBackground(String... params) {
        HttpHandler handler = new HttpHandler();
        ArrayList<ScheduleItem> result = new ArrayList<>();
        this.Token = params[0];
        //https://api.fhict.nl/schedule/Class/ei4s2?days=7&start=2016-10-10
        StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://api.fhict.nl/schedule/");
            stringBuilder.append(params[1] + "/");
            stringBuilder.append(params[2] + "?days=7&start=");
            stringBuilder.append(params[3]);
        String url = stringBuilder.toString();
        url.replaceAll(" ", "%20");

        String jsonStr = handler.makeServiceCall(url, Token);
        if (jsonStr != null) {
            try {
                JSONObject primairy = new JSONObject(jsonStr);
                String title = primairy.getString("title");
                JSONArray schedules = primairy.getJSONArray("data");
                for(int i = 0; i < schedules.length(); i++) {
                    JSONObject secondary = schedules.getJSONObject(i);
                    String room = secondary.getString("room");
                    String subject = secondary.getString("subject");
                    String teacher = secondary.getString("teacherAbbreviation");
                    String start = secondary.getString("start").substring(11);
                    String end = secondary.getString("end").substring(11);
                    StringBuilder timeBuilder = new StringBuilder();
                    timeBuilder.append(start + " - ");
                    timeBuilder.append(end);

                    ScheduleItem scheduleItem = new ScheduleItem(room, subject, teacher, timeBuilder.toString());
                    result.add(scheduleItem);
                }
            }
            catch(JSONException e){
                Log.e("JSON ", "Exception: " + e);
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<ScheduleItem> result)
    {
        parent.schedule = result;
        ListView scheduleList= (ListView) parent.findViewById(R.id.ScheduleList);
        scheduleList.setAdapter(new ScheduleListAdapter(parent, result));
    }
}
