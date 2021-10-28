package AutomationTest.BrightTalkTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static io.restassured.RestAssured.given;

public class RestUtils {
	static Response response;

	RestUtils() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
			public void checkClientTrusted(X509Certificate[] certs, String authType) { }
			public void checkServerTrusted(X509Certificate[] certs, String authType) { }

		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) { return true; }
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	public static Response getResponse(String parameter) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		RestUtils restUtils=new RestUtils();
    	RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://reqres.in/api").build();
    	response =given().spec(reqSpec).when().get(parameter).then().extract().response();
		return response;
    }

    public static Response getQueryResponse(String paraName, String paraValue) throws NoSuchAlgorithmException, KeyManagementException {
		RestUtils restUtils=new RestUtils();
    	RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://reqres.in/api/users").build();
    	response =given().spec(reqSpec).queryParam(paraName,paraValue).when().get().then().extract().response();
		return response;
    }
    
    public static Response postResponse(String postBody, String postURL){
    	RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://reqres.in/api/").build();
    	response =given().spec(reqSpec).contentType(ContentType.JSON)
                .body(postBody).post(postURL).then().extract().response();
		return response;
    }
}
