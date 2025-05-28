Feature: Get options of the board

  As an authorized user
  I want to have opportunity to get information and edit current options of a board
  So that I can track and make decisions in a proper time for updating them in accordance with my plans.

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Rule: Get all available options of the board.

    Scenario: Get all available options of the board in one request.
      When I do request for all options of the board
      Then I got back requested options
      And Since the scenario is for testing purpose only I delete the board to keep workspace clean

  Rule: Get a specific option of the board.

    Scenario Outline: Get a name of a board.
      When I do request to get "<field>" Of a board
      Then I got back requested "<field>"
      And Since the scenario is for testing purpose only I delete the board to keep workspace clean

      Examples:
      |field|
      |name |
      |desc |
      |url  |
      |dateLastView|

