Feature: Login
    

  Scenario Outline: Login to SwagLabs Application with Correct credentials
    Given User launched SwagLabs application
    When User logged in the app using username "<UserName>" and password "<Password>"
    Then user should be able to log in

    Examples:
      | UserName           | Password     |
      | standard_user      | secret_sauce |

  @SmokeTest  
  Scenario Outline: Login to SwagLabs Application with Wrong credentials
    Given User launched SwagLabs application
    When User logged in the app using username "<UserName>" and password "<Password>"
    Then User should not get logged in

    Examples:
      | UserName           | Password     |
      | locked_out_user    | secret_sauce |
