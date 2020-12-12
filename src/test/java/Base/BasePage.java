package Base;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

public class BasePage {

    @Before
    public void setUp(){
        RestAssured.baseURI="http://www.omdbapi.com";
    }

    @After
    public void AfterClass(){
        System.out.println("İşlem Başarılı");
    }

}
