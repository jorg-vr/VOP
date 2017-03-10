<template>
	<div>
          <div class="page-header">
             <h1> {{ this.$route.query.type }} verwijderen </h1>

          </div>
   	<div class="container">
		<div class="row">
	        <div class="col-md-6">
	            <div id="search_input">
	                <div class="input-group col-md-12">
	                    <input type="text" class="form-control input-mg" placeholder="Gebruiker zoeken" />
	                    <span class="input-group-btn">
	                        <button class="btn btn-info btn-mg" type="button">
	                            <i class="fa fa-search" aria-hidden="true"></i>
	                        </button>
	                    </span>
	                </div>
	            </div>
	        </div>
		</div>
	

	<div class="row">
		<div class="col-md-8" col-md-offset-6>
			
			<div class="list-group">
		      <a v-for="user in users" href="#" class="list-group-item">
		        {{ user }}
		        <span class="pull-right">
		          <button class="btn btn-xs btn-info" v-on:click="edit(user)">
		            <router-link to="/identity/:id/edit" class="button"> <i class="fa fa-pencil" aria-hidden="true"></i> </router-link>


		          </button>
		          <button class="btn btn-xs btn-info" v-on:click="remove(user)">
		            <i class="fa fa-trash" aria-hidden="true"></i>
		          </button>
		        </span>
		      </a>
		    </div>

		</div>
	</div>

	</div>
	<router-link :to="{ path: '/identity/:id/edit' , params: { user: '123' }}">Home</router-link>
	{{msg}}

</div>
</template>

<script>
    export default {
        data(){
            return {
            	users: [
			        'User1',
			        'User2',
			        'User3',
			        'User4'
			      ],
			     msg:'sdfsd'
            }
        },
        methods:{
        	remove : function (user){
        		alert( ' Remove ' + user)
        		var i = this.users.indexOf(user);
     			this.users.splice(i, 1)
     			// API CALL VOOR VERWIJDEREN VAN user

        		this.msg=user+' removed'
        	},
        	edit : function (user){
        		alert( ' Edit ' + user)
        		// API CALL DIE GEGEVENS OPVRAAGT van user en springt naar edit page.
        		this.$router.push({ path: '/identity/:id/edit', query: { type: this.$route.query.type, user: usersser }})
        		this.msg=user
        	},

        }
    }
</script>