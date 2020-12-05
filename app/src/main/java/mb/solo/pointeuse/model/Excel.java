package mb.solo.pointeuse.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mb.solo.pointeuse.HistoActivity;

public class Excel {
    //https://github.com/andruhon/android5xlsx
    private List<Point> data;
    private String patternFormat = "dd/MM/yyyy kk:mm";
    private String patternFormatFichier = "dd_MM_yyyy_kk_mm_ss";
    private SimpleDateFormat myFormat = new SimpleDateFormat(patternFormat);
    private SimpleDateFormat myFormatFichier = new SimpleDateFormat(patternFormatFichier);
    public Excel(List<Point> data) {
        this.data = data;
    }

    public List<Point> getData() {
        return data;
    }

    public void setData(List<Point> data) {
        this.data = data;
    }

    public File makeFile(Context ctx){
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("page 1");
        int num = 0;
        for (Point point : data) {
            Row row = sheet.createRow(num);
            Cell cell = row.createCell(0);
            cell.setCellValue(myFormat.format(point.getDateEntre()));
            row.createCell(1).setCellValue(myFormat.format(point.getDateSortie()));
            row.createCell(2).setCellValue(Point.formatDiff(point));
            row.createCell(3).setCellValue(point.getInfo());
            num++;
        }
        Date dateNow = Calendar.getInstance().getTime();
        String titre = myFormatFichier.format(dateNow)+".xls";
        File file = new File(ctx.getExternalFilesDir(null), titre);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
            Log.i("orm", "Création du fichier "+titre);
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
