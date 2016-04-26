package com.example.luongt.misfit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.luongt.misfit.R;
import com.onballgroup.cominlan.model.IServer;

import java.util.List;

/**
 * Created by Phong Le on 4/23/2016.
 */
public class ServerAdapter extends ArrayAdapter<IServer> {
    public ServerAdapter(Context context, int resource, List<IServer> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IServer server = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.server_item, parent, false);
        }

        String string = server.getId() + "\n"
                + server.getName() + " " + server.getAddress() + ":"
                + server.getPort() + "\n"
                + server.getState().name();

        ((TextView)convertView.findViewById(R.id.serverText)).setText(string);

        return convertView;
    }


}
