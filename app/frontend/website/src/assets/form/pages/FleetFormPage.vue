<!--
    This page is used to edit a certain fleet.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <fleet-form :actions="actions" :oldFleet="fleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from '../../../assets/form/types/fleetForm.vue'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('fleet', this.actions.name)
            }
        },
        components: {
            FleetForm
        },
        created(){
            if(this.id){
                this.fetchFleet({id: this.id})
            }
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        computed: {
            ...mapGetters([
                'fleet'
            ])
        },
        methods: {
            ...mapActions([
                'fetchFleet',
            ])
        }
    }
</script>

