<template>
    <div>
        <form-input :placeholder="$t('common.name') + ' ' + $t('fleet.fleet') | capitalize" v-model="fleet.name"></form-input>
        <form-select v-if="!fleet.company && clients.length > 0" optionKey="name" :options="clients" v-model="fleet.company"
                     :hiddenOption="$t('actions.select', {subject: $t('client.client')}).capitalize() ">
        </form-select>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import actions from '../../../constants/actions'
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'

    export default {
        props: {
            fleet: Object
        },
        components: {
            formInput, formSelect
        },
        created(){
            if(this.isAuthorizedForAllResources(resources.FLEET, actions.READ_ALL)){
                this.fetchClients()
            }
        },
        computed: {
            ...mapGetters([
                'clients',
                'isAuthorizedForAllResources'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClients'
            ])
        }
    }
</script>