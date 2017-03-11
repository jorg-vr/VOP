<template>
<div>
    <div class="page-header">
    <h1> {{this.$route.query.type }}</h1>
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
              <div class="col-md-3" >
                <div class="form-group">
                                        <select class="form-control" id="sel1">
                                            <option hidden > zoeken op </option>
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                        </select>
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
                  <li v-for="user in users" class="list-group-item">
                    {{ user.name }}
                    <span class="pull-right">
                      <button type="button" class="btn btn-primary btn-xs btn-primary" v-on:click="edit(user)" id="editButton">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                      </button>
                      <button type="button" class="btn btn-primary btn-xs btn-danger" v-on:click="remove(user)" id="removeButton">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                      </button>
                    </span>
                  </li>
                </div>

            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <ul class="pagination">
                    <li><a href="#">&laquo;</a></li>
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">&raquo;</a></li>
                </ul>
            </div>
        </div>
    </div>


</div>



</template>
<script>
    export default {
        data(){
        return {
            identityType: this.$route.query.type + ' zoeken',
                users: [
                    {
                      "name": "Tom",
                      "id": 1
                    },
                    {
                      "name": "Bob",
                      "id": 2
                    },
                    {
                      "name": "Frank",
                      "id": 3
                    },
                    {
                      "name": "Emma",
                      "id": 3
                    },
                    {
                      "name": "George",
                      "id": 3
                    },
                    {
                      "name": "Hendrik",
                      "id": 3
                    },
                    {
                      "name": "Jolien",
                      "id": 3
                    },
                    {
                      "name": "Lisa",
                      "id": 3
                    },
                    {
                      "name": "Kelly",
                      "id": 3
                    },
                    {
                      "name": "Frank",
                      "id": 3
                    },
                  ],
            clients: [
                    {
                      "name": "Bedrijf1",
                      "id": 1
                    },
                    {
                      "name": "Bedrijf3",
                      "id": 1
                    },
                    {
                      "name": "Bedrijf4",
                      "id": 1
                    },
                    {
                      "name": "Bedrijf5",
                      "id": 1
                    },
                    {
                      "name": "Bedrijf6",
                      "id": 1
                    },
                    
                  ],  
            search: '',
            loading: false
        }
        },
        methods:{
                // Methods for routing purposes 
                add: function () {
                        this.$router.push({ path: 'identities/new', query: { type: this.$route.query.type  }})
                },
                edit : function (user){
                    alert( ' Edit ' + user)
                    // API CALL DIE GEGEVENS OPVRAAGT van user en springt naar edit page.
                    this.$router.push({ path: '/identities/:id/edit', query: { type: this.$route.query.type, user: user }})
                },
                remove : function (user){
                    alert( ' Remove ' + user)
                    var i = this.users.indexOf(user);
                    this.users.splice(i, 1)
                    // API CALL VOOR VERWIJDEREN VAN user
                },
        },
        computed: {
        
        appendString : function (){
            var value = [this.$route.query.type + ' zoeken']
            return value
        },
        },
        created: function () {
            this.loading = true
        },
    }
</script>