@All
@Archive_UnArchive_list
Feature: Archive or unarchive a list

  As an authorized user
  I want to be able to archive or unarchive a list
  So that I am able to put away unnecessary list with ability bring it back at any given time.

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Scenario: Archive a list.
    When I archive a list
    Then A list is archived

  Scenario: Unarchive a list.
    When I archive a list
    And Un archive a list
    Then A list is presented on a board