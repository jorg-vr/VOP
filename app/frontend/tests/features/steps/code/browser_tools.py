from selenium.webdriver.support.ui import WebDriverWait
from selenium.common.exceptions import TimeoutException


def find_web_elements(context, key, search_function):
    elements = search_function(key)
    if len(elements) == 0:
        raise AssertionError('The given key "%s" returns no web elements.' % key)
    else:
        return elements


def find_web_element(context, key, search_function):
    elements = find_web_elements(context, key, search_function)
    if len(elements) > 1:
        raise AssertionError('The given key "%s" returns more than one web elements.'%key)
    else:
        return elements[0]


def visit_url(context, url):
    '''
    This function will visit a certain URL. 
    The function asserts you are on the new page, else an error will occur.
    '''
    context.browser.visit(url)
    check_url(context, url)


def check_url(context, url):
    try:
        WebDriverWait(context.browser.driver, 10).until(wait_for_url_condition(context, url))
    except TimeoutException:
        print("An error occurred while checking the URL (%s)" % url)
        # This assertion isn't really necessary as WebDriverWait will throw a TimeoutException if the URL is incorrect.
    assert url.rstrip('/') == context.browser.url.rstrip(
        '/'), "The given URL (%s) doesn't match the browsers URL (%s)" % (url, context.browser.url)


def find_objects_by_name(context, elementName):
    """
    This is a function which looks for all HTML elements with a certain name. Use this function only when you're hopeless.
    The optional argument printfunction can be turned in order to print the functions of the found elements. 
    """
    functions = [context.browser.find_by_css, context.browser.find_by_id, context.browser.find_by_name, context.browser.find_by_tag, context.browser.find_by_text, context.browser.find_by_value,
                 context.browser.find_by_xpath, context.browser.find_link_by_href, context.browser.find_link_by_partial_href, context.browser.find_link_by_partial_text, context.browser.find_option_by_text,
                 context.browser.find_option_by_value]
    for f in functions:
        foundElements = f(elementName)
        print(str(f) + ' returns the following element(s):\n')
        if(len(foundElements)>0):
            for foundElement in foundElements:
                print(foundElement)

class wait_for_url_condition(object):
    def __init__(self, context, expected_url):
        self.context = context
        self.expected_url = expected_url

    def __call__(self, driver):
        return self.expected_url.rstrip('/') == self.context.browser.url.rstrip('/')