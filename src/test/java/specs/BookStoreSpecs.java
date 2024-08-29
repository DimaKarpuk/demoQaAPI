package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class BookStoreSpecs {
    public static RequestSpecification requestSpecificationWithContentTypeJson = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().all();
    public static RequestSpecification requestSpecificationWithContentTypeApplicationJson = with()
            .filter(withCustomTemplates())
            .contentType("application/json")
            .log().all();
    public static ResponseSpecification responseSpecificationWithStatusCode201 = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(201)
            .build();
    public static ResponseSpecification responseSpecificationWithStatusCode200 = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .build();
    public static ResponseSpecification responseSpecificationWithStatusCode204 = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification responseSpecificationWithStatusCode400 = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(400)
            .build();
}
