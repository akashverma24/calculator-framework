Feature: To test clear button functionality of the calculator

  Background: User should be on calculator application
    Given User is present on web url "https://www.online-calculator.com/full-screen-calculator/"
    Then Verify the title of the page

  Scenario Outline:Verify user is able to clear the screen after inserting different values
    When User input the "<number>"
    And press "<Negative>" button
    And press "Clear" button
    Then  value should be reset to zero
    Examples:
      | number        | Negative  |
      | 019283.78     |  YES      |
      | 123.67        |  NO       |
      | 000000.0000   |  NO       |
      | 9999999.999   |  YES      |
      | 9.999999999   |  NO       |
      | 0.000000000   |  YES      |

  Scenario Outline:Verify user is able to press clear multiple times
    When User input the "<number>"
    And press "Clear" button
    And press "Clear" button
    And press "Clear" button
    And press "Clear" button
    And press "Clear" button
    Then value should be reset to zero
    Examples:
      | number     |
      | 00000      |
      | 987654567  |
      | 9.9999999  |
      | 00.00000   |
