Feature: To test subtraction button functionality of the calculator

  Background: User should be on calculator application
    Given User is present on web url "https://www.online-calculator.com/full-screen-calculator/"
    Then Verify the title of the page

  @Test6
  Scenario Outline: Verify user is able to subtract two integers
    When User input the "<number1>" and "<Negative Flag 1>"
    And press "Subtract" button
    And User input the "<number2>" and "<Negative Flag 2>"
    And press "Enter" button
    Then calculator value should be equal "<Expected>" value


    Examples:
      | number1     |  Negative Flag 1  | number2       | Negative Flag 2 | Expected          |
      | 84635204    |   NO              | 0             |   NO            | 84 635 204        |
      | 1           |   YES             | 2.25          |   NO            |   -3.25           |
      | 999.999999  |   NO              | 98745         |   NO            | -97 745           |
      | 657945      |   NO              | 8.67          |   NO            | 657 936.33        |
      | 4869.20479  |   NO              | 5149.63789    |   NO            | -280.4331         |
      | 100000000   |   YES             | 100000000     |   NO           | 200 000 000        |
      | 999999998   |   NO              | 1             |   NO            | 999 999 997     |
      | 46           |   YES             | 0             |   NO            | -46            |




  @Test7
  Scenario Outline: Verify user is able to subtract a number from the result of a previous result

    When User input the "<number1>" and "<Negative Flag 1>"
    And press "Addition" button
    And User input the "<number2>" and "<Negative Flag 2>"
    And press "Enter" button
    And press "Subtract" button
    And User input the "<number2>" and "<Negative Flag 2>"
    And press "Enter" button
    Then calculator value should be equal "<Expected>" value

    Examples:
      | number1     |  Negative Flag 1  | number2       | Negative Flag 2 | Expected      |
      | 999.999  |   NO              | 9874         |   NO            | 999.999           |


