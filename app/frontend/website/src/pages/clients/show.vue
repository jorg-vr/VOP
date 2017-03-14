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
                    <td>{{client.address.country}}</td>
                </tr>
                <tr>
                    <td> Plaats </td>
                    <td>{{client.address.city}}</td>
                </tr>
                <tr>
                    <td> Postcode </td>
                    <td>{{client.address.postalCode}}</td>
                </tr>
                <tr>
                    <td> Straat </td>
                    <td>{{client.address.street}}</td>
                </tr>
                <tr>
                    <td> Nummer</td>    
                    <td>{{client.address.houseNumber}}</td>
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
                client: {
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
    #show-client td:first-child{
        font-weight: bold;
    }
</style>