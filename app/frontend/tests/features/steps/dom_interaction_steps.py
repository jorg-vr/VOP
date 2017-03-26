from behave import *
from code import browser_tools


@when(u'the user clicks on "{text}"')
def step_click_text(context, text):
    browser_tools.find_web_element(context, text, context.browser.find_by_text).click()


@when(u'the user clicks on the {type} button')
def step_click_button(context, type):
    browser_tools.find_web_element(context, type, context.browser.find_by_id).click()


@when(u'the user fills in {key} with "{value}"')
def step_fill(context, key, value):
    browser_tools.find_web_element(context, key, context.browser.find_by_id).fill(value)


@when(u'the user selects {value} from {select_id')
def step_select(context, value, select_id):
    pass