package mb.solo.pointeuse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mb.solo.pointeuse.model.Point;
import mb.solo.pointeuse.orm.PointDao;

public class CreateOrUpdateActivity extends AppCompatActivity {

    Point element;
    EditText edtDate1;
    EditText edtDate2;
    EditText info;
    DatePickerDialog picker;
    TimePickerDialog pickerT;
    String txtPoint;
    Button submit;

    private String patternFormat = "dd/MM/yyyy kk:mm";
    private SimpleDateFormat myFormat = new SimpleDateFormat(patternFormat);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update);
        edtDate1 = findViewById(R.id.edt_date_entree);
        edtDate2 = findViewById(R.id.edt_date_sortit);
        info = findViewById(R.id.edt_info_cor);
        element = (Point)getIntent().getSerializableExtra("updatePoint");

        //Log.i("orm", String.valueOf(element));
        //https://www.tutorialspoint.com/how-to-use-date-time-picker-in-android

        makePicker();

        makeSubmit();
    }

    private void makePicker(){
        if(element != null){
            edtDate1.setText(myFormat.format(element.getDateEntre()));
            edtDate2.setText(myFormat.format(element.getDateSortie()));
            info.setText(element.getInfo());
        }
        prepareEdt(edtDate1, true);
        prepareEdt(edtDate2, false);
    }

    private void prepareEdt(EditText edt, boolean startOrFinish){
        edt.setInputType(InputType.TYPE_NULL);
        edt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day;
                int month;
                int year;
                int hour;
                int min;
                Date choice;
                if(element != null){
                    if(startOrFinish){
                        choice = element.getDateEntre();
                    }else{
                        choice = element.getDateSortie();
                    }
                    cldr.setTime(choice);
                }
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                hour = cldr.get(Calendar.HOUR);
                min = cldr.get(Calendar.MINUTE);

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

    private void makeSubmit() {
        submit = findViewById(R.id.btn_submit_cor);
        submit.setOnClickListener(v -> {
            String date1Txt = edtDate1.getText().toString();
            String date2Txt = edtDate2.getText().toString();
            if(!date1Txt.equals("") && !date2Txt.equals("")){
                Date date1 = new Date();
                Date date2 = new Date();
                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date1Txt);
                    date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date2Txt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(date2.after(date1)){
                    if(element != null){
                        element.setDateEntre(date1);
                        element.setDateSortie(date2);
                        element.setInfo(info.getText().toString());
                    }else{
                        Point point = new Point(date1, info.getText().toString());
                        point.setDateSortie(date2);
                        element = point;
                    }
                    PointDao dao = new PointDao(CreateOrUpdateActivity.this);
                    dao.createOrUpdate(element);
                }
            }
        });
    }
}
