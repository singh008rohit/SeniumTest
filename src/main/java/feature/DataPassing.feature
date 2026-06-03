Feature: Different way to pass data in cucumber feature file

  @dataPassing
  Scenario: passing data with parameter
    Given Login to application with "username" and "password"
    Then Login witha welcome message "Hi Rohit welcome back"

  Scenario: pasing data with data table as list
    Given Login user has access of different department
      | admin |
      | user  |
      | super |
    Then I will get list of user

  Scenario: Passing data with data table as Map
    Given User username and there department
      | rohit | QA    |
      | jhon  | actor |
    Then Featch all user with name and department

  Scenario: passing data with datatable as list of map
    Given user name with passsword and there expiry
      | name  | password | expiry |
      | Amit  |   123425 |   2023 |
      | Sneha |     5430 |   2025 |
      | Ravi  |     5428 |   2345 |
