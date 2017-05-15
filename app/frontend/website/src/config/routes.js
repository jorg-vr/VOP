/**
 * All possible routes in the web application.
 * See VueRouter
 */

import App from '../app.vue'

import homeClient from '../pages/homeClient.vue'

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
import fleetLogs from '../pages/fleet/logs.vue'
import fleetLog from '../pages/fleet/log.vue'

import indexVehicle from '../pages/vehicle/index.vue'
import newVehicle from '../pages/vehicle/new.vue'
import editVehicle from '../pages/vehicle/edit.vue'
import showVehicle from '../pages/vehicle/show.vue'
import vehicleLogs from '../pages/vehicle/logs.vue'
import vehicleLog from '../pages/vehicle/log.vue'


import indexVehicleType from '../pages/vehicleType/index.vue'
import newVehicleType from '../pages/vehicleType/new.vue'
import editVehicleType from '../pages/vehicleType/edit.vue'
import showVehicleType from '../pages/vehicleType/show.vue'

import newSurety from '../pages/surety/new.vue'
import editSurety from '../pages/surety/edit.vue'
import showSurety from '../pages/surety/show.vue'

import newVehicleInsurance from '../pages/vehicleInsurance/new.vue'
import editVehicleInsurance from '../pages/vehicleInsurance/edit.vue'
import showVehicleInsurance from '../pages/vehicleInsurance/show.vue'

import indexContract from '../pages/contract/index.vue'
import newContract from '../pages/contract/new.vue'
import editContract from '../pages/contract/edit.vue'
import showContract from '../pages/contract/show.vue'

import indexSpecialCondition from '../pages/specialCondition/index.vue'
import newSpecialCondition from '../pages/specialCondition/new.vue'
import editSpecialCondition from '../pages/specialCondition/edit.vue'
import showSpecialCondition from '../pages/specialCondition/show.vue'

import indexInvoice from '../pages/invoice/index.vue'
import showInvoice from '../pages/invoice/show.vue'


import exception from '../pages/exception/exception.vue'
import login from '../pages/login/login.vue'

export default [
    {
        path: '',
        component: App,
        children: [
            {path: 'home', name: 'homeClient', component: homeClient},
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
            {path: 'fleets/:id(\\d+)/logs', name: 'fleet_logs', component: fleetLogs, props: true},
            {path: 'fleets/:resourceId(\\d+)/logs/:id(\\d+)', name: 'fleet_log', component: fleetLog, props: true},

            //Vehicle
            {path: 'vehicles', name: 'vehicles', component: indexVehicle},
            {path: 'fleets/:fleetId(\\d+)/vehicles/new', name: 'new_vehicle', component: newVehicle, props: true},
            {path: 'vehicles/:id(\\d+)', name: 'vehicle', component: showVehicle, props: true},
            {path: 'vehicles/:id(\\d+)/edit', name: 'edit_vehicle', component: editVehicle, props: true},
            {path: 'vehicles/:id(\\d+)/logs', name: 'vehicle_logs', component: vehicleLogs, props: true},
            {path: 'vehicles/:resourceId(\\d+)/logs/:id(\\d+)', name: 'vehicle_log', component: vehicleLog, props: true},


            //VehicleType
            {path: 'vehicles/types', name: 'vehicleTypes', component: indexVehicleType},
            {path: 'vehicles/types/new', name: 'new_vehicleType', component: newVehicleType},
            {path: 'vehicles/types/:id(\\d+)', name: 'vehicleType', component: showVehicleType, props: true},
            {path: 'vehicles/types/:id(\\d+)/edit', name: 'edit_vehicleType', component: editVehicleType, props: true},

            // sureties
            {path: 'sureties', name: 'suretys', component: indexContract},
            {path: 'sureties/new', name: 'new_surety', component: newSurety},
            {path: 'sureties/:id(\\d+)', name: 'surety', component: showSurety, props: true},
            {path: 'sureties/:id(\\d+)/edit', name: 'edit_surety', component: editSurety, props: true},
            
            // contracts
            {path: 'contracts', name: 'contracts', component: indexContract},
            {path: 'contracts/new', name: 'new_contract', component: newContract},
            {path: 'contracts/:id(\\d+)', name: 'contract', component: showContract, props: true},
            {path: 'contracts/:id(\\d+)/edit', name: 'edit_contract', component: editContract, props: true},

            // vehicle insurances
            {path: 'contracts/:contractId(\\d+)/insurances', name: 'insurances', component: indexContract, props: true},
            {path: 'contracts/:contractId(\\d+)/insurances/new', name: 'new_insurance', component: newVehicleInsurance, props: true},
            {path: 'contracts/:contractId(\\d+)/insurances/:id(\\d+)', name: 'insurance', component: showVehicleInsurance, props: true},
            {path: 'contracts/:contractId(\\d+)/insurances/:id(\\d+)/edit', name: 'edit_insurance', component: editVehicleInsurance, props: true},
            
            // special conditions
            {path: 'conditions', name: 'conditions', component: indexSpecialCondition},
            {path: 'conditions/new', name: 'new_condition', component: newSpecialCondition},
            {path: 'conditions/:id(\\d+)', name: 'condition', component: showSpecialCondition, props: true},
            {path: 'conditions/:id(\\d+)/edit', name: 'edit_condition', component: editSpecialCondition, props: true},

            // invoice
            {path: 'clients/:companyId(\\d+)/invoices', name: 'invoices', component: indexInvoice, props: true},
            {path: 'clients/:companyId(\\d+)/invoices/:id(\\d+)', name: 'invoice', component: showInvoice, props: true}
        ],
    },
    {path: '/login', name: 'login', component: login},
    {path: '*', component: exception, props: {statusCode: 404}}
];

