<template>
<div>
    <div class="page-header">
    <h1> Gebruiker </h1>
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
                  <li v-for="user in searchId" class="list-group-item">
                    {{ user.firstName }} {{ user.lastName }} {{ user.email }}
                    <span class="pull-right">
                      <button type="button" class="btn btn-primary btn-xs btn-primary" v-on:click="edit(user.id)" id="editButton">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                      </button>
                      <button type="button" class="btn btn-primary btn-xs btn-danger" v-on:click="remove(user.id)" id="removeButton">
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
                      <button type="button" class="btn btn-primary btn-xs btn-danger" v-on:click="remove(user.id)" id="removeButton">
                        <i class="fa fa-trash" aria-hidden="true"></i>
    </button>

</div>



</template>
<script>
    export default {
        data(){
        return {
            identityType: this.$route.query.type + ' zoeken',
            users: [],
            clients:[],
            search: '',
            loading: false
        }
        },
        methods:{
                // Methods for routing purposes 
                add: function () {
                        this.$router.push({ path: 'users/new', query: { type: this.$route.query.type  }})
                },
                edit : function (id){
                    alert( ' Edit ' + id)
                    // API CALL DIE GEGEVENS OPVRAAGT van user en springt naar edit page.
                    this.$router.push({ path: '/users/:id/edit', query: { type: this.$route.query.type, id: id }})
                },
                remove : function (id){
                     confirm("Wil u doorgaan met het verwijderen?")
                   
                    // API CALL VOOR VERWIJDEREN 

                    if(this.$route.query.type == "Gebruiker"){
                        console.log('https://vopro5.ugent.be/app/api/users/'+id)
                        this.$http.delete('https://vopro5.ugent.be/app/api/users/'+id).then(response => {
                                
                        })
                    }
                    else if(this.$route.query.type == "Klant"){
                        console.log('https://vopro5.ugent.be/app/api/companies/'+id)
                        this.$http.delete('https://vopro5.ugent.be/app/api/companies/'+id).then(response => {
                                
                        })
                    }

                    // remove user in table
                    // var i = this.users.indexOf(user);
                    // this.users.splice(i, 1)
                    

                },
        },
        computed: {
        searchId: function () { 
            var s=this.search.trim().toLowerCase();;
            var listID = []
            listID = this.users
            if(!this.search){
                return listID
            }
            else{
                console.log(listID)
                return listID.filter(function(number){
                    if(number.firstName.toLowerCase().indexOf(s) != -1 || number.lastName.toLowerCase().indexOf(s) != -1 || number.email.toLowerCase().indexOf(s) != -1){
                        return number
                    }
                })
            }

        },
        appendString : function (){
            var value = 'Gebruiker zoeken'
            return value
        },
        },
        mounted: function () {
            // Make ajax request to server according to type

                console.log('Get users object')
                // GET user objects
                this.$http.get('https://vopro5.ugent.be/app/api/users').then(response => {
                    this.users = response.body
                })
            }
            this.loading = true
        },
    }
</script>