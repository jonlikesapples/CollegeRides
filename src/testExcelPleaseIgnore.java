import java.awt.*;
import java.io.File;
import java.util.Date;
import jxl.*;
import jxl.format.*;
import jxl.write.*;
import jxl.write.Label;
import jxl.format.Colour;

/**
 * Created by JonWong on 10/24/16.
 */
public class testExcelPleaseIgnore {
    public static void main(String[] args)
    {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File("excel.xls"));

            WritableSheet sheet = workbook.createSheet("First Sheet", 0); //create sheet in the first position


            String[] stringArray = {"a", "b", "c", "d"};
            Label label;
            for (int i = 0; i < stringArray.length; i++)
            {
                label = new Label (i,0, stringArray[i]);
                sheet.addCell(label);
            }

            WritableCell c = sheet.getWritableCell(0,0);
            WritableCellFormat newFormat = new WritableCellFormat();
            newFormat.setBackground(Colour.YELLOW);
            c.setCellFormat(newFormat);

            WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true);
            WritableCellFormat boldArial10font = new WritableCellFormat(boldFont);
            Label boldLabel = new Label(0,1, "this label should be bold", boldArial10font);

            sheet.addCell(boldLabel);

            System.out.print("end of code");
            workbook.write();
            workbook.close();
        }
        catch (Exception E)
        {
            System.out.println(E.toString());
        }

    }
}
