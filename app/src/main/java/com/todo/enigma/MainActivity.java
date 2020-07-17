
package com.todo.enigma;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.todo.calendar.CalendarView;
import com.todo.calendar.CalendarView.CalendarListener;
import com.todo.enigma.adapter.CustomItemsAdapter;
import com.todo.enigma.models.Item;
import com.todo.enigma.models.Priority;
import com.todo.enigma.utils.DBUtils;
import com.todo.enigma.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


/**
 * Sample Activity
 *
 * @author Marco Hernaiz Cao
 */
public class MainActivity extends AppCompatActivity implements CalendarListener, CompoundButton.OnCheckedChangeListener {

    private CalendarView CalendarView;

    FloatingActionButton fab;
    ArrayList<Item> items = new ArrayList<>();
    CustomItemsAdapter aToDoAdaptor;
    ListView listView;
    EditText editText;
    ImageView imgTick;
    ImageView spkBtn;
    MenuItem deleteItems;
    MenuItem searchItem;
    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(com.todo.enigma.R.layout.activity_main);

        // Gets the calendar from the view
        CalendarView = findViewById(com.todo.enigma.R.id.CalendarPicker);
        fab = (FloatingActionButton) findViewById(R.id.fab);



        fab = (FloatingActionButton) findViewById(R.id.fab);


        //  Button markDayButton = findViewById(com.todo.enigma.R.id.markDayButton);
        // Button clearSelectedDayButton = findViewById(com.todo.enigma.R.id.clearSelectedDayButton);

      /*  markDayButton.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            Random random = new Random(System.currentTimeMillis());
            int style = random.nextInt(2);
            int daySelected = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, daySelected);

            switch (style) {
                case 0:
                    CalendarView.markCircleImage1(calendar.getTime());
                    break;
                case 1:
                    CalendarView.markCircleImage2(calendar.getTime());
                    break;
                default:
                    break;
            }
        });

        clearSelectedDayButton.setOnClickListener(v -> CalendarView.clearSelectedDay());
*/
        // Set listener, in this case, the same activity
        CalendarView.setCalendarListener(this);

        CalendarView.setShortWeekDays(false);

        CalendarView.showDateTitle(true);

        CalendarView.setDate(new Date());


    }

    public void onAddFull(View view) {
        Intent i = new Intent(MainActivity.this, NewItem.class);
        startActivity(i);
    }


    @Override
    public Date onDayClick(Date date) {

        Toast.makeText(this, "onDayClick: " + date, Toast.LENGTH_SHORT).show();

        return date;
    }

    @Override
    public Date onDayLongClick(Date date) {
        Toast.makeText(this, "onDayLongClick: " + date, Toast.LENGTH_SHORT).show();

        return date;
    }

    @Override
    public void onRightButtonClick() {
        Toast.makeText(this, "onRightButtonClick!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftButtonClick() {
        Toast.makeText(this, "onLeftButtonClick!", Toast.LENGTH_SHORT).show();
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 202 && resultCode == 200) {
            Item newItem;
            int position = data.getExtras().getInt("position");
            if (position >= 0) {
                System.out.println("MainActivity position " + position);
                newItem = aToDoAdaptor.getItem(position);
            } else {
                newItem = new Item();
            }
            newItem.subject = data.getExtras().getString("subject");
            String strDate = data.getExtras().getString("date");
            String strTime = data.getExtras().getString("time");

            if(!TextUtils.isEmpty(strDate) && TextUtils.isEmpty(strTime)) {
                newItem.dueDate = Utils.getDateFromString(strDate);
            } else if(!TextUtils.isEmpty(strDate)){
                newItem.dueDate = Utils.getDateAndTimeFromString(strDate + " " + strTime);
            }
            if(TextUtils.isEmpty(strDate)) {
                newItem.dueDate = null;
            }
            if(TextUtils.isEmpty(strTime)) {
                newItem.dueTime = null;
            }
            newItem.dueTime = strTime;
            newItem.priority = (Priority) data.getSerializableExtra("Priority");
            if(position == -1) {
                Log.w("MyApp", "position 0");
                aToDoAdaptor.add(newItem);
                items.add(newItem);
            }
            Collections.sort(aToDoAdaptor.original);
            Collections.sort(aToDoAdaptor.fitems);
            aToDoAdaptor.notifyDataSetChanged();
            DBUtils.writeOne(newItem);
        }
    }
    }
