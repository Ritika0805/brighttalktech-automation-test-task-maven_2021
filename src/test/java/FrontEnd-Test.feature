Feature: Frontend test

  Scenario: Verify header
    Given I am on the "reqres.in" page
    And I should see page header with text "Test your front-end against a real API"

  Scenario: Click on upgrade
    Given I am on the "reqres.in" page
    And I should see Upgrade button
    When I click on Upgrade button
    Then I should see Email input
    And I should see Subscribe button
