<template>
    <div>
        <h1> {{msg}} </h1>
    	<form>	
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<div class="form-group">
										<input type="text" name="fname" id="fname" class="form-control input-sm" placeholder="Voornaam">
									</div>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<div class="form-group">
										<input type="text" name="lname" id="lname" class="form-control input-sm" placeholder="Achternaam">
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<input type="text" name="street" id="street" class="form-control input-sm" placeholder="Straatnaam">
									</div>
								</div>
								
								<div class="col-xs-2 col-sm-2 col-md-2  col-lg-2 ">
									<div class="form-group">
										<input type="text" name="nr" id="nr" class="form-control input-sm" placeholder="Nr">
									</div>
								</div>
								<div class="col-xs-4 col-sm-4 col-md-4  col-lg-4 ">
									<div class="form-group">
										<select class="form-control" id="sel1">
											<option hidden >Functie</option>
											<option>1</option>
											<option>2</option>
											<option>3</option>
											<option>4</option>
										</select>
									</div>
								</div>
								
							</div>

							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<div class="form-group">
										<input type="text" name="city" id="city" class="form-control input-sm" placeholder="Plaats">
									</div>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<div class="form-group">
										<input type="text" name="zip" id="zip" class="form-control input-sm" placeholder="Postcode">
									</div>
								</div>
							</div>

							<div class="form-group">
								<input type="email" name="email" id="email" class="form-control input-sm" placeholder="Email">
							</div>

							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<input type="text" name="number" id="number" class="form-control input-sm" placeholder="Telefoonnummer">
									</div>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<input type="text" name="bd" id="bd" class="form-control input-sm" placeholder="DD/MM/YY">
									</div>
								</div>
							</div>
						</form>

    </div>
</template>
<script>
    export default {
    	props: { msg: String }
    }
</script>
<style scoped>

</style>