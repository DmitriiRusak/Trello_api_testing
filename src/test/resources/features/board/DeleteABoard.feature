@Delete_a_board
Feature: Delete a board

  As an authorized user
  I want to have opportunity to delete a board
  So that I can complete the project on time

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Scenario: Delete a board.
    When I delete the board
    Then Board is deleted