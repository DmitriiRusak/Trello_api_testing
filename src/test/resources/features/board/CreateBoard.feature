Feature: Create a board

  As an authorized user
  I want to create a board
  so that I would be able to clearly plan my goals

  Background:
    Given I am registered user in the Trello app
    Then Since the scenario is for testing purpose only I delete the board to keep workspace clean

  Rule: Create a board with default options

      Scenario: Create a board with 3 default lists on it.
        When I create a board with default options
        Then A board is created
        And 3 number of lists presented on the board

  Rule: Create a board with specific options

      Scenario: Create a board with public access.
        When I create a board using "prefs_permissionLevel" option with value "public"
        Then A board is created
        And a board has "prefs_permissionLevel" option set wth value "public"

      Scenario: Create a board without default lists.
        When I create a board using "defaultLists" option with value "false"
        Then A board is created
        And 0 number of lists presented on the board

      Scenario: Create a board with description.
        When I create a board using "desc" option with value "Sample description"
        Then A board is created
        And a board has "desc" option set wth value "Sample description"

      Scenario: Create a board with red colored background.
        When I create a board using "prefs_background" option with value "red"
        Then A board is created
        And a board has "prefs_background" option set wth value "red"
