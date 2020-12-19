package mb.solo.pointeuse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mb.solo.pointeuse.model.Point;

public class CreateOrUpdateActivity extends AppCompatActivity {

    Point element;
    EditText edtDate1;
    EditText edtDate2;
    DatePickerDialog picker;
    TimePickerDialog pickerT;
    String txtPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update);

        element = (Point)getIntent().getSerializableExtra("updatePoint");

        //Log.i("orm", String.valueOf(element));
        //https://www.tutorialspoint.com/how-to-use-date-time-picker-in-android

        makePicker();
    }

    private void makePicker(){
        edtDate1 = findViewById(R.id.edt_date_entree);
        edtDate2 = findViewById(R.id.edt_date_sortit);

        prepareEdt(edtDate1);
        prepareEdt(edtDate2);
    }

    private void prepareEdt(EditText edt){
        edt.setInputType(InputType.TYPE_NULL);
        edt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int hour = cldr.get(Calendar.HOUR);
                int min = cldr.get(Calendar.MINUTE);

                // date picker dialog
                picker = new DatePickerDialog(CreateOrUpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtPoint = (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                pickerT = new TimePickerDialog(CreateOrUpdateActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                txtPoint += " " + hourOfDay + ":" + minute;
                                                edt.setText(txtPoint);
                                            }
                                        }, hour, min, true
                                );
                                pickerT.show();
                            }
                        }, year, month, day
                );
                picker.show();
            }
        });
    }
}
/*
* @Override
   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
      myHour = hourOfDay;
      myMinute = minute;
      textView.setText("Year: " + myYear + "\n" +
         "Month: " + myMonth + "\n" +
         "Day: " + myday + "\n" +
         "Hour: " + myHour + "\n" +
         "Minute: " + myMinute);
   }*/