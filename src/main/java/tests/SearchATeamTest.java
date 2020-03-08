package tests;

import infra.pages.HomePage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import infra.pages.utils.AssertUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SearchATeamTest extends BaseTest {

    private static HomePage homePage;
    private static boolean isSearched;

    @Test(dataProvider = "csvParamsProvider")
    public void search(String searchTerm) {
        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);

        report.startLevel("2. At the search area, enter " + searchTerm + " and click the search button");
//        Search terms:
//        - barcelona (single word scenario)
//        - real madrid (more than a single worn scenario)
//        - italy
        isSearched = homePage.search(searchTerm);
        report.endLevel();

        // Verify you can enter a search term
        AssertUtils.assertTrue(isSearched, "searching " + searchTerm + " test passed", "Failed to search " + searchTerm, true);

        report.startLevel("3. Check search results");
        if (isSearched) {
            // Verify that for each term, the search term is shown at the URL right after to the "s="
            AssertUtils.assertTrue(homePage.validateSearchInURL(searchTerm), searchTerm + "' was found in url", "Fail to find " + searchTerm + " at the url", true);

            // Verify that for each term, the search term is shown at least 3 times at the search results
            AssertUtils.assertTrue(homePage.validateSearchByResults(searchTerm), searchTerm + " was found at the search results ", "Fail to find " + searchTerm + " at the search results", true);
        }
        report.endLevel();
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





