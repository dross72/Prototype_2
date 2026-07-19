
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class MasterProductList {
    private String filePath;
    //Made a constructor in case the file ever changes
    public MasterProductList(String filePath){
        this.filePath = filePath;
    }
    //All the fields Reps can see
//    productId column 1;
//    description column 2;
//    brandName column 4;
//    ContainerName column 7;
    //The field that decides which product is restricted
    // productStatus column 0;

    //print the fields of the Master Product List that Reps are allowed to view
     public void printRepsProductList() {
        String filePath = "Master Product List_110923.xlsx"; // Change to your file path

        try {
            readExcelFile(filePath);
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
        }
    }

    public static void readExcelFile(String filePath) throws IOException {
    File file = new File(filePath);

    if (!file.exists() || !file.isFile()) {
        throw new IOException("File not found: " + filePath);
    }

    // Choose which cell indexes you want to print
    Set<Integer> allowedColumns = Set.of(0, 1, 2, 4, 7);

    try (FileInputStream fis = new FileInputStream(file);
         Workbook workbook = createWorkbook(fis, filePath)) {

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            System.out.println("Reading Sheet: " + sheet.getSheetName());

            for (Row row : sheet) {
                String value = null;
                for (Cell cell : row) {

                    // Skip columns you don't want
                    if (!allowedColumns.contains(cell.getColumnIndex())) {
                        continue;
                    }

                    value = getCellValue(cell);

                    //check specifically for the Product Status cell
                    //skip restricted columns and skip printing the status if active
                    if (cell.getColumnIndex() == 0) {
                        if ("Restricted".equals(value)) {
                            break;
                        } else if ("Active".equals(value)) {
                            continue;
                        }
                    }


                    System.out.print(value + "\t");
                }

                //stop this loop before it makes an empty space for restricted products
                if ("Restricted".equals(value)) {
                    continue;
                }
                System.out.println();
            }
        }
    }
}

    // Create correct Workbook type based on file extension
    private static Workbook createWorkbook(FileInputStream fis, String filePath) throws IOException {
        if (filePath.toLowerCase().endsWith(".xlsx")) {
            return new XSSFWorkbook(fis);
        } else if (filePath.toLowerCase().endsWith(".xls")) {
            return new HSSFWorkbook(fis);
        } else {
            throw new IOException("Unsupported file type: " + filePath);
        }
    }

    // Convert cell value to String safely
    private static String getCellValue(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    }


