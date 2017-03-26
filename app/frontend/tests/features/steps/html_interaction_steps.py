from behave import *
from code import browser_tools

@when(u'the user clicks on "{text}"')
def step_click(context, text):
    browser_tools.find_web_element(context, text, context.browser.find_by_text).click()


