<!--
    This is a form for creating/updating a fleet.
    fleetForm accepts the old data and an update or create function.
-->
<template>
    <form-component>
        <form-input :placeholder="$t('common.name') + ' ' + $t('fleet.fleet') | capitalize" v-model="fleet.name"></form-input>
        <form-select optionKey="name" :options="clients" v-model="fleet.company"
                     :hiddenOption="$t('actions.select') + ' ' + $t('client.company')| capitalize ">
        </form-select>
        <div class="row">
            <button-action @click="proceed" buttonClass="btn btn-success btn-md"><i class="fa fa-check"></i></button-action>
            <button-link :route="{name: 'fleets'}" buttonClass="btn btn-danger btn-md"><i  class="fa fa-times"></i></button-link>
        </div>
    </form-component>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import formInput from '../formInput.vue'
    import formSelect from '../formSelect.vue'
    import buttonLink from '../../buttons/buttonLink.vue'
    import buttonAction from '../../buttons/buttonAction.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            formComponent, formInput, formSelect, buttonLink, buttonAction
        },
        props: {
            submit: Function, //Function to create the fleet.
            fleet: Object
        },
        created(){
            this.fetchClients()
        },
        computed: {
            ...mapGetters([
                'clients'
            ])
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