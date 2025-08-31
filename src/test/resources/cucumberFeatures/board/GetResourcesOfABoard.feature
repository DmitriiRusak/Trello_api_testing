@Get_resources_of_a_board
#  do not run these Scenarios in parallel mode, it is designed to be run in turns, one by one.
Feature: Get resources of a board

  As an authorized user
  I want to get access to resources of a board
  So that I am able to track and control current progress.

  Rule: Get the board as a resource.

  Scenario: Get the board available for user
    Given I am registered user in the Trello app
    And I create a board with default options
    When I request to get available board
    Then I got back available board

  Rule: Get the resources of a board

    Background:
      Given I am registered user in the Trello app
      And Board is presented on a workSpace

    Scenario: Get all lists available on the board.
      When I do request to get "lists" Of a board
      Then I got back requested resource

    Scenario: Get all members available on the board.
      When I do request to get "members" Of a board
      Then I got back requested resource

    Scenario: Get all cards available on the board.
      And I create a card on a list
      When I do request to get "cards" Of a board
      Then I got back requested resource

    Scenario: Get all labels on the board
      When I do request to get "labels" Of a board
      Then I got back requested resource
      And I delete the board
