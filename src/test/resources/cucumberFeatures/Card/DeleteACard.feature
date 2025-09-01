@All
@DeleteACard
Feature: Delete a card

  As an authorized user.
  I want to be able to delete a card
  So that I can finish my activities on it, and keep workspace clean.

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options
    And I create a card on a list

  @MovableAnnotation
  Scenario: Delete a card
    When I do delete a card
    Then Card is deleted