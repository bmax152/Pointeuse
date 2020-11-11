package mb.solo.pointeuse.orm;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import mb.solo.pointeuse.model.Point;

public class PointDao {
    private static final String TAG = "orm";
    private static Dao<Point, Integer> dao = null;

    public PointDao(Context context){
        if(dao == null){
            try {
                DbManager db = new DbManager(context);
                dao = db.getDao(Point.class);
            } catch (SQLException e) {
                Log.e(TAG, "PointDao - Error! ",e);
            }
        }
    }

    /*
    CRUD
     */
    public List<Point> list(){
        List<Point> items = new ArrayList<>();
        try{
            items = dao.queryBuilder().orderBy("dateEntre", false).query();
            Log.i(TAG, "PointDao - SelectAll - OK");
        }catch (SQLException e){
            Log.e(TAG, "PointDao - SelectAll - Error! ",e);
        }
        return items;
    }

    public Point find(int id){
        try {
            Log.i(TAG, "PointDao - Find OK");
            return dao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, "PointDao - Find-Error! ",e);
            return null;
        }
    }

    public void create(Point item){
        try {
            dao.create(item);
            Log.i(TAG, "PointDao - Create OK");
        } catch (SQLException e) {
            Log.e(TAG, "PointDao - Create - Error! ",e);
        }
    }

    public void update(Point item){
        try {
            dao.update(item);
            Log.i(TAG, "PointDao - Update OK");
        } catch (SQLException e) {
            Log.e(TAG, "PointDao - Update - Error! ",e);
        }
    }

    public void createOrUpdate(Point item){
        try {
            dao.createOrUpdate(item);
            Log.i(TAG, "PointDao - Create or Update OK");
        } catch (SQLException e) {
            Log.e(TAG, "PointDao - Create or Update - Error! ",e);
        }
    }

    public void delete(Point item){
        try {
            dao.delete(item);
            Log.i(TAG, "PointDao - Delete OK");
        } catch (SQLException e) {
            Log.e(TAG, "PointDao - Delete - Error! ",e);
        }
    }

    public void delete(int id) {
        Point item = find(id);
        if (item != null) {
            delete(item);
        }
    }

    /*public List<Point> find(Map<String, Object> params){
        List<Point> items = new ArrayList<>();
        try {
            items = dao.queryForFieldValues(params);
        } catch (SQLException e) {
            Log.e(TAG, "PointDao - FindMap - Error! ",e);
        }
        Collections.sort(items);
        return items;
    }*/

    public Point lastEntre(){
        Point item = null;
        try{
            item = dao.queryBuilder().orderBy("id", true).where()
                    .isNull("dateSortie")
                    .queryForFirst();
        }catch (SQLException e){
            Log.e(TAG, "PointDao - lastEntre - Error! ",e);
        }
        return item;
    }


}
