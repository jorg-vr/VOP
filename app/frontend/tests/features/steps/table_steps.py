from behave import *
from code import html_tools

@then(u'the user sees the {row_class} "{row}"')
def check_row(context, row_class, row):
    '''
    :param row_class: html class of the row
    :param row: content of the row
    '''
    rows = html_tools.html_rows_to_array(context, row_class)
    assert row in rows