package AutomationTest.BrightTalkTest;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static AutomationTest.BrightTalkTest.BasePage.driver;
import static AutomationTest.BrightTalkTest.HomePage.homePage;
import static AutomationTest.BrightTalkTest.RestAssuredUtils.RESPONSE_BODY;
import static AutomationTest.BrightTalkTest.RestAssuredUtils.STATUS_CODE;

public class StepDefinition {
    ReqresPage reqresPage=new ReqresPage();
    static Response response;
    List<Integer> list= new ArrayList<>();;
    JSONParser jsonParser=new JSONParser();
    @Given("^I am on the home page$")
    public void iAmOnTheHomePage() {
        homePage();
    }

    @Given("^I get the default list of users for on (\\d+)st page$")
    public void i_get_the_default_list_of_users_for_on_st_page(String pageNumber) throws Throwable {
        response= RestUtils.getQueryResponse("page", pageNumber);
        Assert.assertEquals(response.getStatusCode(), 200);
        List<Integer> list1 = response.path("data.id");
        list.addAll(list1);
    }

    @When("^I get the list of all users$")
    public void i_get_the_list_of_all_users() throws Throwable {
        response= RestUtils.getQueryResponse("page", "2");
        Assert.assertEquals(response.getStatusCode(), 200);
        List<Integer> list2 = response.path("data.id");
        list.addAll(list2);
    }

    @Then("^I should see total users count equals to number of user ids$")
    public void i_should_see_total_users_count_equals_to_number_of_user_ids() throws Throwable {
        int count= response.path("total");
        Assert.assertEquals(count,list.size());
    }


    @Given("^I make a search for user (\\d+)$")
    public void i_make_a_search_for_user(String id) throws Throwable {
        response = RestUtils.getResponse("/users/" + id + "");
    }

    @Then("^I should see following user data$")
    public void i_should_see_following_user_data(DataTable userDetails) throws Throwable {
        Assert.assertEquals(response.getStatusCode(), 200);
        List<List<String>> data = userDetails.raw();
        Assert.assertEquals(data.get(0).get(0), response.path("data.first_name"));
        Assert.assertEquals(data.get(0).get(1), response.path("data.email"));
    }

    @Then("^I receive error code (\\d+) in response$")
    public void i_receive_error_code_in_response(int statusCode) throws Throwable {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }
    
    @Then("^response should contain folowing data$")
    public void response_should_contain_folowing_data(DataTable table) {

        List<List<String>> lists = new ArrayList<>();
        List<String> listHeaders = table.asLists(String.class).get(0);
        lists.add(listHeaders);
        jsonParser.jsonParsing(RESPONSE_BODY);
        String name = jsonParser.getValue(0,"name");
        List<String> names = jsonParser.getValue("name");
        for(String name1 : names) System.out.println("name is:" + name1);
        String job = jsonParser.getValue(0,"job");
        String id = jsonParser.getValue(0,"id");
        String createdAt = jsonParser.getValue(0,"createdAt");
        List<String> stringList = new ArrayList<>(Arrays.asList(name, job, id, createdAt));
        lists.add(stringList);
        DataTable dataTable = table.toTable(lists, listHeaders.get(0), listHeaders.get(1), listHeaders.get(2), listHeaders.get(3));
        System.out.println(dataTable.toString());
    }

    @Given("^I create user with following \"([^\"]*)\" \"([^\"]*)\"$")
    public void iCreateUserWithFollowing(String arg0, String arg1) throws Throwable {

        String jsonRequestBody = "{\"name\": \""+ arg0 + "\", \"job\": \"" + arg1 + "\"}";
        RestAssuredUtils.postHttp(jsonRequestBody,"/api/users", "POST");
        Assert.assertEquals(STATUS_CODE, "201");
        System.out.println("Response :" + RESPONSE_BODY);
    }

    @Given("^I login succesfully with following data$")
    public void i_login_successfully_with_following_data(DataTable table) {
        List<String> stringList = table.asLists(String.class).get(1);
        String jsonRequestBody = "{\"email\": \"" + stringList.get(0) + "\", \"password\": \"" + stringList.get(1) + "\"}";
        RestAssuredUtils.postHttp(jsonRequestBody,"/api/login", "POST");
        Assert.assertEquals(STATUS_CODE, "200");
        System.out.println("Response :" + RESPONSE_BODY);
    }

    @Given("^I login unsuccesfully with following data$")
    public void i_login_unsuccesfully_with_following_data(DataTable usercredentials) throws Throwable {
        List<List<String>> data = usercredentials.raw();
        String js = "{\n" + "  \"email\": \"" + data.get(0).get(0) + "\",\n" + "  \"password\": \"" + data.get(0).get(1)
                + "\"\n" + "}";
        Assert.assertEquals(RestUtils.postResponse(js, "login").getStatusCode(), 400);
    }

    @Given("^I wait for user list to load$")
    public void i_wait_for_user_list_to_load() {
        RestAssuredUtils.getHttp("/api/users?delay=3");
        Assert.assertEquals(STATUS_CODE, "200");
        System.out.println("Response Body :" + RESPONSE_BODY);
    }

    @Then("^I should see that every user has a unique id$")
    public void i_should_see_that_every_user_has_a_unique_id() {
        jsonParser.jsonParsing(RESPONSE_BODY);
        List<String> users = jsonParser.getValue("id");
        System.out.println("Number of users returned : " + users.size());
        Set<String> stringSet = new HashSet<>(users);
        Assert.assertEquals(stringSet.size(), users.size());
    }

    @Given("^I am on the \"([^\"]*)\" page$")
    public void iAmOnThePage(String arg0) throws Throwable {
        driver.get("https://reqres.in");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10000, TimeUnit.MILLISECONDS);
        System.out.println(driver.getTitle());
    }

    @And("^I should see page header with text \"([^\"]*)\"$")
    public void iShouldSeePageHeaderWithText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String s=reqresPage.getPageTitle();
        Assert.assertEquals(s,arg0);
    }

    @And("^I should see Upgrade button$")
    public void iShouldSeeUpgradeButton(){
        try{
            Boolean b=reqresPage.elementVisibleUpgradeButton();
            Assert.assertTrue(b);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    @When("^I click on Upgrade button$")
    public void iClickOnUpgradeButton() {
        reqresPage.clickOnUpgradeButton();
    }

    @Then("^I should see Email input$")
    public void iShouldSeeEmailInput() {
        Boolean b=reqresPage.elementVisibleEmail();
        Assert.assertTrue(b);
    }

    @And("^I should see Subscribe button$")
    public void iShouldSeeSubscribeButton()  {

        Boolean b=reqresPage.elementVisibleSubscribe();
        Assert.assertTrue(b);
    }
}
