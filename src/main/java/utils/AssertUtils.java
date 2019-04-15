package utils;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import il.co.topq.difido.model.Enums.Status;
import infra.pages.BasePage;
import org.testng.Assert;

public class AssertUtils {

    public static final String OURMATH_HOME_PAGE = "class infra.pages.HomePage";
    public static final String GOAL_OF_THE_MONTH_PAGE = "class infra.pages.GoalOfTheMonthPage";


    private static ReportDispatcher report = ReportManager.getInstance();

    public static void assertEquals(Object actual, Object expected, String message) {
        assertEquals(actual, expected, message, false);
    }

    public static void assertEquals(Object actual, Object expected, String message, boolean softAssert) {

        try {
            Assert.assertEquals(actual, expected, message);
            report.log(message, Status.success);
        } catch (AssertionError e) {

            report.logHtml(e.getMessage(), null, Status.failure);

            if (!softAssert) {
                throw e;
            }
        }
    }


    public static void assertTrue(boolean condition, String message) {
        assertTrue(condition, message, false);
    }

    public static void assertTrue(boolean condition, String message, boolean softAssert) {

        try {
            Assert.assertTrue(condition, message);
            report.log(message + " - OK");
        } catch (AssertionError e) {
            report.logHtml(e.getMessage(), null, Status.failure);
            if (!softAssert) {
                throw e;
            }
        }
    }

    public static void assertTrue(boolean condition, String successMsg, String failureMsg, boolean softAssert) {
        try {
            Assert.assertTrue(condition, successMsg);
        } catch (AssertionError e) {
            report.log(failureMsg, Status.failure);
            if (!softAssert) {
                throw e;
            }
        }
    }

    //TODO: fix this verification to other assertInPage
    public static void validatePage(BasePage basePage, String expectedPage) {
        String actualPageClassName = basePage.getClass().toString();
        if (actualPageClassName.equals(expectedPage)) {
            if (actualPageClassName.equals(OURMATH_HOME_PAGE)) {
                report.log("Our match home page was verified", Status.success);
            } else if (actualPageClassName.equals(GOAL_OF_THE_MONTH_PAGE)) {
                report.log("Goal of the month page was verified", Status.success);
            } else {
                report.log("Expected Page page was verified", Status.success);
            }
            Assert.assertTrue(true);
        } else {
            report.log("Unable to verify page", Status.failure);
            Assert.assertTrue(false);
        }

    }
}
