package AutomationTest.BrightTalkTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static AutomationTest.BrightTalkTest.HomePage.apiHomePage;
public class RestAssuredUtils {

        static String CONTENT_TYPE = "application/json";
        static String STATUS_CODE;
        static String REQUEST_BODY;
        static String RESPONSE_BODY;
        static Response response;

        public static void postHttp (String jsonRequest, String URI, String method) {
            RestAssured.baseURI = apiHomePage() + URI;
            RequestSpecification request = RestAssured.given();
            REQUEST_BODY = jsonRequest;
            request.header("Content-Type", CONTENT_TYPE);
            request.header("Accept", "*/*");
            request.body(REQUEST_BODY);
            if(method.equals("POST")) response = request.post();
            else response = request.put();
            STATUS_CODE = String.valueOf(response.getStatusCode());
            RESPONSE_BODY = response.getBody().asString();
        }

        public static void getHttp(String URI) {
            RestAssured.baseURI = apiHomePage() + URI;
            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", CONTENT_TYPE);
            response = request.get();
            STATUS_CODE = String.valueOf(response.getStatusCode());
            RESPONSE_BODY = response.getBody().asString();
        }

}
