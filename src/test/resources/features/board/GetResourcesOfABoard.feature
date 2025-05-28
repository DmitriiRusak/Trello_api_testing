Feature: Get resources of a board

  As an authorized user
  I want to get access to resources of a board
  So that I am able to track and control current progress.

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Rule: Get specific resource of the board.

    Scenario: Get all lists available on the board.
      When I do request to get "lists" Of a board
      Then I got back requested resource
      And Since the scenario is for testing purpose only I delete the board to keep workspace clean

    Scenario: Get all members available on the board.
      When I do request to get "members" Of a board
      Then I got back requested resource
      And Since the scenario is for testing purpose only I delete the board to keep workspace clean

    Scenario: Get all cards available on the board.
      And I create a card on a list
      When I do request to get "cards" Of a board
      Then I got back requested resource
      And Since the scenario is for testing purpose only I delete the board to keep workspace clean

    Scenario: Get all labels on the board
      When I do request to get "labels" Of a board
      Then I got back requested resource
      And Since the scenario is for testing purpose only I delete the board to keep workspace clean
