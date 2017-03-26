import re

def htmlrows_to_array(context):
    row_elements = context.browser.find_element_by_class_name('fleetrow')
    rows = []
    for row_element in row_elements:
        htmlrow = row_element.get_attribute('innerHTML')
        htmlrow = re.sub(r' *</?tr> *', htmlrow)
        print(htmlrow)
