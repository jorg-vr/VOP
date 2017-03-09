import App from '../components/App.vue'
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
            //Identity
            { path: 'identity', component: Index },
            { path: 'identity/new', component: New },
            { path: 'identity/:id', component: Show },
            { path: 'identity/:id/edit', component: Edit},
            { path: 'identity/:id/remove', component: Remove},
            //Fleet
            { path: 'fleet', component: Index },
            { path: 'fleet/new', component: New },
            { path: 'fleet/:id', component: Show },
            { path: 'fleet/:id/edit', component: Edit},
            { path: 'fleet/:id/remove', component: Remove},
            //Vehicle
            { path: 'vehicle', component: Index },
            { path: 'vehicle/new', component: New },
            { path: 'vehicle/:id', component: Show },
            { path: 'vehicle/:id/edit', component: Edit},
            { path: 'vehicle/:id/remove', component: Remove}
        ]
    },
        //TODO: Make a not found page!
        { path: '*', redirect: ''}
    ];
