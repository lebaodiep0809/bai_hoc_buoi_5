package feature;

import Utils.ExcelUtils;
import action.LoginAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Map;

public class ActionLoginPageTest {
    private static final String EXCEL_PATH = "dataLogin.xlsx";
    private static final String SHEET_NAME = "Sheet1";
    private static final String LOGIN_URL = "https://www.saucedemo.com/";
    private static final String USER_NAME = "Username";
    private static final String PASS_WORD = "Password";

    public static void main() {
        List<Map<String, String>> excelData = ExcelUtils.readExcelData(EXCEL_PATH,SHEET_NAME);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        try {
            for (Map<String, String> rowData : excelData){
                driver.get(LOGIN_URL);
                LoginAction.performLogin(
                        driver,
                        rowData.get(USER_NAME),
                        rowData.get(PASS_WORD)
                );
                System.out.println("Da thuc hien login cho usser: "+rowData.get(USER_NAME));
            }
        }
        finally {
            driver.quit();
        }
    }
}
