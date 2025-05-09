Feature: Cucumber test Labels

  @blocker @positive @RU
  Scenario: Тест на русском
    When Я создаю доску "ДОСКА"
    Then Я получаю код ответа "200"
    And Я удаляю доску

  @blocker
  Scenario Outline: Create a Label with valid values
    When I create a new board with name "Board Name"
    When I create a new Label on a Board with "<name>" and "<color>"
    Then The response status code should be "<code>"
    When I delete the created board

    @positive
    Examples:
      | color  | name              | code |
      | yellow | Label name yellow | 200  |
      | purple | Label name purple | 200  |
      | blue   | Label name blue   | 200  |
      | red    | Label name red    | 200  |
      | green  | Label name green  | 200  |
      | orange | Label name orange | 200  |
      | black  | Label name black  | 200  |
      | sky    | Label name sky    | 200  |
      | pink   | Label name pink   | 200  |
      | lime   | Label name lime   | 200  |
      |        | Label name NULL   | 200  |

    @negative
    Examples:
      | color | name             | code |
      | 12322 | Label name 12322 | 400  |
      | синий | Label name синий | 400  |

Scenario: Get Label
  When I create a new board with name "Board get label"
  When I create a new Label on a Board with "Label" and "red"
  When I get label
  Then The response status code should be "200"
  When I delete the created board

  Scenario: Update Label
    When I create a new board with name "Board update label"
    When I create a new Label on a Board with "Label" and "blue"
    And I update label name "Name" and color "green"
    Then The response status code should be "200"
    And Check name "Name"
    When I delete the created board