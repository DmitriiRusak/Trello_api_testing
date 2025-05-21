Feature: Cucumber test Boards

  @blocker @positive
  Scenario: End-to-end test with Boards
#    testCreateABoard
    When I create a new board with name "Cucumber Board"
    Then The response status code should be "200"
    And The response should contain a valid board id
#    testGetBoard
    When I get the board with board id
    Then The response status code should be "200"
    And The response should contain a valid board id
    And The response should contain a valid board name "Cucumber Board"
#    testCreateAListOnABoard
    When I create a list on a board with name "Cucumber List"
    Then The response status code should be "200"
#    testCreateALabelOnABoard
    When I create a label on a board with name "Cucumber Label" and color "red"
    Then The response status code should be "200"
    And The response should contain a valid label name "Cucumber Label"
    And The response should contain a valid color "red"
#    testGetLabelsOnABoard
    When I get label on a board
    Then The response status code should be "200"
#    testGetAFieldOnABord
    When I get specific field with "name" from a board
    Then The response status code should be "200"
#    testGetActionsOfABoard
    When I get actions from a board
    Then The response actions in count "3"
#    Get checklists from a board
    When I get checklists from a board
    Then The response status code should be "200"
    And The response should contain empty array
#    testGetCardsOnABoard
    When I get all existed cards from a bord
    Then The response status code should be "200"
    And The response should contain empty array
#    testGetFilteredCardsOnABoard
    When I get all existed filtered "all" cards from a bord
    Then The response status code should be "200"
    And The response should contain empty array
#    testGetCustomFieldsForBoard
    When I get all existed custom fields from a bord
    Then The response status code should be "200"
    And The response should contain empty array
#    testGetListsOnABoard
    When I get "all" lists from a bord
    Then The response status code should be "200"
    Then The response actions in count "4"
#    testGetFilteredListsOnABoard
    When I get "closed" lists from a bord
    Then The response status code should be "200"
    And The response should contain empty array
#    testGetTheMembersOfABoard
    When I get members of a bord
    Then The response status code should be "200"
    Then The response actions in count "1"
##    testInviteMemberToBoardViaEmail
#  Member not allowed to add a multi-board guest without allowBillableGuest parameter
#    When I invite Member to Board via email
#    Then The response actions in count "2"
#    testUpdateABoard
    When I update a board by giving a new name "New Cucumber Board"
    Then The response status code should be "200"
    And The response should contain a valid board id
    And The response should contain a valid board name "New Cucumber Board"
#    testGetBoardStarsOnABoard
    When I get boardStars on a Board
    Then The response status code should be "200"
#    testGetMembershipsOfABoard
    When I get memberships of a Board
    Then The response status code should be "200"
##    testRemoveMemberFromBoard
#    When I remove member from board
#    Then The response status code should be "200"
#    testDeleteABoard
    When I delete the created board
    Then The response status code should be "200"