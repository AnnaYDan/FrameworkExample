Feature: My First Cucumber Test

  Scenario: Test login page with correct credentials
    Given I am on travel portal homepage "http://newtours.demoaut.com/"
    When I type username "a" and password "a"
    And click sign-in button
    Then link "SIGN-OFF" should be displayed