package com.example.ex092;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author		Shahar Yani
 * @version     1.1
 * @since		06/11/2020
 * The showTheObjects.class displays the 20 objects of a selected series and also displays index
 * or sum until the selected position
 */
public class showTheObjects extends AppCompatActivity implements  View.OnCreateContextMenuListener {

    String[] objects = new String[20];
    double[] sumOfAll = new double[20];
    double distance, first;
    boolean flag;
    int pos;
    TextView showRequest;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_the_objects);

        showRequest = findViewById(R.id.showRequest);
        lv = findViewById(R.id.lv);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnCreateContextMenuListener(this);

        Intent gi = getIntent();
        first = Double.parseDouble(gi.getStringExtra("firstObject"));
        distance = Double.parseDouble(gi.getStringExtra("distance"));
        flag = gi.getBooleanExtra("type", true);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, objects);

        if (flag) { // Geometric series
            for (int i = 0; i < objects.length; i++) {
                objects[i] = String.valueOf(first * Math.pow(distance, (i + 1) - 1));
                if (i == 0) {
                    sumOfAll[0] = Double.parseDouble(objects[0]);
                } else {
                    sumOfAll[i] = sumOfAll[i - 1] + Double.parseDouble(objects[i]);
                }
                while (!(objects[i].endsWith(".")) && objects[i].contains(".") && objects[i].endsWith("0")) {
                    objects[i] = objects[i].substring(0, objects[i].length() - 2);
                }
            }
        } else { // Math series
            for (int i = 0; i < objects.length; i++) {
                objects[i] = String.valueOf(first + (distance * (i + 1) - distance));
                if (i == 0) {
                    sumOfAll[0] = Double.parseDouble(objects[0]);
                } else {
                    sumOfAll[i] = sumOfAll[i - 1] + Double.parseDouble(objects[i]);
                }
                while (!(objects[i].endsWith(".")) && objects[i].contains(".") && objects[i].endsWith("0")) {
                    objects[i] = objects[i].substring(0, objects[i].length() - 2);
                }
            }
        }
        lv.setAdapter(adp);
    }

    /**
     * Returns back to the MainActivity.class when the button was clicked.
     *
     * @param view the button
     */
    public void returnBack(View view) {
        finish();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        v.setOnCreateContextMenuListener(this);
        menu.setHeaderTitle("Options");
        menu.add("Index of the selected item");
        menu.add("Sum until the selected item");
    }

    /**
     * The onContextItemSelected method works when the user clicks a long click on a item of the lv
     *
     * @param item the item that selected from the context menu
     * @return True if it works currently.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adpInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String option = item.getTitle().toString();
        pos = adpInfo.position;
        if (option.equals("Index of the selected item"))
            showRequest.setText(String.valueOf(pos + 1));
        else showRequest.setText(String.valueOf((int) sumOfAll[pos]));
        return true;
    }

}