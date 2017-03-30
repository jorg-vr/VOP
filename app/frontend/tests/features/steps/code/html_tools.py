import re
from code import browser_tools

def html_rows_to_array(context, row_class):
    '''
    :param row_class: html class of the rows
    '''
    xpath = u"//div[contains(concat(' ', normalize-space(@class), ' '), ' %s ')]"%row_class
    row_elements = browser_tools.find_web_elements(context, xpath, context.browser.find_by_xpath)
    #row_elements = browser_tools.find_web_element(context, row_class, context.browser.driver.find_element_by_class_name)
    return [row.text for row in row_elements]
