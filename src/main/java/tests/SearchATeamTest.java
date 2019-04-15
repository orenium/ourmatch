package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SearchATeamTest extends BaseTest {

   private HomePage homePage;


    @Test(dataProvider = "csvParamsProvider")
    public void search(String searchTerm) {
        boolean isSearched;
        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        report.startLevel("2. At the search area, enter " + searchTerm + " and click the search button");
//        Search terms:
//        - barcelona (single word scenario)
//        - real madrid (more than a single worn scenario)
//        - italy
        isSearched = homePage.search(searchTerm);
        report.endLevel();

        // Verify you can enter a search term
        Assert.assertTrue(isSearched, " failed to search " + searchTerm);
        if (isSearched) {
            // Verify that for each term, the search term is shown at the URL right after to the "s="
            Assert.assertTrue(homePage.validateSearchInURL(searchTerm), "fail to find " + searchTerm + " at the url");

            // Verify that for each term, the search term is shown at least 3 times at the search results
            Assert.assertTrue(homePage.validateSearchByResults(searchTerm), "fail to find " + searchTerm + " at the search results");
        }
    }


    @DataProvider(name = "csvParamsProvider")
    public Object[][] dataProvider3() throws Exception {

        FileInputStream fStream = new FileInputStream("src/main/resources/searchTerms.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(fStream));

        int numOfLines = 0;
        String line;

        ArrayList<String> searchItems = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            if (numOfLines > 0) {
                String[] splitStr = line.split(",");
                searchItems.add(splitStr[0]);
            }
            numOfLines++;
        }
        br.close();
        Object[][] params = new Object[numOfLines - 1][1];

        for (int i = 0; i < numOfLines - 1; i++) {
            params[i][0] = searchItems.get(i);
        }
        return params;
    }

}





