/**
 * All possible routes in the web application.
 * See VueRouter
 */

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

import newSurety from '../pages/surety/new.vue'
import editSurety from '../pages/surety/edit.vue'
import showSurety from '../pages/surety/show.vue'

import indexInsurance from '../pages/insurance/index.vue'
import newInsurance from '../pages/insurance/new.vue'
import editInsurance from '../pages/insurance/edit.vue'
import showInsurance from '../pages/insurance/show.vue'

import indexInvoice from '../pages/invoice/index.vue'
import showInvoice from '../pages/invoice/show.vue'


import exception from '../pages/exception/exception.vue'
import login from '../pages/login/login.vue'

export default [
    {
        path: '',
        component: App,
        name: 'home',
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
            {path: 'vehicles/:id(\\d+)/edit', name: 'edit_vehicle', component: editVehicle, props: true},


            // sureties
            {path: 'surety/new', name: 'new_surety', component: newSurety},
            {path: 'surety/:id(\\d+)', name: 'surety', component: showSurety, props: true},
            {path: 'surety/:id(\\d+)/edit', name: 'edit_surety', component: editSurety, props: true},   

            // insurance
            {path: 'insurances', name: 'insurances', component: indexInsurance},
            {path: 'insurances/new', name: 'new_insurance', component: newInsurance},
            {path: 'insurances/:id(\\d+)', name: 'insurance', component: showInsurance, props: true},
            {path: 'insurances/:id(\\d+)/edit', name: 'edit_insurance', component: editInsurance, props: true},

            // invoice
            {path: 'clients/:companyId(\\d+)/invoices', name: 'invoices', component: indexInvoice},
            {path: 'clients/:companyId(\\d+)/invoices/:id(\\d+)', name: 'invoice', component: showInvoice, props: true}
        ],
    },
    {path: '/login', name: login, component: login},
    {path: '*', component: exception, props: {statusCode: 404}}
];

