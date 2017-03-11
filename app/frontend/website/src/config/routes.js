import App from '../components/App.vue'
import NewIdentity from '../components/identity/New.vue'
import EditIdentity from '../components/identity/Edit.vue'
import IndexIdentity from '../components/identity/Index.vue'
import RemoveIdentity from '../components/identity/Remove.vue'
import ShowIdentity from '../components/identity/Show.vue'

import NewFleet from '../components/fleet/New.vue'
import EditFleet from '../components/fleet/Edit.vue'
import IndexFleet from '../components/fleet/Index.vue'
import RemoveFleet from '../components/fleet/Remove.vue'
import ShowFleet from '../components/fleet/Show.vue'

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
            { path: 'identity', component: IndexIdentity },
            { path: 'identity/new', component: NewIdentity },
            { path: 'identity/:id', component: ShowIdentity },
            { path: 'identity/:id/edit', component: EditIdentity },
            { path: 'identity/:id/remove', component: RemoveIdentity },
            //Fleet
            { path: 'fleet', component: IndexFleet },
            { path: 'fleet/new', component: NewFleet },
            { path: 'fleet/:id', component: ShowFleet },
            { path: 'fleet/:id/edit', component: EditFleet },
            { path: 'fleet/:id/remove', component: RemoveFleet },
            //Vehicle
            { path: 'vehicle', name: 'vehicles', component: IndexVehicle },
            { path: 'vehicle/new', name: 'new_vehicle', component: NewVehicle },
            { path: 'vehicle/:id', name: 'vehicle', component: ShowVehicle },
            { path: 'vehicle/:id/edit', name: 'edit_vehicle', component: EditVehicle },
            { path: 'vehicle/:id/remove', name: 'remove_vehicle', component: RemoveVehicle }
        ]
    },
        //TODO: Make a not found page!
        { path: '*', redirect: ''}
    ];
