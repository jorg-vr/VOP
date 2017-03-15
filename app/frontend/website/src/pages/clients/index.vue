<template>
<div>
    <div class="page-header">
    <h1> Klant </h1>
    </div>
    

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
            users: [],
            clientList : [ 
                    {id: 1, name: 'Client1'},
                    {id: 2, name: 'Client2'},
                    {id: 3, name: 'Client3'},
                    {id: 4, name: 'Client4'}
                ],
            clients:{},
            search: ''
        }
        },
        methods:{
                // Methods for routing purposes 
                add: function () {
                        this.$router.push({ path: 'clients/new'})
                },
                remove : function (id){
                     confirm("Wil u doorgaan met het verwijderen?")
                   
                    // API CALL VOOR VERWIJDEREN 
                        console.log('https://vopro5.ugent.be/app/api/clients/'+id)
/*                        this.$http.delete('https://vopro5.ugent.be/app/api/clients/'+id).then(response => {
                                
                        })
                    */

                    // remove user in table
                    // var i = this.users.indexOf(user);
                    // this.users.splice(i, 1)
                    

                },
        },
        computed: {
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
            appendString : function (){
                var value = 'Klant zoeken'
                return value
            },
        },
        mounted: function () {
            // Make ajax request to server according to type
                console.log('Get companies objects')

/*                this.$http.get('https://vopro5.ugent.be/app/api/companies').then(response => {
                    console.log(response.body.data)
                    console.log(this.clientList)
                    this.clientList = response.body.data
                    console.log(this.clientList)
                })*/
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