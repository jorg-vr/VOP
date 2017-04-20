<!--
    This page shows all insurances from a contract
    @param id: The id of the contract to be shown.

-->
<template>
    <div>
         <div class="page-header">
            <h1>{{$t("insurance.types") | capitalize }} </h1>
            <button-add :resource="resource"></button-add>
        </div>
                <list-component v-for="insurance in filteredcontractInsurances"
                                v-if="insurance"
                                :resource="resource"
                                :object="insurance"
                                :visibleKeys="['cost','tax','startDate']"
                                :key="insurance.id">
                </list-component>
        <button-back :route="{name: 'insurances'}"></button-back>
        </div>
    </div>
</template>
<script>
    import resources from '../../constants/resources'
    import listComponent from '../../assets/general/listComponent.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import vehicleSearchBar from '../../assets/search/types/vehicleSearchBar.vue'
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    export default {
        data(){
            return {
                resource: resources.SURETY
            }
        },
        components: {
            buttonBack,buttonAdd,listComponent,buttonLink
        },
        props: {
            id: String
        },
        created(){
             let contractId = this.id
             this.fetchInsurancesByContract(contractId)
             console.log('check')
             console.log(this.filteredcontractInsurances)
        },
        computed: {
            ...mapGetters([
                'insurance',
                'filteredcontractInsurances'
            ])
        },
        methods: {
            ...mapActions([
                'fetchInsurancesByContract'
            ])
        },
    }
</script>
