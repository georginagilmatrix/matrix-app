#language: en
Feature: Mail Register
  As an unregistered user I should be able to register my email
  when I enter an email

  Background:
    Given I launch Matrix app as an unregistered user
    Then I see Landing screen
    When I click on "Obtener tu tarjeta" button

    Then I see Alias Register screen
    When I enter a valid alias "GEORGINA"
    And I click on Continue button of Alias Register screen

    Then I see DNI Register screen
    When I enter a valid DNI "GEORGINA"
    And I click on Continue button of DNI Register screen
    Then I see Mail Register screen

  @SuccessfulMailRegister
  Scenario Outline: Successful Mail Register
    Given I am on Mail Register screen
    When I enter a valid mail <mail>
    Then Continue button of Mail Register screen is enabled

    When I click on Continue button of Mail Register screen
    Then I see "Next" screen

    Examples:
    | mail |
    | "xx@ss.com" |

  @UnsuccessfulMailRegister
  Scenario Outline: Unsuccessful Mail Register
    Given I am on Mail Register screen
    When I enter an invalid mail <mail>
    Then Continue button of Mail Register screen is disabled
    And I see <message> message on Mail Register screen

    Examples:
    | mail | message |
    | "xx@ss.com" | "message" |
