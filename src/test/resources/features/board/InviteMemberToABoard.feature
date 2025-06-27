@Invite_member
Feature: Invite member to a board

  As an authorized user
  I want to have opportunity to invite new member to a board
  So that I can work on a project with colleague

  Rule: Invite member via email.

    Scenario: Invite a member.
      Given I am registered user in the Trello app
      And I create a board with default options
      When I invite a new member on a board
      Then A new member is added to a board


