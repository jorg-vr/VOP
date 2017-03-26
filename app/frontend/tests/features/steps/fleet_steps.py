from behave import *


@when(u'the user fills in the fleet form')
def step_fill_form(context):
    context.execute_steps(u'''
        When the user fills in name with "Test Vloot 1"
        And the user clicks on "Hertsens"
    ''')


@then(u'the user sees the new fleet')
def step_check_fleet(context):
    context.execute_steps(u'''
        Then the user sees the row "Test Vloot 1, Hertsens"
    ''')