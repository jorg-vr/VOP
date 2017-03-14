<!--
    This is the form for creating/updating a fleet.
    The form accepts the old data and an update or create function.
-->
<template>
    <div>
        <div class="row">
            <div class="form-group">
                <input type="text" class="form-control input-sm"
                       placeholder="Naam vloot" v-model="fleet.name">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">Leasing bedrijf</label>
            <div class="col-sm-8">
                <select class="form-control" v-model="fleet.company">
                    <option v-for="company in companies" v-bind:value="company.id">{{company.name}}</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div>
                <div id="buttons">
                    <button class="btn btn-success btn-md" v-on:click="submit(fleet)">
                        <i class="fa fa-check" aria-hidden="true"></i>
                    </button>
                    <router-link :to="{name: 'fleets'}">
                        <button type="button" class="btn btn-danger btn-md">
                            <i class="fa fa-times" aria-hidden="true"></i>
                        </button>
                    </router-link>

                </div>
            </div>
        </div>
    </div>

</template>
<script>
    export default {
        data() {
            //The values for the fleet.
            return {
                fleet: {
                    name: '',
                    company: ''
                }
            }
        },
        created(){
            //Set the values for the fleet equal to possible old values of the fleet
            //in case of an update.
            this.newEmail = this.email;
            this.newFleetName = this.fleetName;
        },
        props: {
            email: String, //old email
            fleetName: String, //old name
            //This function accepts 2 parameters, the e-mail and the name.
            //It should create or upate a fleet.
            submit: Function
        }
    }

</script>