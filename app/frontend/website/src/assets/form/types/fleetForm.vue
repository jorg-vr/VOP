<!--
    This is a form for creating/updating a fleet.
    fleetForm accepts the old data and an update or create function.
-->
<template>
    <form-component v-if="fleet">
        <form-input :placeholder="$t('common.name') + ' ' + $t('fleet.fleet') | capitalize" v-model="fleet.name"></form-input>
        <form-select optionKey="name" :options="clients" v-model="fleet.company"
                     :hiddenOption="$t('actions.select') + ' ' + $t('client.company')| capitalize ">
        </form-select>
        <div class="row">
            <button-success @click="proceed"></button-success>
            <button-fail :route="{name: 'fleets'}"></button-fail>
        </div>
    </form-component>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'
    import buttonFail from '../../buttons/buttonFail.vue'
    import buttonSuccess from '../../buttons/buttonSuccess.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            formComponent, formInput, formSelect, buttonFail, buttonSuccess
        },
        props: {
            submit: Function, //Function to create the fleet.
            oldFleet: Object
        },
        created(){
            if(this.fleet === undefined){
                this.fleet = {}
            }
            this.fetchClients()
        },
        computed: {
            ...mapGetters([
                'clients'
            ]),
            fleet() {
                if(this.oldFleet===undefined){
                    return {}
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