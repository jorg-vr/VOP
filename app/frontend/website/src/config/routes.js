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
            { path: 'identity', component: Index },
            { path: 'identity/new', component: New },
            { path: 'identity/:id', component: Show },
            { path: 'identity/:id/edit', component: Edit},
            { path: 'identity/:id/remove', component: Remove}
        ]
    },
        //TODO: Make a not found page!
        { path: '*', redirect: ''}
    ];
