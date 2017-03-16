<!--
    This page shows a certain user in detail. 
    Full name and email for the user are displayed
-->
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
            <router-link :to="{name: 'users'}"><button class="btn btn-default pull-left">Terug</button></router-link>
        </div>
    </div>
</template>
<script>
    export default {
        data: function(){
            return {
                user: {}
            }
        },
        methods:{
            // Function that makes an API call to fetch specific information for a certain user
            fetchUserInformation(){
                var id = this.$route.params.id
                this.$http.get('https://vopro5.ugent.be/app/api/users/'+id).then(response => {
                    this.user = response.body
                 })
            }
        },
        // Lifecycle hook called when this component is created
        created : function (){
            this.fetchUserInformation()
        }
    }
</script>
<style>
    #show-user td:first-child{
        font-weight: bold;
    }
</style>