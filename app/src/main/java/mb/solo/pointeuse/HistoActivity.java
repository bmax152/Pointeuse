package mb.solo.pointeuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histo);

        data = dao.list();

        /*Date d2 = Calendar.getInstance().getTime();
        Date d1 = addDay(d2, -1);

        data = dao.listDate(d1, d2);*/
        makeList();
    }

    /*public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }*/

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
            holder.truc.setText(point.getDateEntre().toString());
            if(point.getDateSortie() == null){
                holder.truc2.setText("En cour...");
                holder.truc3.setText(Point.formatDiff(point.getDateEntre(), Calendar.getInstance().getTime()));
            }else{
                holder.truc2.setText(point.getDateSortie().toString());
                holder.truc3.setText(Point.formatDiff(point));
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

        public MyListViewHolder(@NonNull View itemView) {
            super(itemView);
            truc = itemView.findViewById(R.id.textView2);
            truc2 = itemView.findViewById(R.id.textView3);
            truc3 = itemView.findViewById(R.id.textView);
        }
    }
}