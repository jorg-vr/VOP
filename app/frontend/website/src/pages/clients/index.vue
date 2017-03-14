<template>
<div>
    <div class="page-header">
    <h1> Klant </h1>
    </div>
    

        <info-pane v-for="clients in clientList"
                    :textValues="new Array(clients.name)"
                   :remove="remove"
                   :objectId= "clientList.id"
                   edit="edit_client"
                   show="clients"
                   :key="clientList.id">
        </info-pane>

        <button type="button" class="btn btn-primary btn-circle btn-lg">+</button>

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
                    {id: 1, name: 'C1'},
                    {id: 2, name: 'C2'},
                    {id: 3, name: 'C3'},
                    {id: 4, name: 'C4'}
                ],
            search: '',
            loading: false
        }
        },
        methods:{
                // Methods for routing purposes 
                add: function () {
                        this.$router.push({ path: 'clients/new', query: { type: this.$route.query.type  }})
                },
                edit : function (id){
                    alert( ' Edit ' + id)
                    // API CALL DIE GEGEVENS OPVRAAGT van user en springt naar edit page.
                    this.$router.push({ path: '/clients/:id/edit', query: { type: this.$route.query.type, id: id }})
                },
                remove : function (id){
                     confirm("Wil u doorgaan met het verwijderen?")
                   
                    // API CALL VOOR VERWIJDEREN 
                        console.log('https://vopro5.ugent.be/app/api/clients/'+id)
                        this.$http.delete('https://vopro5.ugent.be/app/api/clients/'+id).then(response => {
                                
                        })
                    

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
                console.log('Get companies object')
                this.$http.get('https://vopro5.ugent.be/app/api/companies').then(response => {
                    console.log(response.body.data)
                    console.log(this.clients)
                    this.clients = response.body.data
                    console.log(this.clients)
                })
            this.loading = true
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