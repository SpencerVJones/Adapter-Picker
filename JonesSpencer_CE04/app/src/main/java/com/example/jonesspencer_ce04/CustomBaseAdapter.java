// Spencer Jones
// MDV3832-0 - 062024
// CustomBaseAdapter.java

package com.example.jonesspencer_ce04;

// Imports
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    private List<MainActivity.Person> personList; // List to store person data
    private LayoutInflater inflater;

    // Constructor
    public CustomBaseAdapter(Context context, List<MainActivity.Person> personList) {
        this.personList = personList;
        this.inflater = LayoutInflater.from(context);
    }

    // Returns size of person list
    @Override
    public int getCount() {
        return personList.size();
    }

    // Returns person object at specified position
    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    // Returns item ID (position)
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Creates and returns view for list items
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.base_adaptor, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.birthday = convertView.findViewById(R.id.birthday);
            holder.image = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MainActivity.Person person = personList.get(position); // Get person object for current position
        // Set person's name, birthday, and image in view
        holder.name.setText(person.getFirstName() + " " + person.getLastName());
        holder.birthday.setText(person.getBirthday());
        holder.image.setImageResource(person.getPicture());

        return convertView;
    }

    // ViewHolder class to hold references to views in each list item
    static class ViewHolder {
        TextView name;
        TextView birthday;
        ImageView image;
    }
}