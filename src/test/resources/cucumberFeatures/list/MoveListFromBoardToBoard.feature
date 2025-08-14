Feature: Move list from one board to another board

  As an authorized user
  I want to be able to move a list from one board to another board
  So that I am able to use it in new project.

  Background: Create two boards, one of them without lists.

    Given I am registered user in the Trello app
    And I create a board with default options
    And I create one more board

  @Move_list_from_board_to_board
  Scenario: Move list from one board to another
    When I move to do list, from one board, to another
    Then I see to do list being moved from one board, to another
