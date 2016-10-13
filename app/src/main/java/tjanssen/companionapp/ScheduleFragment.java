package tjanssen.companionapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner weekSpinner;
    Spinner typeSpinner;
    Spinner elementSpinner;

    ArrayAdapter<CharSequence> typeAdapter;
    ArrayAdapter<CharSequence> elementAdapter;

    MainActivity parent;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        parent = (MainActivity) getActivity();
        //Populate Spinners
        weekSpinner = (Spinner) v.findViewById(R.id.WeekSpinner);
        typeSpinner = (Spinner) v.findViewById(R.id.TypeSpinner);
        elementSpinner = (Spinner) v.findViewById(R.id.ElementSpinner);
        populateTypeSpinner(parent.types);
        weekSpinner.setOnItemSelectedListener(this);
        typeSpinner.setOnItemSelectedListener(this);
        elementSpinner.setOnItemSelectedListener(this);

        return v;
    }

    public void populateTypeSpinner(ArrayList<String> types){
        String[] temp = types.toArray(new String[types.size()]);
        typeAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, temp);
        typeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.TypeSpinner:
                String[] temp;
                switch (typeSpinner.getSelectedItemPosition()) {
                    case 0:
                        temp = this.parent.classes.toArray(new String[this.parent.classes.size()]);
                        elementAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, temp);
                        elementSpinner.setAdapter(elementAdapter);
                        break;
                    case 1:
                        temp = this.parent.subjects.toArray(new String[this.parent.classes.size()]);
                        elementAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, temp);
                        elementSpinner.setAdapter(elementAdapter);
                        break;
                    case 2:
                        temp = this.parent.teachers.toArray(new String[this.parent.classes.size()]);
                        elementAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, temp);
                        elementSpinner.setAdapter(elementAdapter);
                        break;
                }
            case R.id.ElementSpinner:
                if (this.parent.fragmentInteractionComplete) {
                    new JSONscheduleTask(this.parent).execute(this.parent.token, typeSpinner.getSelectedItem().toString(), elementSpinner.getSelectedItem().toString(), weekSpinner.getSelectedItem().toString());
                    break;
                }
            case R.id.WeekSpinner:
                if (this.parent.fragmentInteractionComplete) {
                    new JSONscheduleTask(this.parent).execute(this.parent.token, typeSpinner.getSelectedItem().toString(), elementSpinner.getSelectedItem().toString(), weekSpinner.getSelectedItem().toString());
                    break;
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
