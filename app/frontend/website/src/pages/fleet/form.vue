<template>
    <div>
        <div class="row">
            <div class="form-group">
                <input type="text" class="form-control input-sm"
                       placeholder="E-mail klant" v-model="newEmail">
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <input type="text" class="form-control input-sm"
                       placeholder="Naam vloot" v-model="newFleetName">
            </div>
        </div>
        <div class="row">
            <div>
                <div id="buttons">
                    <button class="btn btn-success btn-md" v-on:click="submit(newEmail, newFleetName)">
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
            return {
                newEmail: '',
                newFleetName: ''
            }
        },
        created(){
            this.newEmail = this.email;
            this.newFleetName = this.fleetName;
        },
        props: {
            email: String, //old email
            fleetName: String, //old name
            submit: Function //This function accepts 2 parameters, the e-mail and the name.
        }
    }

</script>