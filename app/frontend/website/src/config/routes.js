import rootUrl from './rootUrl'
import App from '../app.vue'

import NewUser from '../pages/user/new.vue'
import EditUser from '../pages/user/edit.vue'
import ShowUser from '../pages/user/show.vue'
import IndexUser from '../pages/user/index.vue'


import NewClient from '../pages/client/new.vue'
import EditClient from '../pages/client/edit.vue'
import ShowClient from '../pages/client/show.vue'
import IndexClient from '../pages/client/index.vue'


import NewFleet from '../pages/fleet/new.vue'
import IndexFleet from '../pages/fleet/index.vue'
import ShowFleet from '../pages/fleet/show.vue'
import EditFleet from '../pages/fleet/edit.vue'


import newVehicle from '../pages/vehicle/new.vue'
import editVehicle from '../pages/vehicle/edit.vue'
import showVehicle from '../pages/vehicle/show.vue'

export default [
    {
        path: '/app',
        component: App,
        children: [
            //User
            {path: 'users', name: 'users', component: IndexUser},
            {path: 'users/new', name: 'new_user', component: NewUser},
            {path: 'users/:id(\\d+)', name: 'user', component: ShowUser, props: true},
            {path: 'users/:id(\\d+)/edit', name: 'edit_user', component: EditUser, props: true},
            // Clients
            {path: 'clients', name: 'clients', component: IndexClient},
            {path: 'clients/new', name: 'new_client', component: NewClient},
            {path: 'clients/:id(\\d+)', name: 'client', component: ShowClient, props: true},
            {path: 'clients/:id(\\d+)/edit', name: 'edit_client', component: EditClient, props: true},
            //Fleet
            {path: 'fleets', name: 'fleets', component: IndexFleet},
            {path: 'fleets/new', name: 'new_fleet', component: NewFleet},
            {path: 'fleets/:id(\\d+)', name: 'fleet', component: ShowFleet, props: true},
            {path: 'fleets/:id(\\d+)/edit', name: 'edit_fleet', component: EditFleet, props: true},


            //Vehicle
            {path: 'vehicles/new', name: 'new_vehicle', component: newVehicle, props: true},
            {path: 'vehicles/:id(\\d+)', name: 'vehicle', component: showVehicle, props: true},
            {path: 'vehicles/:id(\\d+)/edit', name: 'edit_vehicle', component: editVehicle, props: true}
        ],
    },
    {path: '/test/app', component: App, alias: '/app', children: []}
];

