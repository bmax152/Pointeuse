package mb.solo.pointeuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import mb.solo.pointeuse.model.Point;

public class CreateOrUpdateActivity extends AppCompatActivity {

    Point element;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update);

        element = (Point)getIntent().getSerializableExtra("updatePoint");

        //Log.i("orm", String.valueOf(element));
        //https://www.tutorialspoint.com/how-to-use-date-time-picker-in-android
    }
}