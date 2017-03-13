import App from '../app.vue'

import NewUser from '../pages/users/new.vue'
import EditUser from '../pages/users/edit.vue'
import IndexUser from '../pages/users/index.vue'


import NewClient from '../pages/clients/new.vue'
import EditClient from '../pages/clients/edit.vue'
import IndexClient from '../pages/clients/index.vue'


import NewFleet from '../pages/fleet/new.vue'
import IndexFleet from '../pages/fleet/index.vue'
import ShowFleet from '../pages/fleet/show.vue'
import NewSubfleet from '../pages/subfleet/new.vue'


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
            { path: 'users/new', component: NewUser },  
            { path: 'users/:id/edit', component: EditUser },
            // Clients
            { path: 'clients', name: 'clients', component: IndexClient },
            { path: 'clients/new', component: NewClient },
            { path: 'clients/:id/edit', component: EditClient },
            //Fleet
            { path: 'fleets', name: 'fleets', component: IndexFleet },
            { path: 'fleets/new', name: 'new_fleet', component: NewFleet },
            { path: 'fleets/:id', name: 'fleet', component: ShowFleet },
            { path: 'fleets/:id/new', name: 'new_subfleet', component: NewSubfleet},

            //Vehicle
            { path: 'vehicles/new', name: 'new_vehicle', component: newVehicle },
            { path: 'vehicles/:id', name: 'vehicle', component: showVehicle },
            { path: 'vehicles/:id/edit', name: 'edit_vehicle', component: editVehicle }

        ]
    },
        //TODO: Make a not found page!
        { path: '*', redirect: ''}
    ];
