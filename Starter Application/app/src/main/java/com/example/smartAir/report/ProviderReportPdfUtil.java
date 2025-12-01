package com.example.smartAir.report;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProviderReportPdfUtil {

    public static File generateReport(
            Context ctx,
            String childName,
            String windowLabel,
            double adherencePercent,
            int totalRescue,
            int problemDays,
            int[] zoneDistribution // [green, yellow, red]
    ) throws IOException {

        PdfDocument doc = new PdfDocument();
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = doc.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        Paint title = new Paint(Paint.ANTI_ALIAS_FLAG);
        title.setTextSize(20f);
        Paint body = new Paint(Paint.ANTI_ALIAS_FLAG);
        body.setTextSize(12f);
        Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        int x = 40;
        int y = 60;

        canvas.drawText("SMART AIR Provider Report", x, y, title);
        y += 30;
        canvas.drawText("Child: " + childName, x, y, body);
        y += 20;
        canvas.drawText("Window: " + windowLabel, x, y, body);
        y += 30;

        canvas.drawText("Rescue frequency: " + totalRescue + " uses", x, y, body);
        y += 20;
        canvas.drawText(String.format("Controller adherence: %.1f%%", adherencePercent), x, y, body);
        y += 20;
        canvas.drawText("Symptom burden (problem days): " + problemDays, x, y, body);
        y += 40;

        float baseY = y + 100;
        float barWidth = 60f;
        float gap = 20f;

        int total = Math.max(1, zoneDistribution[0] + zoneDistribution[1] + zoneDistribution[2]);

        for (int i = 0; i < 3; i++) {
            float fraction = zoneDistribution[i] / (float) total;
            float height = fraction * 100f;
            float left = x + i * (barWidth + gap);
            float top = baseY - height;
            float right = left + barWidth;
            canvas.drawRect(left, top, right, baseY, barPaint);
        }

        canvas.drawText("Zone distribution (Green / Yellow / Red)", x, baseY + 20, body);

        doc.finishPage(page);

        File out = new File(ctx.getCacheDir(), "provider_report.pdf");
        FileOutputStream fos = new FileOutputStream(out);
        doc.writeTo(fos);
        fos.close();
        doc.close();

        return out;
    }
}
