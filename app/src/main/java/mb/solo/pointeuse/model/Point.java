package mb.solo.pointeuse.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "points")
public class Point {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String dateEntre;
    @DatabaseField
    private String dateSortie;
    @DatabaseField
    private int tempsTravail;
    @DatabaseField
    private String info;

    public Point() {
    }

    public Point(String dateEntre, String info) {
        this.dateEntre = dateEntre;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateEntre() {
        return dateEntre;
    }

    public void setDateEntre(String dateEntre) {
        this.dateEntre = dateEntre;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public int getTempsTravail() {
        return tempsTravail;
    }

    public void setTempsTravail(int tempsTravail) {
        this.tempsTravail = tempsTravail;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", dateEntre='" + dateEntre + '\'' +
                ", dateSortie='" + dateSortie + '\'' +
                ", tempsTravail=" + tempsTravail +
                ", info='" + info + '\'' +
                '}';
    }
}
