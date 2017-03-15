<template>
    <div>
        <div class="page-header">
            <h1> Gebruiker </h1>
        </div>
        <div class="col-md-8">
            <table id="show-user" class="table">
                <tr>
                    <td> Voornaam </td>
                    <td>{{user.firstName}}</td>
                </tr>
                <tr>
                    <td> Achternaam </td>
                    <td>{{user.lastName}}</td>
                </tr>
                <tr>
                    <td> Email </td>
                    <td>{{user.email}}</td>
                </tr>
            </table>
        </div>

    </div>
</template>
<script>
    export default {
        data: function(){
            return {
                user: {
                    firstName: "Bob",
                    lastName: "B",
                    email:"bob@mail.be"
                }
            }
        },
    }
</script>
<style>
    #show-user td:first-child{
        font-weight: bold;
    }
</style>