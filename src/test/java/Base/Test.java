package Base;

import io.restassured.response.Response;
import jdk.jfr.Description;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Test extends BasePage {
    public static List<String> BySearch() {

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

    }

    public static void ByIdOrTitle(String imdbId) {
        Response response = given()
                .param("apikey", "d25400cc")
                .param("i", imdbId)
                .when()
                .log().all().get().prettyPeek()
                .then()
                .statusCode(200).extract().response();
    }

    @org.junit.Test
    @Description("Testing BySearch adn ByIdOrTitle")
    public void TestScenario() {
        System.out.println("By Search");
        List<String> List = BySearch();

        System.out.println("By Id Or Title");
        ByIdOrTitle(List.get(1));
    }
}
