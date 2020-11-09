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
import android.widget.Toast;

/**
 * @author		Shahar Yani
 * @version     1.0
 * @since		06/11/2020
 * The showTheObjects.class displays the 20 objects of a selected series and also displays index
 * or sum until the selected position
 */
public class showTheObjects extends AppCompatActivity implements AdapterView.OnItemClickListener{

    /**
     * The objects of the selected series.
     */
    String [] objects = new String[20];
    /**
     * The sumOfAll the objects.
     */
    double [] sumOfAll = new double[20];
    /**
     * The distance represents the distance between objects.
     * The first represents the first object of the whole series.
     */
    double distance, first;
    /**
     * The flag represents the type of the series (Geometric / Math).
     */
    boolean flag;
    /**
     * The Pos represents the position of the selected item in the ListView.
     */
    int pos;
    /**
     * The showRequest displays the result of the user request.
     */
    TextView showRequest;
    /**
     * The Lv shows the first 20 items of the selected series.
     */
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_the_objects);

        showRequest = findViewById(R.id.showRequest);
        lv = findViewById(R.id.lv);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(this);

        Intent gi = getIntent();
        first = Double.parseDouble(gi.getStringExtra("firstObject"));
        distance = Double.parseDouble(gi.getStringExtra("distance"));
        flag = gi.getBooleanExtra("type", true);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,objects);

        if (flag){ // Geometric series
            for (int i=0; i<objects.length; i++){
                objects[i] = String.valueOf(first*Math.pow(distance,(i+1)-1));
                if (i == 0) {
                    sumOfAll[0] = Double.parseDouble(objects[0]);
                }
                else{
                    sumOfAll[i] = sumOfAll[i-1] + Double.parseDouble(objects[i]);
                }
                while (!(objects[i].endsWith(".")) && objects[i].contains(".") && objects[i].endsWith("0")){
                    objects[i] = objects[i].substring(0,objects[i].length()-2);
                }
            }
        }
        else{ // Math series
            for (int i=0; i<objects.length;i++){
                objects[i] = String.valueOf(first+(distance*(i+1)-distance));
                if (i == 0) {
                    sumOfAll[0] = Double.parseDouble(objects[0]);
                }
                else{
                    sumOfAll[i] = sumOfAll[i-1] + Double.parseDouble(objects[i]);
                }
                while (!(objects[i].endsWith(".")) && objects[i].contains(".") && objects[i].endsWith("0")){
                    objects[i] = objects[i].substring(0,objects[i].length()-2);
                }
            }
        }
        lv.setAdapter(adp);

    }

    /**
     * Returns back to the MainActivity.class when the button was clicked.
     * @param view the button
     */
    public void returnBack(View view) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        view.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Options");
        menu.add("Index of the selected item");
        menu.add("Sum until the selected item");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String option = item.getTitle().toString();
        if (option.equals("Index of the selected item")) showRequest.setText(String.valueOf(pos+1));
        else showRequest.setText(String.valueOf((int)sumOfAll[pos]));
        return super.onContextItemSelected(item);
    }
}