<!--
    This is the form.vue for creating/updating a vehicle.
    The form.vue accepts the old vehicle and an update or create function.
-->
<template>
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-4 control-label">Nummerplaat</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Nummerplaat" v-model="vehicle.licensePlate">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Chassisnummer</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="Chassisnummber" v-model="vehicle.vin">
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
                <select class="form-control" v-model="vehicle.type">
                    <option v-for="vehicleType in vehicleTypes" v-bind:value="vehicleType.id">{{vehicleType.name}}</option>
                </select>
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
                <select class="form-control" v-model="vehicle.company">
                    <option v-for="company in companies" v-bind:value="company.id">{{company.name}}</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div>
                <div id="buttons">
                    <button type="button" class="btn btn-success btn-md" v-on:click="submit(vehicle)">
                        <i class="fa fa-check" aria-hidden="true"></i>
                    </button>
                    <router-link v-if="vehicle.fleet != null" :to="{name: 'fleet', params: {id: vehicle.fleet}}">
                        <button type="button" class="btn btn-danger btn-md">
                            <i class="fa fa-times" aria-hidden="true"></i>
                        </button>
                    </router-link>
                    <router-link v-else :to="{name: 'fleets'}">
                        <button type="button" class="btn btn-danger btn-md">
                            <i class="fa fa-times" aria-hidden="true"></i>
                        </button>
                    </router-link>
                </div>
            </div>
        </div>
    </form>
</template>
<script>
    export default {
        data() {
            return {
                vehicleTypes: [],
                companies: []
            }
        },
        created() {
            this.fetchVehicleTypes();
            this.fetchCompanies();
        },
        methods: {
            fetchVehicleTypes(){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicleTypes').then(response => {
                    const data = response.body.data;
                    for(let i=0; i<data.length; i++){
                        this.vehicleTypes.push(data[i]);
                    }
                })
            },
            fetchCompanies(){
                this.$http.get('https://vopro5.ugent.be/app/api/companies').then(response => {
                    const data = response.body.data;
                    for(let i=0; i<data.length; i++){
                        this.companies.push(data[i]);
                    }
                })
            }
        },
        props: {
            vehicle: Object, //Vehicle which should be created/updated with this form.vue.
            submit: Function //Submit function to create/update the vehicle.
        }
    }

</script>