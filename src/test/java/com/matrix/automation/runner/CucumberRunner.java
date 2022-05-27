package com.matrix.automation.runner;

import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;
import com.matrix.automation.factory.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.cucumber.core.cli.Main;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CucumberRunner {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CucumberRunner.class);

    private static final String ABS_PATH = new File("").getAbsolutePath();
    private static final String REPORTS_DIRECTORY = "build/reports/";
    private static final String REPORTS_DIR = ABS_PATH + File.separator + REPORTS_DIRECTORY;
    private static final String SCREENSHOTS_DIR = REPORTS_DIR + "/screenshots";
    private static final String JSON_REPORT_FILE = "build/reports/cucumber-report.json";
    private static final String JSON_USAGE_REPORT_FILE = "build/reports/cucumber-usage-report.json";
    private static final String ZIPS_PATH = ABS_PATH + "/zips/";
    private static String scenarioId = "";

    private AppiumDriver driver;
    protected static BaseTest test;

    public static void main(String[] args) throws Throwable {
        // Before test execution clean and creates json report files
        LOGGER.info("CucumberRunner main ==>> args[1]: {}", args[1]);
        createReportsDirectory();
        createReportFile(JSON_REPORT_FILE);
        createReportFile(JSON_USAGE_REPORT_FILE);

        // Obtains the id's of the tests to execute @tag
        scenarioId = args[args.length - 1].replaceAll("@", "");
        LOGGER.info("CucumberRunner main ==>> scenarioId1: {}", scenarioId);
        if (scenarioId != null && !scenarioId.equals("")) {
            scenarioId = scenarioId.replaceAll("@", "");
            System.setProperty("scenarioId", scenarioId);
        }
        LOGGER.info("CucumberRunner main ==>> scenarioId2: {}", scenarioId);

        // Execute Cucumber
        Main.run(args, Thread.currentThread().getContextClassLoader());

        // Cucumber reports
        File reportOutputDirectory = new File(REPORTS_DIRECTORY);
        List<String> jsonFiles = new ArrayList<String>();
        jsonFiles.add(JSON_REPORT_FILE);

        // Cucumber Reports on Jenkins
        String buildNumber = "1";
        String projectName = "Matrix Automation Tests";
        boolean runWithJenkins = true;
        boolean parallelTesting = false;

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);
        // addidtional metadata presented on main page
        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "chrome");
        configuration.addClassifications("Branch", "release/1.0");

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        Reportable result = reportBuilder.generateReports();
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(REPORTS_DIR + "cucumber-pretty.txt", true));
            out.newLine();
            out.write(result.getScenarios() + " Scenarios (" + result.getPassedScenarios() + " passed, " + result.getFailedScenarios() + " failed)");
            out.newLine();
            out.write(result.getSteps() + " Steps (" + result.getFailedSteps() + " failed, " + result.getSkippedSteps() + " skipped, " + result.getPassedSteps() + " passed)");
            out.newLine();
            out.write("Duration: " + result.getFormattedDuration());
        } catch (IOException e) {
            // error processing code
            LOGGER.info("Error to write results: {}", e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        LOGGER.info("++++++++++++++++++ FIN ++++++++++++++");
    }

    private static void createReports(String jsonFile) {
        try {
            LOGGER.info("CucumberRunner createReports ===>>> jsonFile: {}", jsonFile);

            if (JSON_REPORT_FILE.equals(jsonFile)) {
                CucumberResultsOverview resultsOverview = new CucumberResultsOverview();
                resultsOverview.setOutputDirectory(REPORTS_DIRECTORY);
                resultsOverview.setOutputName("cucumber-results");
                resultsOverview.setSourceFile(jsonFile);
                resultsOverview.execute();

                CucumberDetailedResults detailedResults = new CucumberDetailedResults();
                detailedResults.setOutputDirectory(REPORTS_DIRECTORY);
                detailedResults.setOutputName("cucumber-detailed");
                detailedResults.setSourceFile(jsonFile);
                detailedResults.setScreenShotLocation("screenshots/");
                detailedResults.execute();
            } else {
                CucumberUsageReporting usageReport = new CucumberUsageReporting();
                usageReport.setOutputDirectory(REPORTS_DIRECTORY);
                usageReport.setJsonUsageFile(jsonFile);
                usageReport.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createReportsDirectory() {
        try {
            LOGGER.info("CucumberRunner ===>>> createReportsDirectory");
            File repDir = new File(REPORTS_DIR);
            if (repDir.exists()) {
                FileUtils.deleteDirectory(repDir);
                Thread.sleep(2000);
            }
            repDir = new File(REPORTS_DIR);
            repDir.mkdirs();
            assignPermissions(repDir);

            File screenshotsDir = new File(SCREENSHOTS_DIR);
            screenshotsDir.mkdirs();
            assignPermissions(screenshotsDir);

            // zips directory
            File zipsDir = new File(ZIPS_PATH);
            if (!zipsDir.exists()) {
                zipsDir = new File(ZIPS_PATH);
                zipsDir.mkdirs();
                assignPermissions(zipsDir);
            }
        } catch (Exception e) {
            LOGGER.info("Error to delete/create reports and zips directory: {}", e.getMessage());
        }
    }

    private static void assignPermissions(File file) {
        file.setExecutable(true, true);
        file.setWritable(true, true);
        file.setReadable(true, true);
    }

    private static void createReportFile(String jsonFile) {
        LOGGER.info("CucumberRunner, createReportFile ===>>> jsonFile: {}", jsonFile);
        String str1 = ABS_PATH + "/" + jsonFile;
        String str2 = str1.replaceAll("\\\\+", "/");
        File report = new File(str2);
        try {
            if (report.exists()) {
                String repPath = report.getAbsolutePath();
                report.delete();
                Thread.sleep(5000);
                report = new File(repPath);
            }
            report.createNewFile();
            assignPermissions(report);
        } catch (final Exception e) {
            LOGGER.info("Error to create report file: {}, {}", report, e.getMessage());
        }
    }
}