package mb.solo.pointeuse.model;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class Chrono extends TimerTask {

    private Point item;
    private TextView tv;
    private Date dateNow;
    @Override
    public void run() {
        dateNow = Calendar.getInstance().getTime();
        //tv.setText(Point.formatDiff(item.getDateEntre(), dateNow));
        Log.i("orm", (Point.formatDiff(item.getDateEntre(), dateNow)));
    }

    public Chrono(Point item, TextView tv){
        this.tv = tv;
        this.item = item;
    }
}
