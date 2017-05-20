import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
	actions:{
        fetchInvoicePdf(context, {clientId, invoiceId}){
            return new Promise(resolve => {
                //VueResource cannot handle the responseType property.
                //For this API call we need to use XMLHttpRequests to set the responseType
                let oReq = new XMLHttpRequest();
                oReq.open("GET", Vue.http.options.root + '/' + utils.formatLocation(locations.INVOICE_PDF, {clientId, invoiceId}), true);
                oReq.setRequestHeader('Function', Vue.http.headers.common['Function'])
                oReq.setRequestHeader('Authorization', Vue.http.headers.common['Authorization'])
                oReq.responseType = "blob";
                oReq.onload = function(oEvent) {
                    resolve(oReq.response)
                };
                oReq.send();
            })
        }

	}

}
