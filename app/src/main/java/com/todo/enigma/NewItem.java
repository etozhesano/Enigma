package com.todo.enigma;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.todo.enigma.adapter.TimePickerFragment;
import com.todo.enigma.models.Priority;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.todo.calendar.CalendarView.currentCalendar;
import static com.todo.calendar.CalendarView.lastSelectedDayCalendar;

public class NewItem extends AppCompatActivity implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener {
    // DatePickerDialog.OnDateSetListener {
    private EditText etNewTask;
    private EditText timeTextView;
    private EditText dateTextView;
    private Spinner spinner;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_item);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Priority.values()));
        etNewTask = (EditText) findViewById(R.id.etNewTask);
        timeTextView = (EditText) findViewById(R.id.etDisplayTime);

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });
        dateTextView = (EditText) findViewById(R.id.etDisplayDate);
        ImageView timeButton = (ImageView) findViewById(R.id.imgTime);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        ImageView dateButton = (ImageView) findViewById(R.id.imgDate);
        Date date = (Date) getIntent().getSerializableExtra("date");
        String time = getIntent().getStringExtra("time");

if (lastSelectedDayCalendar == null) {
    Date testDate = currentCalendar.getTime();

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    String reportDate = format.format(testDate);
    dateTextView.setText(reportDate);
}
else {
    Date topDate = lastSelectedDayCalendar.getTime();
    SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy");
    String lastDay = form.format(topDate);
    dateTextView.setText(lastDay);
}
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        Drawable drawable = menu.findItem(R.id.newadd).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        }

        Drawable drawable1 = menu.findItem(R.id.shareNew).getIcon();
        if (drawable1 != null) {
            drawable1.mutate();
            drawable1.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        }
        return true;
    }


    public void onAddNewSaveClick(MenuItem item) {
        if (TextUtils.isEmpty(etNewTask.getText().toString().trim())) {
            Toast.makeText(NewItem.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etNewTask.getWindowToken(), 0);
            Intent data = new Intent();
            data.putExtra("subject", etNewTask.getText().toString());
            System.out.println("NewActivity position " + position);
            data.putExtra("position", position);
            data.putExtra("date", dateTextView.getText().toString());
            data.putExtra("time", timeTextView.getText().toString());
            Priority p = (Priority) spinner.getSelectedItem();
            data.putExtra("Priority", p);
            setResult(200, data);
            this.finish();
        }
    }

    /*  public void onDateSet(View view) {
          Calendar now = Calendar.getInstance();
          DatePickerDialog dpd = DatePickerDialog.newInstance(
                  NewItem.this,
                  now.get(Calendar.YEAR),
                  now.get(Calendar.MONTH),
                  now.get(Calendar.DAY_OF_MONTH)
          );
          dpd.show(getFragmentManager(), "Datepickerdialog");
      }
   */


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.etDisplayTime);
        textView.setText(hourOfDay +":" + minute);
    }



    public void clearTime(View view) {
        timeTextView.setText("");
    }


    public void onShareClick(MenuItem view) {
        if (!TextUtils.isEmpty(etNewTask.getText().toString())) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "";
            shareBody += etNewTask.getText().toString();
            if (!TextUtils.isEmpty(dateTextView.getText().toString())) {
                shareBody += " by " + dateTextView.getText().toString();
                if (!TextUtils.isEmpty(timeTextView.getText().toString())) {
                    shareBody += " " + timeTextView.getText().toString();
                }
            }
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ToDo task");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void backToDate(View view) {
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }

    @Override
    public void onClick(View v) {

    }

   /* @Override
    public void onClick(View v) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    } */
}