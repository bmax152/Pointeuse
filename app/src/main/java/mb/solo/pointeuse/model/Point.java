package mb.solo.pointeuse.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.DateTimeType;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "points")
public class Point {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private Date dateEntre;
    @DatabaseField
    private Date dateSortie;
    @DatabaseField
    private long tempsTravail; //tmps en milliseconde
    @DatabaseField
    private String info;

    public Point() {
    }

    public Point(Date dateEntre, String info) {
        this.dateEntre = dateEntre;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateEntre() {
        return dateEntre;
    }

    public void setDateEntre(Date dateEntre) {
        this.dateEntre = dateEntre;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public long getTempsTravail() {
        return tempsTravail;
    }

    public void setTempsTravail(long tempsTravail) {
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

    /*
    * Renvois une difference de date au format: 00h00min
    * */

    static public String formatDiff(Date date1, Date date2){
        long diff = date2.getTime() - date1.getTime();
        long diffHour = diff/(3600000);
        long restMin  = diff%(3600000);
        long diffMin = restMin/60000;

        return diffHour + "h" + diffMin + "min";
    }
}
