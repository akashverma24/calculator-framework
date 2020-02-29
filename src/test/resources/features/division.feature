Feature: To test division button functionality of the calculator

  Background: User should be on calculator application
    Given User is present on web url "https://www.online-calculator.com/full-screen-calculator/"
    Then Verify the title of the page

  @Test3
  Scenario Outline: Verify user is able to divide two positive integers
    When User input the "<number1>" and "<Negative Flag 1>"
    And press "Division" button
    And User input the "<number2>" and "<Negative Flag 2>"
    And press "Enter" button
    Then calculator value should be equal "<Expected>" value


    Examples:
      | number1   |  Negative Flag 1  | number2       | Negative Flag 2 | Expected      |
      | 999999999 |       NO          | 100000000     |     NO          |  9.99999999   |
      | 134.123   |      YES          | 3459          |     NO          | -0.03877508   |
      | 9864      |      YES          | 3.5678        |     NO          | -2 764.72896   |
      | 5.21      |      NO           | 3             |    YES          | -1.73666667    |
      | 34567     |      NO           | 4.567         |     NO          | 7 568.86359   |
      | 45.082    |      NO           | 9.999999      |    YES          | -4.50820045   |
      | 20394858  |      NO           | 5647.38295    |      NO         |  3 611.38215   |





  @Test4
  Scenario Outline: Verify calculator report error for incorrect division
    When User input the "<number1>" and "<Negative Flag 1>"
    And press "Division" button
    And User input the "<number2>" and "<Negative Flag 2>"
    And press "Enter" button
    Then calculator value should be equal "<Expected>" value

    Examples:
      | number1   |  Negative Flag 1  | number2       | Negative Flag 2 | Expected      |
      | 999999999 |       NO          | 0             |     NO          |  Error        |
      | 10000000  |       YES         | .             |     NO          |  Error        |

  @Test5
  Scenario Outline: Should be able to divide the result of a previous operation by a large integer
    When User input the "<number1>" and "<Negative Flag 1>"
    And press "Addition" button
    And User input the "<number2>" and "<Negative Flag 2>"
    And press "Enter" button
    And press "Division" button
    And User input the "<number2>" and "<Negative Flag 2>"

    Examples:
      | number1   |  Negative Flag 1  | number2       | Negative Flag 2 | Expected      |
      | 99999999 |       NO          | 1000000     |     YES          |  100.999999   |

