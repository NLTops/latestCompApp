package tjanssen.companionapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Tops on 13-10-2016.
 */

public class JSONinitialTask extends AsyncTask<String, Void, ArrayList<String>>
{
    private MainActivity parent;
    JsonReader reader;
    String Token;
    String RequestType;

    public JSONinitialTask (MainActivity parent){
        this.parent = parent;
    }

    public ArrayList<String> getWeeks(){
        HttpHandler handler = new HttpHandler();
        ArrayList<String> result = new ArrayList<>();
        String jsonStr = handler.makeServiceCall("https://api.fhict.nl/schedule/weeks", Token);
        if (jsonStr != null) {
            try {
                JSONArray weeks = new JSONArray(jsonStr);
                for(int i = 0; i < weeks.length(); i++){
                    JSONObject w = weeks.getJSONObject(i);
                    String date = w.getString("start").substring(0,10);
                    result.add(date);
                }
            }
            catch(JSONException e){
                Log.e("JSON ", "Exception: " + e);
            }
        }
        return result;
    }

    public ArrayList<String> getTeachers(){
        HttpHandler handler = new HttpHandler();
        ArrayList<String> result = new ArrayList<>();
        String jsonStr = handler.makeServiceCall("https://api.fhict.nl/schedule/autocomplete/Teacher", Token);
        if (jsonStr != null) {
            try {
                JSONArray weeks = new JSONArray(jsonStr);
                for(int i = 0; i < weeks.length(); i++){
                    JSONObject w = weeks.getJSONObject(i);
                    String date = w.getString("name");
                    result.add(date);
                }
            }
            catch(JSONException e){
                Log.e("JSON ", "Exception: " + e);
            }
        }
        return result;
    }

    public ArrayList<String> getSubjects(){
        HttpHandler handler = new HttpHandler();
        ArrayList<String> result = new ArrayList<>();
        String jsonStr = handler.makeServiceCall("https://api.fhict.nl/schedule/autocomplete/Subject", Token);
        if (jsonStr != null) {
            try {
                JSONArray weeks = new JSONArray(jsonStr);
                for(int i = 0; i < weeks.length(); i++){
                    JSONObject w = weeks.getJSONObject(i);
                    String date = w.getString("name");
                    result.add(date);
                }
            }
            catch(JSONException e){
                Log.e("JSON ", "Exception: " + e);
            }
        }
        return result;
    }

    public ArrayList<String> getClasses() {
        HttpHandler handler = new HttpHandler();
        ArrayList<String> result = new ArrayList<>();
        String jsonStr = handler.makeServiceCall("https://api.fhict.nl/schedule/autocomplete/Class", Token);
        if (jsonStr != null) {
            try {
                JSONArray weeks = new JSONArray(jsonStr);
                for(int i = 0; i < weeks.length(); i++){
                    JSONObject w = weeks.getJSONObject(i);
                    String date = w.getString("name");
                    result.add(date);
                }
            }
            catch(JSONException e){
                Log.e("JSON ", "Exception: " + e);
            }
        }
        return result;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        Token = params[0];
        RequestType = params[1];
        ArrayList<String> result = new ArrayList<>();
        switch(RequestType)
        {
            case "spinnerWeek":
                result = getWeeks();
                break;
            case "spinnerElementClass":
                result = getClasses();
                break;
            case "spinnerElementTeacher":
                result = getTeachers();
                break;
            case "spinnerElementSubject":
                result = getSubjects();
                break;
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result)
    {
        Spinner weekSpinner;
        Spinner elementSpinner;
        String[] temp;
        ArrayAdapter<CharSequence> elementAdapter;

        switch(RequestType)
        {
            case "spinnerWeek":
                parent.weeks = result;
                weekSpinner = (Spinner) parent.findViewById(R.id.WeekSpinner);
                temp = result.toArray(new String[result.size()]);
                ArrayAdapter<CharSequence> weekAdapter;
                weekAdapter = new ArrayAdapter<CharSequence>(parent, android.R.layout.simple_spinner_dropdown_item, temp);
                weekAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                weekSpinner.setAdapter(weekAdapter);
                break;
            case "spinnerElementClass":
                parent.classes = result;
                elementSpinner = (Spinner) parent.findViewById(R.id.ElementSpinner);
                temp = result.toArray(new String[result.size()]);
                elementAdapter = new ArrayAdapter<CharSequence>(parent, android.R.layout.simple_spinner_dropdown_item, temp);
                elementAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                elementSpinner.setAdapter(elementAdapter);
                break;
            case "spinnerElementTeacher":
                parent.teachers = result;
                break;
            case "spinnerElementSubject":
                parent.subjects = result;
                parent.fragmentInteractionComplete = true;
                break;
        }
    }
}
