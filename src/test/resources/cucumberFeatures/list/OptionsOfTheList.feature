@All
@GetAList
Feature: Get options of a list

  As an authorized user
  I want to have opportunity to get information and edit current options of a list
  So that I would be able to track control and update options of a list in accordance with my plans

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Rule: Get all available options of the list

    Scenario: Get all available information from the list
      When I request the lists information
      Then I got back requested options for "List"

  Rule: Get a specific option from the list

    Scenario Outline: Get specific option from the list
      When I do request to get "<field>" of a "list"
      Then I got back requested "<field>" of a list

      Examples:
        | field   |
#        | all   | так можно получить все fields которые существуют у list-a. Но в этом rule это не корректно
        | name    |
        | closed  |
        | idBoard |
        | pos     |

  Rule: Update a List

    @UpdateAList
    Scenario Outline: Update a list
      When I update a list options "<optionNames>" with new values "<optionValues>"
      Then A list options "<optionNames>" is updated with values "<optionValues>"

      Examples:
        | optionNames                | optionValues                                   |
        | name                       | name_for_update                                |
        | pos                        | 140737488404480                                |
        | subscribed                 | true                                           |
        | closed                     | true                                           |
        | name closed                | double_options_name false                      |
        | name closed pos            | double_options_name false 140737488404480      |
        | name closed pos subscribed | double_options_name false 140737488404480 true |

    @EndToEndBasicSetUp
    Scenario Outline: Update lists name
      When I update a list name "<listName>" with new name "<newNameValue>"
      Then A name of the list "<listName>" is changed to "<newNameValue>"

      Examples:
        | listName | newNameValue      |
        | To Do    | Should be done |
        | Doing    | In progress    |
        | Done     | Finished       |
