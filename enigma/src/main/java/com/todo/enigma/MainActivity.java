
package com.todo.enigma;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;


import com.todo.calendar.CalendarView;
import com.todo.calendar.CalendarView.CalendarListener;
import com.todo.calendar.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;



/**
 * Sample Activity
 *
 * @author Marco Hernaiz Cao
 */
public class MainActivity extends AppCompatActivity implements {

    private CalendarView CalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView

        // Gets the calendar from the view
        CalendarView = findViewById(com.todo.enigma.R.id.CalendarPicker);
        Button markDayButton = findViewById(com.todo.enigma.R.id.markDayButton);
        Button clearSelectedDayButton = findViewById(com.todo.enigma.R.id.clearSelectedDayButton);

        markDayButton.setOnClickListener(view -> {
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

        // Set listener, in this case, the same activity
        CalendarView.setCalendarListener(this);

        CalendarView.setShortWeekDays(false);

        CalendarView.showDateTitle(true);

        CalendarView.setDate(new Date());
    }


    @Override
    public void onDayClick(Date date) {
        Toast.makeText(this, "onDayClick: " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDayLongClick(Date date) {
        Toast.makeText(this, "onDayLongClick: " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightButtonClick() {
        Toast.makeText(this, "onRightButtonClick!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftButtonClick() {
        Toast.makeText(this, "onLeftButtonClick!", Toast.LENGTH_SHORT).show();
    }

}
