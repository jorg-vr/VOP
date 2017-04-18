import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {

	state: {
		invoice: {},
		invoices: [],
		filteredInvoices: [],

	},
	getters:{
		invoice(state) {
            return state.invoice
        },
        invoices(state){
        	return state.invoices
        },
        filteredInvoices(state){
        	return state.filteredInvoices
        },
		getInvoiceByAll: (state, getters) => (value) => {
            return getters.filterByAll(state.invoices, value)
        },

        getInvoicesByAllAdvanced: (state, getters) => (invoice) => {
            return getters.filterByAllAdvanced(state.invoices, invoice)
        }
	},
	mutations:{
		/* Mutation used for adding invoices to total collection of all invoices */
		addInvoices (state, payload) {
			console.log(payload)
			console.log(' in addInvoices mutation')
			console.log(payload.data)
			var i
			var data = payload.data
			
			for (i = 0; i < data.length; i++) {
				var obj = data[i]
				// add name and id of fleet and company for fetching / visual purposes
				obj.companyId = payload.companyId
				obj.companyName = payload.companyName
				// Add invoice tot total collection
				state.invoices.push(obj)
				state.filteredInvoices.push(obj)
			}	
	    },
	    setInvoice(state,payload){
	    	state.invoice=payload
	    },
	    setInvoices(state,payload){
	    	state.invoices=payload
	    },
	    setFilteredInvoices(state,payload){
	    	state.filteredInvoices=payload	
	    }

	},
	actions:{
		/* Add invoices from a company to all invoices -> GET /company/{id}/invoices
		payload must consist of two fields: companyId and companyName. This is for fetching and display purposes */
		addInvoicesCompany(context,payload){
			// used for local testing
			//context.commit('addInvoices',{data:[{endDate:'2016-08-29',id:payload.companyId,paid:'false',startDate:'2016-08-01',totalAmount:'44',type:'billing'}], companyId:payload.companyId,companyName:payload.companyName})
			return new Promise(resolve => {
			                RequestHandler.getObjectsRequest(locations.CLIENT+payload.companyId+'/'+locations.INVOICE).then(invoices => {
								//invoices are an Array
			                    context.commit('addInvoices', {data: invoices, companyId: payload.companyId, companyName: payload.companyName})
			                    resolve(invoices)
				})
			})
        },
        // Get one invoice
        fetchInvoice(context, payload){
        	console.log(payload)
        	console.log('API CALL NAAR: '+locations.CLIENT+payload.companyId+'/'+locations.INVOICE+payload.id)
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.CLIENT+payload.companyId+'/'+locations.INVOICE, payload.id).then(invoice => {
                    context.commit(setInvoice, {invoice})
                    resolve(invoice)
                })
            })
        },
        // Get one invoice in a specified format (currently only .pdf supported)
        exportAsPDF(context,payload){
        	console.log(locations.FLEET+payload.fleetId+'/'+locations.INVOICE+payload.id)
			return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.CLIENT+payload.companyId+locations.INVOICE+payload.id, '.pdf').then(invoice => {
                    context.commit(setInvoice, {invoice})
                    resolve(invoice)
                })
            })

        }

	}

}