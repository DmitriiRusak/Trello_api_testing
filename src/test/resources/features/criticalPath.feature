@critical @api @trello
@ui_api @trello @smoke
Feature: Trello UI and API Integration Test

  Background:
    Given I have valid Trello API credentials
    And I am logged into Trello via UI

  @create_board
  Scenario: User creates a board via UI and verifies via API
    When I create a new board named "UI-API Test Board" via Trello UI
    Then the board should exist in Trello API response
    And the API response status code should be 200
    And the board name in API should match "UI-API Test Board"

  @cleanup
  Scenario: Delete test board via API
    Given a board named "UI-API Test Board" exists via API
    When I delete the board via API
    Then the API response status code should be 200