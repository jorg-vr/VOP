from behave import *


@then(u'the user sees "{text}"')
def step_check_text(context, text):
    assert context.browser.is_text_present(text), 'The text "%s" is not visible' % text