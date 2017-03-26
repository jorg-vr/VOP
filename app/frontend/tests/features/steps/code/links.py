
lookup_table = {
    'start': '',
    'fleets': 'fleets',
    'users': 'users',
    'clients': 'clients',
}


def generate_link(context, page_description):
    page_description = page_description.lower()
    return context.root_url + lookup_table[page_description]
