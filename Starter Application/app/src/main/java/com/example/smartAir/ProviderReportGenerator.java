package com.example.smartAir;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

public class ProviderReportGenerator {

    public static void generate(Context context, R6Repository repo) {

        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        paint.setTextSize(24);
        paint.setFakeBoldText(true);

        int y = 60;
        canvas.drawText("SmartAir - Provider Report", 50, y, paint);

        paint.setFakeBoldText(false);
        paint.setTextSize(16);
        y += 40;

        canvas.drawText("Today's Zone: " + repo.getTodayZone(), 50f, y, paint); y += 25;
        canvas.drawText("Last Rescue: " + repo.getLastRescueTime(), 50f, y, paint); y += 25;
        canvas.drawText("Weekly Rescue Count: " + repo.getWeeklyRescueCount(), 50f, y, paint); y += 25;

        canvas.drawText("Controller Adherence: " + repo.getControllerAdherencePercent() + "%", 50f, y, paint); y += 25;
        canvas.drawText("Symptom Burden Days: " + repo.getSymptomBurdenDays(), 50f, y, paint); y += 25;
        canvas.drawText("Triage Incidents: " + repo.getTriageIncidents(), 50f, y, paint); y += 40;

        canvas.drawText("Zone Distribution:", 50f, y, paint); y += 25;
        Map<String,Integer> dist = repo.getZoneDistribution();
        for (String key : dist.keySet()) {
            canvas.drawText(key + ": " + dist.get(key) + " days", 70f, y, paint);
            y += 20;
        }

        document.finishPage(page);

        try {
            File outFile = new File(context.getExternalFilesDir(null),
                    "provider_report_r6.pdf");
            FileOutputStream fos = new FileOutputStream(outFile);
            document.writeTo(fos);
            fos.close();

            Toast.makeText(context,
                    "Report saved: " + outFile.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error creating report", Toast.LENGTH_SHORT).show();
        } finally {
            document.close();
        }
    }
}
