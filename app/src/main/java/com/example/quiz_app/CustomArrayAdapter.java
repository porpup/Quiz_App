package com.example.quiz_app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Model> {
  private Context context;

  public CustomArrayAdapter(Context context, int resource, ArrayList<Model> users) {
    super(context, resource, users);
    this.context = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.custom_list_item, null);
    }

    TextView usernameTextView = view.findViewById(R.id.usernameTextView);
    TextView highScoreTextView = view.findViewById(R.id.highScoreTextView);

    Model user = getItem(position);

    if (user != null) {
      usernameTextView.setText(user.getUsername());
      highScoreTextView.setText("High Score: " + user.getHighScore());

      // Set the text color to white
      usernameTextView.setTextColor(Color.WHITE);
      highScoreTextView.setTextColor(Color.YELLOW);

      // Change text size
      usernameTextView.setTextSize(20);
      highScoreTextView.setTextSize(20);
    }

    return view;
  }
}
