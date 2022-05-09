#language: en_US
Feature: SignUp

  @SuccessfulSignUp
  Scenario: Successful Sign Up Flow Validation via App
  As an unregistered user I should be able to Sign Up on Matrix app
  when I try to register myself

    Given I launch Matrix app as an unregistered user
    Then I see Landing screen
    When I click on "Obtener tu tarjeta" button

    Then I see Sign Up screen
    When I click on "Click me!" button in Sign Up screen
    Then I start Onboarding process