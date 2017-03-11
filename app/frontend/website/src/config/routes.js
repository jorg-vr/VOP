import App from '../components/App.vue'

import NewIdentity from '../components/identity/New.vue'
import EditIdentity from '../components/identity/Edit.vue'
import IndexIdentity from '../components/identity/Index.vue'
import RemoveIdentity from '../components/identity/Remove.vue'
import ShowIdentity from '../components/identity/Show.vue'

import NewFleet from '../components/fleet/New.vue'
import EditFleet from '../components/fleet/Edit.vue'
import IndexFleet from '../components/fleet/Index.vue'
import ShowFleet from '../components/fleet/Show.vue'

import newVehicle from '../components/vehicle/new.vue'
import editVehicle from '../components/vehicle/edit.vue'
import showVehicle from '../components/vehicle/show.vue'

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
            { path: 'vehicles/new', name: 'new_vehicle', component: newVehicle },
            { path: 'vehicles/:id', name: 'vehicle', component: showVehicle },
            { path: 'vehicles/:id/edit', name: 'edit_vehicle', component: editVehicle }
        ]
    },
        //TODO: Make a not found page!
        { path: '*', redirect: ''}
    ];
