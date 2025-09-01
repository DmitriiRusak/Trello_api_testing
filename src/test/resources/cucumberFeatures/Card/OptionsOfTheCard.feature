@All
@OptionsOfTheCard
Feature: Options of a card

  As an authorized user.
  I want to be able to receive information and edit current card settings.
  So that I can track and make timely decisions about updating them in accordance with my current plans.

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options
    And I create a card on a list

  Rule: Get all available options of the card

    Scenario: Get all available information from the card
      When I request the cards information
      Then I got back requested options for "Card"

  Rule: Get a specific option from the card

    Scenario Outline: Get specific option from the card
      When I do request to get "<field>" of a "card"
      Then I got back requested option "<field>" for card
      Examples:
        | field            |
        | id               |
        | address          |
        | badges           |
        | checkItemStates  |
        | closed           |
        | coordinates      |
        | creationMethod   |
        | dueComplete      |
        | dateLastActivity |
        | desc             |
        | descData         |
        | due              |
        | dueReminder      |
        | idBoard          |

  Rule: Update an option of a card

    @Smoke
    Scenario: Move the card from list to list
      When I move the card to the next list
      Then I see the card is being moved to the next list

    Scenario Outline: Update specific option of a card
      When I change "<option>" of a card to "<value>"
      Then a card has "<option>" option set wth value "<value>"

      Examples:
        | option     | value                                        |
        | name       | New name for a card                          |
        | desc       | The description from java project for a card |
        | closed     | true                                         |
        | subscribed | true                                         |

    Scenario Outline: Update color of a card
      When I change color of a card to "<color>"
      Then The color of a card changed to "<color>"

      Examples:
        | color  |
        | pink   |
        | yellow |
        | lime   |
        | blue   |
        | black  |
        | orange |
        | red    |
        | purple |
        | sky    |
        | green  |