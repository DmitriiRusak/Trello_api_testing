@All
Feature: Get resources of a list

  As an authorized user
  I want to get access to resources of a list
  So that I am able to track and control the compliance of resources with current tasks.

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Rule: Get specific resource of the board.

    Scenario: Get all cards available on the list.
      When I create a card on a list
      And I do request to get "card" of a list
      Then I got back requested resource

    Scenario: Get the board the list is on.
      When I do request to get "board" of a list
      Then I got back requested resource

    Scenario: Get the actions of a list.
      When I do request to get "actions" of a list
      Then I got back actions of a list


