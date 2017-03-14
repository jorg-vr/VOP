<template>
<div>
    <div class="page-header">
    <h1> Klant </h1>
    </div>

        <div class="row">
            <div class="col-md-4">
                <div id="search_input">
                    <div class="input-group col-md-12 ">
                        <input type="text" class="form-control input-mg" v-bind:placeholder=appendString v-model="search"/>
                        <span class="input-group-btn">
                            <button class="btn btn-primary btn-mg" type="button">
                                <i class="fa fa-search" aria-hidden="true"></i>
                            </button>
                        </span>
                    </div>
                </div>
            </div>
           

            <div class="col-md-1 " >
                <span class="input-group-btn">
                            <button type="button" class="btn btn-primary btn-sm" id="addButton" v-on:click="add">
                                <i class="fa fa-plus" aria-hidden="true"></i>
                            </button>
                </span>
            </div>
        </div>
    

    <div class="content" v-show=loading>
        <div class="row">
            <div class="col-md-8" col-md-offset-6>
                
                <div class="list-group">
                  <li v-for="client in searchId" class="list-group-item">
                    {{ client.name }} {{ client.vatNumber }} {{ client.phoneNumber }}
                    <span class="pull-right">
                      <button type="button" class="btn btn-primary btn-xs btn-primary" v-on:click="edit(client.id)" id="editButton">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                      </button>
                      <button type="button" class="btn btn-primary btn-xs btn-danger" v-on:click="remove(client.id)" id="removeButton">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                      </button>
                    </span>
                  </li>
                </div>

            </div>
        </div>

    </div>

    <!-- Temp button because server is down -->
    <button type="button" class="btn btn-primary btn-xs btn-primary" v-on:click="edit(123)" id="editButton">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                      </button>
                      <button type="button" class="btn btn-primary btn-xs btn-danger" v-on:click="remove(client.id)" id="removeButton">
                        <i class="fa fa-trash" aria-hidden="true"></i>
    </button>

</div>



</template>
<script>
    export default {
        data(){
        return {
            users: [],
            clients:{"test":"kkk"},
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