from splinter import Browser
from time import sleep
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

BEHAVE_DEBUG = True

def before_all(context):
    context.browser_name = context.config.userdata.get('browser', 'chrome')
    context.version = context.config.userdata.get('version', '57')
    context.root_url = context.config.userdata.get('root_url', 'https://staff:hz67jqhj@vopro5.ugent.be/app/')
    context.platform = context.config.userdata.get('platform', 'WINDOWS')
    context.debug = BEHAVE_DEBUG

def before_feature(context, feature):
    context.browser = Browser(context.browser_name)
    context.browser.visit(context.root_url)


def after_feature(context, feature):
    sleep(20)