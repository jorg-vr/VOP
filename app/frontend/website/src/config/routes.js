import App from '../components/App.vue'

import NewIdentity from '../components/identity/New.vue'
import EditIdentity from '../components/identity/Edit.vue'
import IndexIdentity from '../components/identity/Index.vue'
import RemoveIdentity from '../components/identity/Remove.vue'
import ShowIdentity from '../components/identity/Show.vue'

import NewFleet from '../components/fleet/new.vue'
import EditFleet from '../components/fleet/edit.vue'
import IndexFleet from '../components/fleet/index.vue'
import ShowFleet from '../components/fleet/show.vue'

import NewVehicle from '../components/vehicle/New.vue'
import EditVehicle from '../components/vehicle/Edit.vue'
import IndexVehicle from '../components/vehicle/Index.vue'
import RemoveVehicle from '../components/vehicle/Remove.vue'
import ShowVehicle from '../components/vehicle/Show.vue'

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
            { path: 'fleets/new', name: 'new_fleet', component: NewFleet },
            { path: 'fleets/:id', name: 'fleet', component: ShowFleet },
            { path: 'fleets/:id/edit', name: 'edit_fleet', component: EditFleet },

            //Vehicle
            { path: 'vehicles/', name: 'vehicles', component: IndexVehicle },
            { path: 'vehicles/new', name: 'new_vehicle', component: NewVehicle },
            { path: 'vehicles/:id', name: 'vehicle', component: ShowVehicle },
            { path: 'vehicles/:id/edit', name: 'edit_vehicle', component: EditVehicle },
            { path: 'vehicles/:id/remove', name: 'remove_vehicle', component: RemoveVehicle }
        ]
    },
        //TODO: Make a not found page!
        { path: '*', redirect: ''}
    ];
