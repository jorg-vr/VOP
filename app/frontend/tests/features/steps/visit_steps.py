from behave import *
from code import links
from code import browser_tools


@given('the user is on the {page_description} page')
@when('the user visits the {page_description} page')
def step_visit(context, page_description):
    browser_tools.visit_url(context, links.generate_link(context, page_description))
