package TestScripts;

import Reporting.ExtentReportManager;
import Reporting.Setup;
import Utilities.property;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.restassured.RestAssured;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utilities.Constants;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static Reporting.ExtentReportManager.extentReports;
import static Reporting.Setup.extentTest;

public class BaseTest {
    protected StringWriter writer;
    protected PrintStream captor;
    protected ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    @BeforeSuite
    public void setUp() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }
//
//    @BeforeSuite
//    public void setUpSuite() {
//        ExtentReport.initialize();
//    }
//
//    @AfterSuite
//    public void afterSuite() throws IOException {
//        ExtentReport.report.flush();
//        File htmlFile = new File(Constants.EXTENTREPORTPATH);
//        Desktop.getDesktop().browse(htmlFile.toURI());
//    }
//    @BeforeMethod
//    public void setUp() {
//        RestAssured.baseURI = property.baseUri;
//        writer = new StringWriter();
//        captor = new PrintStream(new WriterOutputStream(writer), true);
//    }


//    protected void formatAPIAndLogInReport(String content) {
//        String prettyPrint = content.replace("\n", "<br>");
//        LogStatus.info("<pre>" + prettyPrint + "</pre>");
//    }

    public String generateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

//    public void writeRequestAndResponseInReport(String request, String response) {
//        LogStatus.info("---- Request ---");
//        formatAPIAndLogInReport(request);
//        LogStatus.info("---- Response ---");
//        formatAPIAndLogInReport(response);
//    }
    @BeforeMethod
    public void setUpMethod() {
        RestAssured.baseURI = property.baseUri;
        writer = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writer), true);
extentReports = ExtentReportManager.createInstance(Constants.EXTENTREPORTPATH, "Test API Automation Report", "Test ExecutionReport");
        // Start ExtentTest for the current test method
       // extentTest.set(ExtentManager.getExtentTest());
    }

//    @AfterMethod
//    public void tearDown() {
//        // Log captured output
//        String consoleOutput = writer.toString();
//        ExtentReportManager.logInfoDetails("Console Output:<br>" + consoleOutput);
//
//    }

    public void writeRequestAndResponseInReport(String request, String response) {
        ExtentReportManager.logInfoDetails("---- Request ---<br>" + request);
        ExtentReportManager.logInfoDetails("---- Response ---<br>" + response);
    }
}
