import App from '../App.vue'
import New from '../components/identity/New.vue'
import Edit from '../components/identity/Edit.vue'
import Index from '../components/identity/Index.vue'
import Remove from '../components/identity/Remove.vue'
import Show from '../components/identity/Show.vue'

export default [
    {
        path: '',
        component: App,
        children: [
            {
                path: 'identity',
                component: Index,
                children: [
                    { path: 'new', component: New },
                    {
                        path: ':id',
                        component: Show,
                        children: [
                            { path: 'edit', component: Edit},
                            { path: 'remove', component: Remove},
                        ]
                    }
                ]
            },
            {
                path: '/identity/new',
                component: New
            }
        ]
    },
        //TODO: Make a not found page!
        { path: '*', redirect: '/'}
    ];
