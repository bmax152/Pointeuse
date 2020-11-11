package mb.solo.pointeuse.model;

import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import android.os.Handler;

public class Chrono extends TimerTask {

    private Point item;
    private TextView tv;
    private Date dateNow;
    private Handler mHandler;
    @Override
    public void run() {

        dateNow = Calendar.getInstance().getTime();
        String d = Point.formatDiff(item.getDateEntre(), dateNow);
        //https://gist.github.com/MaartenS/0ea266c7ba5028537329
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tv.setText(Point.formatDiff(item.getDateEntre(), dateNow));
            }
        });
    }

    public Chrono(Point item, TextView tv, Handler mHandler){
        this.tv = tv;
        this.item = item;
        this.mHandler = mHandler;
    }
}
