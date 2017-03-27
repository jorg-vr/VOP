from splinter import Browser
from time import sleep

#Set to true if tests need to be run locally.
BEHAVE_LOCAL = True

def before_all(context):
    context.browser_name = context.config.userdata.get('browser', 'chrome')
    context.version = context.config.userdata.get('version', '57')
    if BEHAVE_LOCAL:
        context.root_url = context.config.userdata.get('root_url', 'http://localhost:8080/app/')
    else:
        context.root_url = context.config.userdata.get('root_url', 'https://staff:hz67jqhj@vopro5.ugent.be/app/')
    context.platform = context.config.userdata.get('platform', 'WINDOWS')

def before_feature(context, feature):
    context.browser = Browser(context.browser_name)
    context.browser.visit(context.root_url)

def after_feature(context, feature):
    sleep(5)

