@CreateList
Feature: Create a list

  As an authorized user
  I want to create a list on a board
  so that I would be able to add cards (tasks) related to my prject

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

    @Smoke
    @EndToEndBasicSetUp
    Scenario: Create a list
      When I create a list with name "someName" on the board
      Then List "someName" is created

