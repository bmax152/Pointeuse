package mb.solo.pointeuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mb.solo.pointeuse.model.Point;
import mb.solo.pointeuse.orm.PointDao;

public class MainActivity extends BaseActivity {

    private static final String TAG = "orm";
    private PointDao dao = new PointDao(this);
    private Point item = null;
    private String patternFormat = "dd/MM/yyyy kk:mm";
    private SimpleDateFormat myFormat = new SimpleDateFormat(patternFormat);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPoint = findViewById(R.id.btnPoint);

        showLast();
        btnPoint.setOnClickListener(v ->{
            createPoint();
        });

        Button b = findViewById(R.id.btn);
        b.setOnClickListener(v ->{
            try {
                Date date1 = myFormat.parse("02/06/2020 08:30");
                Date date2 = myFormat.parse("02/06/2020 12:00");
                //long diffMinu = (date2.getTime() - date1.getTime())/(1000*60);
                //TODO: erreur de virgule 3.0 au lieu de 3.5
                float diffHour = ((date2.getTime() - date1.getTime())/(1000*60))/60;
                Log.i(TAG, diffHour+"");

            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }

    private void showLast() {
        item = dao.lastEntre();
        TextView tvLast = findViewById(R.id.tvLast);
        TextView tvPoint = findViewById(R.id.tvPoint);
        Button btnPoint = findViewById(R.id.btnPoint);
        if(item != null){
            tvLast.setText(R.string.tvLast_en_cour);
            tvPoint.setText(item.getDateEntre());
            btnPoint.setText(R.string.btn_end_point);
        }else{
            tvLast.setText(R.string.tvLast_default);
            tvPoint.setText("");
            btnPoint.setText(R.string.btn_add_point);
        }
    }

    private void createPoint(){

        Date dateNow = Calendar.getInstance().getTime();
        String date = myFormat.format(dateNow);
        if(item == null){
            Point point = new Point(date, "");
            Log.i(TAG, point.toString());
            dao.create(point);
        }else{
            item.setDateSortie(date);
            Log.i(TAG, item.toString());

            try {
                Date date1 = myFormat.parse(item.getDateEntre());
                Date date2 = myFormat.parse(item.getDateSortie());
                long diffHour = ((date2.getTime() - date1.getTime())/(1000*60))/60;

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //
            dao.update(item);
        }
        showLast();
    }
}