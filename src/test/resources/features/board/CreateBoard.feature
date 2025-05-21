Feature: Create a board

  As an authorized user
  I want to create a board
  so that I would be able to clearly plan my goals

  Background:
    Given I am registered user in the Trello app

  Rule: Create a board with default options

      Scenario: Create a board with 3 default lists on it.
        When I create a board with default options
        Then A board is created
        And Three lists presented on the board

  Rule: Create a board with specific options

      Scenario: Create a board with public access
        When I create a board with public access
        Then A board is created
        And a board has public access

      Scenario: Create a board without default lists
        When I create a board without default lists
        Then A board is created
        And A board doesn't have 3 default lists

      Scenario: Create a board with description
        When I create a board with description
        Then A board is created
        And description is presented on the board

      Scenario: Create a board with red colored background
        When I create a board with red colored background option
        Then A board is created
        And the color of background is red