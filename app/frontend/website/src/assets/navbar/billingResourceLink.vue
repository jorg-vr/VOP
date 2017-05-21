<template>
    <protected-link v-if="activeFunction" :to="to" :permission="permission">
        {{$t('invoice.invoices')|capitalize}}
    </protected-link>
</template>
<script>
    import ProtectedLink from '../protection/ProtectedLink.vue'
    import actions from '../../constants/actions'
    import {PagePermissions} from '../../constants/PagePermissions'
    import resources from '../../constants/resources'
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        data(){
            return {
                permission:PagePermissions[actions.READ_ALL.path(resources.BILLING.name)],
            }
        },
        components: {
            ProtectedLink
        },
        computed: {
            ...mapGetters([
                'activeFunction'
            ]),
            to(){
                return {name:resources.INVOICE.name.plural(),params:{companyId:this.activeFunction.company}}
            }
        },
    }
</script>