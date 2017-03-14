import App from '../app.vue'

import NewIdentity from '../pages/identity/New.vue'
import EditIdentity from '../pages/identity/Edit.vue'
import IndexIdentity from '../pages/identity/Index.vue'
import RemoveIdentity from '../pages/identity/Remove.vue'
import ShowIdentity from '../pages/identity/Show.vue'

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
            //Identity
            { path: 'identities', name: 'identities', component: IndexIdentity },
            { path: 'identities/new', component: NewIdentity },
            { path: 'identities/:id', component: ShowIdentity },
            { path: 'identities/:id/edit', component: EditIdentity },
            { path: 'identities/:id/remove', component: RemoveIdentity },

            //Fleet
            { path: 'fleets', name: 'fleets', component: IndexFleet },
            { path: 'fleets/(\d+)', name: 'fleet', component: ShowFleet },
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
