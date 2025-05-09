Feature: Smoke Tests [Trello API]
  As a Trello API user
  I want to verify basic API functionality
  So that I can ensure the core features are working

  Background:
    Given I have valid Trello API credentials


  @positive @board
  Scenario: Create and delete a board
    When i create a new board with name "Smoke Test Board"
    Then the response status code should be "200"
    And the response should contain a valid board id
    When i delete the created board
    Then the response status code should be "200"

  @positive @list
  Scenario: Create a list on a board
    When i create a new board with name "List Test Board"
    When I create a list with name "Test List" on the board
    Then the response status code should be "200"
    And the response should contain a valid list id
    And the list name should be "Test List"
    And i delete the created board

  @positive @card
  Scenario: Create a card on a list
    When i create a new board with name "Smoke Test Board"
    And I have created a list named "Card Test List" on the board
    When I create a card with name "Test Card" on the list
    Then the response status code should be "200"
    And the response should contain a valid card id
    And the card name should be "Test Card"
    And i delete the created board
#
#  @negative @authentication
#  Scenario: Attempt to create board with invalid API key
#    Given I have invalid Trello API credentials
#    When I attempt to create a new board with name "Unauthorized Board"
#    Then the response status code should be 401
#    And the response should contain an unauthorized error message