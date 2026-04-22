package Bai_hoc;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginPageTest {
    private static final String EXCEL_PATH = "dataLogin.xlsx";
    private static final String SHEET_NAME = "Sheet1";
    private static final String LOGIN_URL = "https://www.saucedemo.com/";

    public static void main(String[] args) {
        List<Map<String, String>> testData = readExcelData(EXCEL_PATH, SHEET_NAME);
        WebDriver driver;
        driver = new ChromeDriver();
        for (Map<String, String> row : testData) {

            String user = row.get("Username");
            String pass = row.get("Password");

            System.out.println("Đang thử đăng nhập với User: " + user);
            driver.get(LOGIN_URL);

//            WebElement userField = driver.findElement(By.id("user-name"));
//            WebElement passField = driver.findElement(By.id("password"));
//            WebElement loginBtn = driver.findElement(By.id("login-button"));
//
//            userField.sendKeys(user);
//            passField.sendKeys(pass);
//            loginBtn.click();
//            driver.quit();

        }
    }

    /**
     * Đọc dữ liệu từ file Excel và trả về danh sách Map (Header -> Value)
     */
    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Không tìm thấy sheet: " + sheetName);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return data;

            int colCount = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowMap = new HashMap<>();
                boolean isEmptyRow = true;

                for (int j = 0; j < colCount; j++) {
                    String header = formatter.formatCellValue(headerRow.getCell(j));
                    String value = formatter.formatCellValue(row.getCell(j)).trim();

                    if (!value.isEmpty()) isEmptyRow = false;
                    rowMap.put(header, value);
                }

                if (!isEmptyRow) data.add(rowMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
