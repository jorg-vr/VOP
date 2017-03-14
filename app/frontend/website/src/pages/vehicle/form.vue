<!--
    This is the form for creating/updating a vehicle.
    The form accepts the old vehicle and an update or create function.
-->
<template>
    <form class="form-horizontal col-md-6">
        <div class="form-group">
            <label class="col-sm-4 control-label">Nummerplaat</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Nummerplaat" v-model="vehicle.licensePlate"">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Chassisnummer</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Chassisnummber" v-model="vehicle.chassisNumber">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Merk</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Merk" v-model="vehicle.brand">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Model</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Model"v-model="vehicle.model">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Type</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Type"v-model="vehicle.type">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Kilometerafstand</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Kilometerafstand" v-model="vehicle.mileage">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Productiejaar</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Productiedatum" v-model="vehicle.year">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Leasing bedrijf</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Leasing bedrijf" v-model="vehicle.leasingCompany">
            </div>
        </div>
        <button type="button" class="btn btn-default float-left" v-on:click="submit">Voeg toe</button>
    </form>
</template>
<script>
    export default {
        data() {
            return {}
        },
        props: {
            vehicle: Object, //Vehicle which should be created/updated with this form.
            submit: Function //Submit function to create/update the vehicle.
        }
    }

</script>