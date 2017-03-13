<template>
    <div>
    	<form>	
							<div class="row">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
									<div class="form-group">
										<input type="text" name="fname" id="fname" class="form-control input-sm" v-bind:placeholder=fname>
									</div>
								</div>
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
									<div class="form-group">
										<input type="text" name="lname" id="lname" class="form-control input-sm" placeholder="Achternaam" v-show=lname>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
									<div class="form-group">
										<input type="text" name="street" id="street" class="form-control input-sm" placeholder="Straatnaam">
									</div>
								</div>
								
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
									<div class="form-group">
										<input type="text" name="nr" id="nr" class="form-control input-sm" placeholder="Nr">
									</div>
								</div>
								<div class="col-xs-3 col-sm-3 col-md-3  col-lg-3 ">
									<div class="form-group">
										<select class="form-control" id="sel1">
											<option hidden > {{selectname}} </option>
											<option>1</option>
											<option>2</option>
											<option>3</option>
											<option>4</option>
										</select>
									</div>
								</div>
								
							</div>

							<div class="row">
								<div class="col-xs-4 col-sm-4 col-md-4  col-lg-4 ">
									<div class="form-group">
										<input type="text" name="city" id="city" class="form-control input-sm" placeholder="Plaats">
									</div>
								</div>
								<div class="col-xs-4 col-sm-4 col-md-4  col-lg-4 ">
									<div class="form-group">
										<input type="text" name="zip" id="zip" class="form-control input-sm" placeholder="Postcode">
									</div>
								</div>
							</div>


							<div class="row">
                                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                                    <div class="form-group">
                                        <input type="email" name="email" id="email" class="form-control input-sm" placeholder="Email">
                                    </div>
                                </div>
                            </div>


							<div class="row">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
									<div class="form-group">
										<input type="text" name="number" id="number" class="form-control input-sm" v-bind:placeholder=nr1>
									</div>
								</div>
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
									<div class="form-group">
										<input type="text" name="bd" id="bd" class="form-control input-sm" v-bind:placeholder=nr2>
									</div>
								</div>
							</div>
						</form>

    </div>
</template>
<script>
    export default {
    	props: { msg: String },
    	data(){
        return {
            identityType: this.$route.query.type,
            fname: '',
            lname: '',
            selectname: '',
            nr1:'',
            nr2:''

        	}
        },
    	methods: {
    		determineForm : function (){
    			// determine form for either a user or a client
    			if(this.msg=="Gebruiker"){
    				this.fname = "Voornaam"
    				this.lname = true
    				this.selectname="Functie"
    				this.nr1 = "Telefoonnummer"
    				this.nr2 = "DD/MM/YY"
    			}
    			else if(this.msg="Klant"){
    				this.fname = "Bedrijfsnaam"
    				this.lname = false
    				this.selectname="Vennootschapstype"
					this.nr1 = "Rekeningnummer"
    				this.nr2 = "BTW-nummer"
    			}
    		}
    	},
    	created: function () {
            this.determineForm()
        },
    }
</script>
<style scoped>

</style>