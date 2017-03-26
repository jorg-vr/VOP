


def before_all(context):
    context.browser_name = context.config.userdata.get('browser', 'chrome')
    context.version = context.config.userdata.get('version', '57')
    context.root_url = context.config.userdata.get('root_url', )




