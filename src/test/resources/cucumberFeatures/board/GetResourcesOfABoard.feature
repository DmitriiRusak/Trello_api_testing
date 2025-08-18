@Get_resources_of_a_board
Feature: Get resources of a board

  As an authorized user
  I want to get access to resources of a board
  So that I am able to track and control current progress.

#  Сработает только если в workspace есть доска.
  Scenario: Get the board available for user
    When I request to get available board
    Then I got back available board

  Rule: Get specific resource of the board.
    Background:
    Given I am registered user in the Trello app
    And I create a board with default options

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
