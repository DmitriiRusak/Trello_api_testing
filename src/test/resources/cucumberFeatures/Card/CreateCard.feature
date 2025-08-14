@CreateCard
Feature: Create a card

  As an authorized user
  I want to create a card
  So that I would be able to plan my current task.

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Rule: Create a card with default options

    @Smoke
    Scenario: Create a card with dafault option
      When I create a card on a list
      Then A card is created

  Rule: Create a card with specific options

    @EndToEndBasicSetUp
    Scenario Outline: Create a card with specific options.
      When I create a card using "<optionName>" option with value "<value>"
      Then A card is created
      And a card has "<optionName>" option set wth value "<value>"

      Examples:
        | optionName | value                             |
        | name       | Card_created_with_specific_option |
        | desc       | Some description for the card     |
        | pos        | 140739999999999                   |
        | pos        | 123456789101112                   |
        | closed     | false                             |
        | subscribed | true                              |

#  Test for 'closed' should be asserted differently, since when you closed the card it is dissapered from board.
#  In another word it should be separate Sceanario with specific Assert.