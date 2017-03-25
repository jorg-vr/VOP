<!--
    This is fleetForm.vue.vue for creating/updating a fleet.
    fleetForm.vue.vue accepts the old data and an update or create function.
-->
<template>
    <form-component>
        <form-input placeholder="Naam vloot" v-model="fleet.name"></form-input>
        <form-select label="bedrijf" optionKey="name" :options="clients" v-model="fleet.company"></form-select>
        <div class="row">
            <button-action @click="proceed" buttonClass="btn btn-success btn-md"><i class="fa fa-check"></i></button-action>
            <button-link :route="{name: 'fleets'}" buttonClass="btn btn-danger btn-md"><i  class="fa fa-times"></i></button-link>
        </div>
    </form-component>
</template>
<script>
    import formInput from '../formInput.vue'
    import formSelect from '../formSelect.vue'
    import buttonLink from '../../buttons/buttonLink.vue'
    import buttonAction from '../../buttons/buttonAction.vue'
    import formComponent from '../formComponent.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        props: {
            fleet: Object,
            submit: Function //Function to create the fleet.
        },
        components: {
            formInput, formSelect, buttonLink, buttonAction, formComponent
        },
        created(){
            this.getClients()
        },
        computed: {
            ...mapGetters([
                'clients'
            ])
        },
        methods: {
            ...mapActions([
                'getClients'
            ]),
            proceed(){
                this.submit({fleet: this.fleet}).then(() => {
                    this.$router.push({name: 'fleets'})
                })

            }
        }

    }

</script>