package tjanssen.companionapp;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Tops on 13-10-2016.
 */

public class GradeListAdapter extends BaseAdapter {
    private List<GradeItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public GradeListAdapter(Context aContext, List<GradeItem> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getViewTypeCount(){
        return 3;
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
            convertView = li.inflate(R.layout.grade_list_layout, parent, false);
        }
        TextView tvDate = (TextView) convertView.findViewById(R.id.dateTextView);
        TextView tvItem = (TextView) convertView.findViewById(R.id.itemTextView);
        TextView tvGrade = (TextView) convertView.findViewById(R.id.gradeTextView);

        GradeItem gradeItem = this.listData.get(position);
        tvDate.setText(gradeItem.date);
        tvItem.setText(gradeItem.item);
        tvGrade.setText(gradeItem.grade.toString());

        return convertView;
    }
}
