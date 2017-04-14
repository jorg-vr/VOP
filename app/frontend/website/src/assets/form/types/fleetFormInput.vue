<template>
    <div>
        <form-input :placeholder="$t('common.name') + ' ' + $t('fleet.fleet') | capitalize" v-model="fleet.name"></form-input>
        <form-select v-if="!fleet.company" optionKey="name" :options="clients" v-model="fleet.company"
                     :hiddenOption="$t('actions.select', {subject: $t('client.client')}).capitalize() ">
        </form-select>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
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
            ])
        }
    }
</script>