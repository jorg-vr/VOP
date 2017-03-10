<template>
	<div>
          <div class="page-header">
             <h1> {{ this.$route.query.type }}  </h1>

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
		      <a v-for="u in users" href="#" class="list-group-item">
		        {{ u }}
		        <span class="pull-right">
		          <button class="btn btn-xs btn-info">
		            <i class="fa fa-pencil" aria-hidden="true"></i>
		          </button>
		          <button class="btn btn-xs btn-info">
		            <i class="fa fa-trash" aria-hidden="true"></i>
		          </button>
		        </span>
		      </a>
		    </div>

		</div>
	</div>

	</div>


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
			      ]
            }
        },
        methods:{

        }
    }
</script>