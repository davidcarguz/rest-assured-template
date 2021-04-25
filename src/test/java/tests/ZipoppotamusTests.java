package tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ZipoppotamusTests {

    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void setUpRequestSpec(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://api.zippopotam.us/").
                build();
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200).
                build();
    }


    @Test
    public void validateCityFromZipCode(){

        String placeName =
        given().
                spec(requestSpec).
                pathParam("countryCode","us").
                pathParam("postalCode","90210").
        when().
                get("{countryCode}/{postalCode}").
        then().
                spec(responseSpec).
                extract().
                path("places[0].'place name'");

        Assert.assertEquals(placeName,"Beverly Hills");

    }

    @Test
    public void validateStatusCode(){
        given().
                spec(requestSpec).
                when().
                        get("us/90210").
                then().
                        assertThat().
                        statusCode(200);
    }

    @Test
    public void validateContentType(){
        given().
                spec(requestSpec).
                when().
                        get("us/90210").
                then().
                        spec(responseSpec);
    }
}
