Feature: Buy Product

  @End2End
  Scenario Outline: Buy a SwagLabs product successfully
    Given User launched SwagLabs application
    When User logged in the app using username "<UserName>" and password "<Password>"
    And User adds "<Product>" product to the cart
    And User enters Checkout details with "<FirstName>", "<LastName>", "<Zipcode>"
    And User completes Checkout process
    Then User should get the Confirmation of Order

    Examples:
      | UserName         | Password     | Product                   | FirstName | LastName  | Zipcode |
      | standard_user    | secret_sauce | Sauce Labs Fleece Jacket  | Ashish    | Ghosh     | 1181    | 