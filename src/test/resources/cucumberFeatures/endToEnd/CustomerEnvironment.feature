@All
@EndToEndCustomerEnvironment
#  It is supposed that all the scenarios are being done one by one, from top to bottom.
# Works only if no boards is currently presented on a workspace
Feature: Customer environment

  As an authorized user
  I want create a board without lists on it, create 3-5 lists on it, create 3-5 cards on the first list
  So that I set up my environment in appropriate manner to suite my plans

  Scenario: Set up the board.
    Given I am registered user in the Trello app
    When I create a board using "defaultLists" option with value "false"
    Then A board is created
    And 0 number of lists presented on the board

  Scenario: Set up the lists on board.
    Given I request to get available board
    When I create a list with name "Accomplished" on the board
    * I create a list with name "In_progress" on the board
    * I create a list with name "Should_be_done_this_week" on the board
    * I create a list with name "Long_time_plans" on the board
    Then Lists are presented on a board

  Scenario: Set up the cards on a list
    Given I request to get available board
    When I create a card using "name" option with value "Learn gitHub actions" on a list with name "Long_time_plans"
    And I create a card using "name" option with value "Learn JMeter" on a list with name "Long_time_plans"
    * I create a card using "name" option with value "learn play write" on a list with name "Long_time_plans"
    * I create a card using "name" option with value "learn one of TMS" on a list with name "Long_time_plans"
    * I create a card using "name" option with value "Find a job" on a list with name "Long_time_plans"
    Then 5 Cards are presented on a list "Long_time_plans"
    And I delete the board

