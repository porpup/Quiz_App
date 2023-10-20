package com.example.quiz_app;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<String> {
  private Context context;

  public CustomArrayAdapter(Context context, int resource, ArrayList<String> items) {
    super(context, resource, items);
    this.context = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = super.getView(position, convertView, parent);
    TextView textView = (TextView) view.findViewById(android.R.id.text1);

    // Set the text color to white
    textView.setTextColor(Color.WHITE);

    return view;
  }
}
