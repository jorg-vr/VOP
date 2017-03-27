from behave import *

TEST_FLEET_NAME = 'Test Vloot'
TEST_FLEET_COMPANY = 'Hertsens'


@when(u'the user fills in the fleet form')
def step_fill_form(context):
    context.execute_steps(u'''
        When the user fills in name with "%s"
        And the user selects %s from company
    '''%(TEST_FLEET_NAME, TEST_FLEET_COMPANY))


@then(u'the user sees the new fleet')
def step_check_fleet(context):
    context.execute_steps(u'''
        Then the user sees the fleetrow "%s %s"
    '''%(TEST_FLEET_NAME, TEST_FLEET_COMPANY))
