import App from '../app.vue'

import NewUser from '../pages/users/new.vue'
import EditUser from '../pages/users/edit.vue'
import ShowUser from '../pages/users/show.vue'
import IndexUser from '../pages/users/index.vue'


import NewClient from '../pages/clients/new.vue'
import EditClient from '../pages/clients/edit.vue'
import ShowClient from '../pages/clients/show.vue'
import IndexClient from '../pages/clients/index.vue'


import NewFleet from '../pages/fleet/new.vue'
import IndexFleet from '../pages/fleet/index.vue'
import ShowFleet from '../pages/fleet/show.vue'
import EditFleet from '../pages/fleet/edit.vue'


import newVehicle from '../pages/vehicle/new.vue'
import editVehicle from '../pages/vehicle/edit.vue'
import showVehicle from '../pages/vehicle/show.vue'

export default [
    {
        path: '',
        component: App,
        children: [
            //User
            { path: 'users', name: 'users', component: IndexUser },
            { path: 'users/new', name: 'new_user',component: NewUser },  
            { path: 'users/:id', name: 'user', component: ShowUser },
            { path: 'users/:id/edit', name: 'edit_user    ', component: EditUser },
            // Clients
            { path: 'clients', name: 'clients', component: IndexClient },
            { path: 'clients/new', name: 'new_client',component: NewClient },
            { path: 'clients/:id', name: 'client', component: ShowClient },
            { path: 'clients/:id/edit', name: 'edit_client',component: EditClient },
            //Fleet
            { path: 'fleets', name: 'fleets', component: IndexFleet },
            { path: 'fleets/:id', name: 'fleet', component: ShowFleet },
            { path: 'fleets/new', name: 'new_fleet', component: NewFleet },
            { path: 'fleets/:id/edit', name: 'edit_fleet', component: EditFleet},


            //Vehicle
            { path: 'vehicles/new', name: 'new_vehicle', component: newVehicle },
            { path: 'vehicles/:id', name: 'vehicle', component: showVehicle },
            { path: 'vehicles/:id/edit', name: 'edit_vehicle', component: editVehicle }

        ]
    },
        //TODO: Make a not found page!
        { path: '*', redirect: ''}
    ];
