#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@smoke
Feature: Validate able to login to application with valid user
  I want to use this template for my feature file
Background:
  Given user navigates to login page
  @test5
  Scenario Outline: Login with valid user and invalid password
    Given valid user and valid password
    When enter user name "<User>" and password "<Password>"
    Then login to application successfully
    Given try with user details
      | name   | rohit |
      | age    |    21 |
      | gender | male  |
   # Given I load test data from "userProfile.json"

    Examples: 
      | User  | Password |
      | user1 |     1234 |
      | user2 |     4567 |
      | user3 |     8910 |

  @test6
  Scenario: validating different why to pass data in test cases
    Given try with user details
      | name   | rohit |
      | age    |    21 |
      | gender | male  |
    #Given I load test data from "userProfile.json"
    When Open my laptop "laptop"
      When I filter products by category Books
