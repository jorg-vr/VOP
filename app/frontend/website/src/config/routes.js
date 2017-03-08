import App from '../App.vue'
import Create from '../components/identity/Create.vue'
import Edit from '../components/identity/Edit.vue'
import Index from '../components/identity/Index.vue'
import Remove from '../components/identity/Remove.vue'
import Show from '../components/identity/Show.vue'

export default [
    {
        path: '/',
        component: App,
        children: [
            {
                path: 'identity',
                component: Index,
                children: [
                    { path: 'create', component: Create},
                    { path: 'edit', component: Edit},
                    { path: 'remove', component: Remove},
                    { path: ':id', component: Show }
                ]
            }

        ]
    },
        { path: '*', redirect: '/'}
    ];
