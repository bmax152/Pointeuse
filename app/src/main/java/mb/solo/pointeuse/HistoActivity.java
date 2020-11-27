package mb.solo.pointeuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mb.solo.pointeuse.model.Point;
import mb.solo.pointeuse.orm.PointDao;

public class HistoActivity extends AppCompatActivity {

    List<Point> data;
    PointDao dao = new PointDao(HistoActivity.this);
    RecyclerView list;
    MyListAdapter adapter;

    DatePickerDialog picker;
    Date date1;
    Date date2;
    EditText edtDate1;
    EditText edtDate2;
    Button btnSubmit;

    private String patternFormat = "dd/MM/yyyy kk:mm";
    private SimpleDateFormat myFormat = new SimpleDateFormat(patternFormat);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histo);

        data = dao.list();
        Log.i("orm", data.toString());
        /*Date d2 = Calendar.getInstance().getTime();
        Date d1 = addDay(d2, -1);

        data = dao.listDate(d1, d2);*/
        makeList();

        //https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
        makePicker();
    }

    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }

    //Gére les datePicker:
    private void makePicker(){
        edtDate1 = findViewById(R.id.edt_date1);
        edtDate2 = findViewById(R.id.edt_date2);
        btnSubmit = findViewById(R.id.btn_submit);
        prepareEdt(edtDate1);
        prepareEdt(edtDate2);

        btnSubmit.setOnClickListener(v->{
            String d1Txt = edtDate1.getText().toString();
            String d2Txt = edtDate2.getText().toString();

            if(!d1Txt.equals("") && !d2Txt.equals("")){
                date1 = new Date();
                date2 = new Date();
                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d1Txt);
                    date2 = new SimpleDateFormat("dd/MM/yyyy").parse(d2Txt);
                    date2 = addDay(date2, 1);
                    Log.i("orm", date1.toString());
                    Log.i("orm", date2.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                data =  dao.listDate(date1, date2);
                adapter.notifyDataSetChanged();
                Log.i("orm", data.toString());
            }
        });
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
                // date picker dialog
                picker = new DatePickerDialog(HistoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day
                );
                picker.show();
            }
        });
     }

    //Gére la RV
    private void makeList() {

        list = findViewById(R.id.rv_histo);

        list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyListAdapter();
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListViewHolder>{

        @NonNull
        @Override
        public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_histo_layout, parent, false);
            return new MyListViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
            Point point = data.get(position);
            String d1 = myFormat.format(point.getDateEntre());
            String d2 = "";
            holder.truc.setText(d1);
            if(point.getDateSortie() == null){
                holder.truc2.setText("En cour...");
                holder.truc3.setText(Point.formatDiff(point.getDateEntre(), Calendar.getInstance().getTime()));
            }else{
                d2 = myFormat.format(point.getDateSortie());
                holder.truc2.setText(d2);
                holder.truc3.setText(Point.formatDiff(point));
            }
            if(point.getInfo().equals("")){
                holder.truc4.setText("***");
            }else{
                holder.truc4.setText(point.getInfo());
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class MyListViewHolder extends RecyclerView.ViewHolder {

        TextView truc;
        TextView truc2;
        TextView truc3;
        TextView truc4;

        public MyListViewHolder(@NonNull View itemView) {
            super(itemView);
            truc = itemView.findViewById(R.id.textView2);
            truc2 = itemView.findViewById(R.id.textView3);
            truc3 = itemView.findViewById(R.id.textView);
            truc4 = itemView.findViewById(R.id.textView4);
        }
    }
}