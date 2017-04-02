<!--
    This is a form for creating/updating a fleet.
    The form accepts the old data and an update or create function.
-->
<template>
    <form-component v-if="fleet" @submit="proceed" :failroute="{name: 'fleets'}">
        <fleet-form-input :clients=clients :fleet="fleet"></fleet-form-input>
    </form-component>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import fleetFormInput from './fleetFormInput.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            formComponent, fleetFormInput
        },
        props: {
            submit: Function, //Function to create the fleet.
            oldFleet: Object,
            clientId: String
        },
        created(){
            this.fetchClients()
        },
        computed: {
            ...mapGetters([
                'clients'
            ]),
            fleet() {
                if(this.oldFleet===undefined){
                    return {company: this.clientId}
                }
                else {
                    return this.oldFleet
                }
            }
        },
        methods: {
            ...mapActions([
                'fetchClients'
            ]),
            proceed(){
                this.submit({fleet: this.fleet}).then(() => {
                    this.$router.push({name: 'fleets'})
                })

            }
        }

    }
</script>