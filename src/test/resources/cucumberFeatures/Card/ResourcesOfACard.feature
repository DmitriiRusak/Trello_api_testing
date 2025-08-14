Feature: Resources of a card

  As an authorized user
  I want to get access to resources of a card
  So that I am able to track and control the compliance of resources with current tasks.

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options
    And I create a card on a list

  @GetResourcesOfACard
  Rule: Get resources of the card.

  Scenario: Get actions of the card.
    When I do create a comment "A new comment from ResourcesOfACard, Scenario: Get actions of the card " on a card
    And I do request to get resource "/actions" of a "cards"
    Then I got back requested resource

  Scenario: Get attachments on a card
    When I add an attachment "src/test/resources/ForCreateAttachmentOnCardTest.txt" to the card
    And I do request to get resource "/attachments" of a "cards"
    Then I got back requested resource

  Scenario: Get specific attachment on a card
    When I add an attachment "AttachmentForResourcesOfACard.txt" to the card
    Then attachment "AttachmentForResourcesOfACard.txt" on a card is presented

  Scenario: Get the board the card is on
    When I do request to get resource "/board" of a "cards"
    Then I got back requested resource

  Scenario: Get checklists on a card
    When I do create a checklist on a card
    And I do request to get resource "/checklists" of a "cards"
    Then I got back requested resource

  Scenario: Get the list the card is on
    When I do request to get resource "/list" of a "cards"
    Then I got back requested resource


  @CreateResourceOnACard
  Rule: Create a resource on a card

  Scenario: Create an attachment on a card
    When I add an attachment "src/test/resources/ForCreateAttachmentOnCardTest.txt" to the card
    Then attachment "src/test/resources/ForCreateAttachmentOnCardTest.txt" on a card is presented

  Scenario: Create a checklist on a card
    When I do create a checklist on a card
    Then CheckList is created

  Scenario: Create new comment to a card
    When I do create a comment "A new comment from ResourceOfACard" on a card
    Then Comment "A new comment from ResourceOfACard class" is created

  Scenario: Create new label on a card
    When I do create a label
    And Add a label to a card
    Then A label is presented on a card

  @EditeResourceOfACard
  Rule: Edite resource of a card

  Scenario: Delete an attachment of a card
    Given I add an attachment "src/test/resources/ForCreateAttachmentOnCardTest.txt" to the card
    And attachment "src/test/resources/ForCreateAttachmentOnCardTest.txt" on a card is presented
    When I delete an attachment on a card
    Then Resource is deleted from a card

  Scenario: Delete checklist of a card
      When I do create a checklist on a card
      And I delete a checklist off the card
      Then Resource is deleted from a card
