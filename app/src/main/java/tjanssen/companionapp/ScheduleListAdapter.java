package tjanssen.companionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tops on 13-10-2016.
 */

public class ScheduleListAdapter extends BaseAdapter {
    private List<ScheduleItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ScheduleListAdapter(Context aContext, List<ScheduleItem> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.schedule_list_layout, parent, false);
        }
        TextView tvRoom = (TextView) convertView.findViewById(R.id.RoomTextView);
        TextView tvClass = (TextView) convertView.findViewById(R.id.ClassTextView);
        TextView tvTeacher = (TextView) convertView.findViewById(R.id.TeacherTextView);
        TextView tvTime = (TextView) convertView.findViewById(R.id.TimeTextView);

        ScheduleItem scheduleItem = this.listData.get(position);
        tvRoom.setText(scheduleItem.room);
        tvClass.setText(scheduleItem.subject);
        tvTeacher.setText(scheduleItem.teacher);
        tvTime.setText(scheduleItem.time);

        return convertView;
    }
}
