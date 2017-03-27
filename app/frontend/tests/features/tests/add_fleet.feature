Feature: Verify add fleet
  Objective: To verify that the user is able to add a fleet

  Scenario: The user adds a fleet
    Given the user is on the fleets page
    When the user clicks on the add button
    And the user fills in the fleet form
    And the user submits the form
    Then the user is on the fleets page
    And the user sees the new fleet

  #TODO add restore scenario
  #TODO add failing scenarios