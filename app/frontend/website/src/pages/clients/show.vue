<template>
    <div>
        <div class="page-header">
            <h1> Klant </h1>
        </div>
        <div class="col-md-8">
            <table id="show-vehicle" class="table">
                <tr>
                    <td>Naam </td>
                    <td>{{vehicle.name}}</td>
                </tr>
                <tr>
                    <td> Land </td>
                    <td>{{vehicle.address.country}}</td>
                </tr>
                <tr>
                    <td> Plaats </td>
                    <td>{{vehicle.address.city}}</td>
                </tr>
                <tr>
                    <td> Postcode </td>
                    <td>{{vehicle.address.postalCode}}</td>
                </tr>
                <tr>
                    <td> Straat </td>
                    <td>{{vehicle.address.street}}</td>
                </tr>
                <tr>
                    <td> Nummer</td>
                    <td>{{vehicle.address.houseNumber}}</td>
                </tr>
                <tr>
                    <td> BTW nummer </td>
                    <td>{{vehicle.vatNumber}}</td>
                </tr>
                <tr>
                    <td> Telefoonnummer</td>
                    <td>{{vehicle.phoneNumber}}</td>
                </tr>
            </table>
        </div>

    </div>
</template>
<script>
    export default {
        data: function(){
            return {
                vehicle: {
                    name: "Bedrijf",
                    address:{
                    	country:"Belgium",
                    	city:" Gent",
                    	street: "Pintelaan",
                    	houseNumber: "9",
                    	postalCode: "9000"
                    },
                    vatNumber: "6549846846",
                    phoneNumber: "0412 34 56 78"
                }
            }
        },
        created: function (){
            console.log('SHOWED')
        }
    }
</script>
<style>
    #show-vehicle td:first-child{
        font-weight: bold;
    }
</style>