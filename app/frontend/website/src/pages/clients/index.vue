<!--
    This page shows all clients in the database.
    From this page a new client can be added or an existing client can be edited or removed.
-->
<template>
<div>
    <div class="page-header">
    <h1>Klanten</h1>
    </div>
    <!-- Render an info-pane for every clients -->
        <info-pane v-for="clients in clientList"
                    :textValues="new Array(clients.name)"
                   :remove="remove"
                   :objectId= "clients.id"
                   edit="edit_client"
                   show="client"
                   :key="clients.id">
        </info-pane>

        <button type="button" class="btn btn-primary btn-circle btn-lg" v-on:click="add()">+</button>

</div>



</template>
<script>
import infoPane from "../../assets/listComponent.vue"
    export default {
        components: {
            'info-pane': infoPane
        },
        data(){
        return {
            clientList : [],
            clients:{},
            search: ''
        }
        },
        methods:{
            // Methods for routing purposes
            // Route to adding a new client
            add: function () {
                this.$router.push({ path: 'clients/new'})
            },
            // Function to remove a client 
            remove : function (id){
                console.log(id);
                // API call to remove client from database
                this.$http.delete('https://vopro5.ugent.be/app/api/companies/'+id)
                // API call to remove user from database
                let newClients = this.clientList.filter(client => client.id !== id);
                this.clientList = newClients
            },
            // Function to fetch list of clients from database
            fetchClientList(){
                this.$http.get('https://vopro5.ugent.be/app/api/companies').then(response => {
                    const data = response.body.data;
                    for(let i=0; i<data.length; i++){
                        this.clientList.push(data[i]);
                    }
                 })
            }
        },
        computed: {
            // Computed property used for searching
            searchId: function () { 
                var s=this.search.trim().toLowerCase();;
                var listID = []
                listID = this.clients
                if(!this.search){
                    return listID
                }
                else{
                    console.log(listID)
                    return listID.filter(function(number){
                        if(number.name.toLowerCase().indexOf(s) != -1){
                            return number
                        }
                    })
                }
            },
            // Computed property used as a placeholder in the searchbar
            appendString : function (){
                var value = 'Klant zoeken'
                return value
            },
        },
        // Lifecycle hook called when this component is created
        created: function () {
            this.fetchClientList()
        },
    }
</script>

<style>
    .btn-circle.btn-lg {
        position: fixed;
        left: 230px;
        bottom: 40px;
        width: 50px;
        height: 50px;
        padding: 10px 16px;
        font-size: 18px;
        line-height: 1.33;
        border-radius: 25px;
}
</style>