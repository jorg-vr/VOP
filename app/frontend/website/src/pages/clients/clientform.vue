<template>
	<div>
		<form>	
			<div class="row" v-for="a in this.at">
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<div class="form-group">
						<input type="text" name="fname" id="fname" class="form-control input-sm" v-bind:placeholder=a>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-offset-7">
					<div id="buttons">
						<button type="button" class="btn btn-success btn-md" v-on:click="proceed()"> <i class="fa fa-check" aria-hidden="true"></i></button>    
						<button type="button" class="btn btn-danger btn-md" v-on:click="cancel()"> <i class="fa fa-times" aria-hidden="true"></i></button>  
					</div>
				</div>
			</div>
		</form>

	</div>
</template>
<script>
	export default {
		props: { at: Array, type: String },
		data(){
			return {
			}
		},
		methods: {
		},
		created: function () {
		},
		methods: {
    		createUserObject : function () {
    			return {firstName:this.field1,lastName:this.field2,email:this.field7}
    		},
    		createCompanyObject : function () {
    			return {name:this.field1,vatNumber:this.field9,phoneNumber:this.field8,address:{country:this.field3,city:this.field5,street:this.field3,houseNumber:this.field4,postalCode:this.field6}}
    		},
    		proceed : function (){

    			var dest=''

    			if(this.$route.path == '/clients/new'){
    				console.log('Go to proceed Add')
    				dest='proceedAddClient'
    			}
    			else if(this.$route.path == '/clients/:id/edit'){
    				console.log('Go to edit Add')
    				dest='proceedEditClient'
    			}

    			console.log('Child (Form) fired proceed method')
   				if(this.type=="Gebruiker"){
   					var User = this.createUserObject()
    				this.$bus.$emit(dest,User)
    			}
    			else if(this.type=="Klant"){
    				var Company = this.createCompanyObject()
    				this.$bus.$emit(dest,Company)
    			}
    		},
    		cancel : function (){
    			console.log('Child (Form) fired cancel method')
    			this.$router.go(-1)
    		},


    	},
    	mounted : function(){ 
    		var vm = this;
    		// listen to event fired by Edit Vue
    		this.$bus.$on('getEditInfo', function(input,type){
    			//TODO

    		});

    	}
	}
</script>
<style scoped>

</style>