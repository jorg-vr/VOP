import App from '../app.vue'

import indexUser from '../pages/user/index.vue'
import newUser from '../pages/user/new.vue'
import editUser from '../pages/user/edit.vue'
import showUser from '../pages/user/show.vue'

import indexClient from '../pages/client/index.vue'
import newClient from '../pages/client/new.vue'
import editClient from '../pages/client/edit.vue'
import showClient from '../pages/client/show.vue'

import indexFleet from '../pages/fleet/index.vue'
import newFleet from '../pages/fleet/new.vue'
import editFleet from '../pages/fleet/edit.vue'
import showFleet from '../pages/fleet/show.vue'

import indexVehicle from '../pages/vehicle/index.vue'
import newVehicle from '../pages/vehicle/new.vue'
import editVehicle from '../pages/vehicle/edit.vue'
import showVehicle from '../pages/vehicle/show.vue'

export default [
    {
        path: '',
        component: App,
        children: [
            //User
            {path: 'users', name: 'users', component: indexUser},
            {path: 'users/new', name: 'new_user', component: newUser},
            {path: 'users/:id(\\d+)', name: 'user', component: showUser, props: true},
            {path: 'users/:id(\\d+)/edit', name: 'edit_user', component: editUser, props: true},
            // Clients
            {path: 'clients', name: 'clients', component: indexClient},
            {path: 'clients/new', name: 'new_client', component: newClient},
            {path: 'clients/:id(\\d+)', name: 'client', component: showClient, props: true},
            {path: 'clients/:id(\\d+)/edit', name: 'edit_client', component: editClient, props: true},
            //Fleet
            {path: 'fleets', name: 'fleets', component: indexFleet},
            {path: 'fleets/new', name: 'new_fleet', component: newFleet, props: true},
            {path: 'fleets/:id(\\d+)', name: 'fleet', component: showFleet, props: true},
            {path: 'fleets/:id(\\d+)/edit', name: 'edit_fleet', component: editFleet, props: true},

            //Vehicle
            {path: 'vehicles', name: 'vehicles', component: indexVehicle},
            {path: 'vehicles/new', name: 'new_vehicle', component: newVehicle, props: true},
            {path: 'vehicles/:id(\\d+)', name: 'vehicle', component: showVehicle, props: true},
            {path: 'vehicles/:id(\\d+)/edit', name: 'edit_vehicle', component: editVehicle, props: true}
        ],
    }
];

