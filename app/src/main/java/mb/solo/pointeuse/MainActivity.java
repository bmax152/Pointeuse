package mb.solo.pointeuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import mb.solo.pointeuse.model.Chrono;
import mb.solo.pointeuse.model.Point;
import mb.solo.pointeuse.orm.PointDao;

public class MainActivity extends BaseActivity {

    private static final String TAG = "orm";
    private PointDao dao = new PointDao(this);
    private Point item = null;
    private String patternFormat = "dd/MM/yyyy kk:mm";
    private SimpleDateFormat myFormat = new SimpleDateFormat(patternFormat);
    private Timer timer = new Timer();
    private Handler mHandler = new Handler();
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPoint = findViewById(R.id.btnPoint);

        showLast();
        btnPoint.setOnClickListener(v ->{
            createPoint();
        });

        //Pour les tests
        Button b = findViewById(R.id.btn);
        b.setOnClickListener(v ->{
            /*try {
                Date date1 = myFormat.parse("02/06/2020 08:30");
                Date date2 = myFormat.parse("04/06/2020 08:30");
                String test = Point.formatDiff(date1, date2);
                Log.i(TAG, test );

            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            //Log.i(TAG, dao.list().toString() );
            /*TextView tv = findViewById(R.id.tvChrono);
            Timer timer = new Timer();
            TimerTask task = new Chrono(item, tv, mHandler);
            timer.schedule(task, 1, 60000);*/
        });
    }

    private void showLast() {
        item = dao.lastEntre();
        TextView tvLast = findViewById(R.id.tvLast);
        TextView tvPoint = findViewById(R.id.tvPoint);
        Button btnPoint = findViewById(R.id.btnPoint);
        EditText info = findViewById(R.id.edtInfo);
        TextView tv = findViewById(R.id.tvChrono);
        if(item != null){
            tvLast.setText(R.string.tvLast_en_cour);
            String date = myFormat.format(item.getDateEntre());
            tvPoint.setText(date);
            info.setEnabled(false);
            btnPoint.setText(R.string.btn_end_point);
            task = new Chrono(item, tv, mHandler);
            timer.schedule(task, 1, 60000);
        }else{
            tvLast.setText(R.string.tvLast_default);
            tvPoint.setText("");
            btnPoint.setText(R.string.btn_add_point);
            tv.setText("");
            //Pour ne pas interférer avec le lancement de l'app
            if(task!=null){
                task.cancel();
            }
        }
    }

    private void createPoint(){

        Date dateNow = Calendar.getInstance().getTime();
        EditText info = findViewById(R.id.edtInfo);
        if(item == null){
            Point point = new Point(dateNow, info.getText().toString());
            info.setText("");
            info.setEnabled(false);
            Log.i(TAG, point.toString());
            dao.create(point);
        }else{
            item.setDateSortie(dateNow);
            info.setEnabled(true);
            Log.i(TAG, item.toString());
            dao.update(item);
        }
        showLast();
    }
}