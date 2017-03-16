<!--
    This page shows a certain client in detail. 
    Name,address,vatnumber and phonenubmer for the client are displayed.
-->
<template>
    <div>
        <div class="page-header">
            <h1> Klant </h1>
        </div>
        <div class="col-md-8">
            <table id="show-client" class="table">
                <tr>
                    <td>Naam </td>
                    <td>{{client.name}}</td>
                </tr>
                <tr>
                    <td> Land </td>
                    <td>{{address.country}}</td>
                </tr>
                <tr>
                    <td> Plaats </td>
                    <td>{{address.city}}</td>
                </tr>
                <tr>
                    <td> Postcode </td>
                    <td>{{address.postalCode}}</td>
                </tr>
                <tr>
                    <td> Straat </td>
                    <td>{{address.street}}</td>
                </tr>
                <tr>
                    <td> Nummer</td>    
                    <td>{{address.houseNumber}}</td>
                </tr>
                <tr>
                    <td> BTW nummer </td>
                    <td>{{client.vatNumber}}</td>
                </tr>
                <tr>
                    <td> Telefoonnummer</td>
                    <td>{{client.phoneNumber}}</td>
                </tr>
            </table>
        </div>

    </div>
</template>
<script>
    export default {
        data: function(){
            return {
                client: {},
                address: {}
            }
        },
        methods:{
            // Function that makes an API call to fetch specific information for a certain client
            fetchClientInformation(){
                var id = this.$route.params.id
                this.$http.get('https://vopro5.ugent.be/app/api/companies/'+id).then(response => {
                    this.client = response.body
                    this.address=this.client.address
                 })
            }
        },
        // Lifecycle hook called when this component is created
        created : function (){
            this.fetchClientInformation()
        }
    }
</script>
<style>
    #show-client td:first-child{
        font-weight: bold;
    }
</style>