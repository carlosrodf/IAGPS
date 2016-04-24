package usac.tesis.com.tesis.profileManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import usac.tesis.com.tesis.R;

/**
 * Created by root on 20/04/16.
 */
public class customAdapter extends ArrayAdapter<perfil>{

    public customAdapter(Context context, int resource, List<perfil> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        perfil p = getItem(position);
        TextView name = (TextView)customView.findViewById(R.id.insert_name);
        TextView desc = (TextView)customView.findViewById(R.id.insert_desc);

        name.setText(p.getName());
        desc.setText(p.getDescription());
        return customView;
    }
}
