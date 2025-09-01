@All
@BoardComponentTesting
Feature: Create a board

  As an authorized user
  I want to create a board
  so that I would be able to clearly plan my goals

  Background:
    Given I am registered user in the Trello app

  Rule: Create a board with default options

    @Smoke
    @EndToEndBasicSetUp
    @CreateABoardByDefault
    Scenario: Create a board with 3 default lists on it.
      When I create a board with default options
      Then A board is created
      And 3 number of lists presented on the board

  Rule: Create a board with specific options

    Scenario Outline: Create a board with with specific options.
      When I create a board using "<optionName>" option with value "<value>"
      Then A board is created
      And a board has "<optionName>" option set wth value "<value>"

      Examples:
        | optionName            | value              |
        | prefs_permissionLevel | public             |
        | desc                  | Sample description |
        | prefs_background      | red                |

    Scenario: Create a board without default lists.
      When I create a board using "defaultLists" option with value "false"
      Then A board is created
      And 0 number of lists presented on the board
