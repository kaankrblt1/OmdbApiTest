package Base;

import io.restassured.response.Response;
import jdk.jfr.Description;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertNotEquals;

public class Test extends BasePage {
    public static List<String> BySearch() {
        try {
            Response response = given()
                    .param("apikey", "d25400cc")
                    .param("s", "Harry Potter")
                    .when()
                    .log().all().get().prettyPeek()
                    .then()
                    .statusCode(200).extract().response();
            int titleSize = Integer.parseInt(response.jsonPath().getString("Search.Title.size()"));
            int size = 0;
            String imdbId = null;
            String title = null;
            List<String> SorcererSizeList = new ArrayList<>();

            while (size < titleSize) {
                title = response.jsonPath().getString("Search.Title[" + size + "]");
                if (title.contains("Harry Potter and the Sorcerer's Stone")) {

                    imdbId = response.jsonPath().getString("Search.imdbID[" + size + "]");
                    break;
                }
                size++;
            }

            SorcererSizeList.add(title);
            SorcererSizeList.add(imdbId);

            return SorcererSizeList;
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
            return null;
        }
    }

    public static void ByIdorTitle(String imdbId) {
        try {
            Response response = given()
                    .param("apikey", "d25400cc")
                    .param("i", imdbId)
                    .when()
                    .log().all().get().prettyPeek()
                    .then()
                    .statusCode(200)
                    .body("Title", not(emptyOrNullString()))
                    .body("Year", not(emptyOrNullString()))
                    .body("Released", not(emptyOrNullString()))
                    .extract().response();
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
        }
    }

    @org.junit.Test
    @Description("Testing BySearch and ByIDorTitle")
    public void TestScenario() {
        System.out.println("---By Search---");
        List<String> List = BySearch();

        System.out.println("---By Id Or Title---");
        ByIdorTitle(List.get(1));
    }

    @org.junit.Test
    @Description("Testing imdbId is Null")
    public void listIsNullScenario() {
        List<String> List = BySearch();

        assertNotEquals(0, List.size());
    }
}
