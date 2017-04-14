<!--
    This page shows a certain insurance in detail. 
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("insurance.insurance") | capitalize }}</h1>
        </div>
        <div class="col-md-8">
            <table class="table show-table">
                <tr>
                    <td>{{$t('insurance.type') | capitalize }}</td>
                    <td>{{insurance.type}}</td>
                </tr>
                <tr>
                    <td>{{$t('insurance.vehicle') | capitalize }}</td>
                    <td>{{insurance.vehicle}}</td>
                </tr>
                <tr>
                    <td>{{$t('insurance.company') | capitalize }}</td>
                    <td>{{insurance.insuranceCompany}}</td>
                </tr>
                <tr>
                    <td>{{$t('insurance.premium') | capitalize }}</td>
                    <td>{{insurance.premium}}</td>
                </tr>
                <tr>
                    <td>{{$t('insurance.franchise') | capitalize }}</td>
                    <td>{{ insurance.franchise}}</td>
                </tr>
                <tr>
                    <td>{{$t('insurance.startdate') | capitalize }}</td>
                    <td>{{insurance.startDate}}</td>
                </tr>
                <tr>
                    <td>{{$t('insurance.enddate') | capitalize }}</td>
                    <td>{{insurance.endDate}}</td>
                </tr>

            </table>
       
            <button-back :route="{name: 'insurances'}"></button-back>

        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'

    export default {
        components: {
            buttonBack
        },
        props: {
            id: String
        },
        created(){
             let insuranceId = this.id
             this.fetchInsurance({id: insuranceId})
        },
        computed: {
            ...mapGetters([
                'insurance'
            ])
        },
        methods: {
            ...mapActions([
                'fetchInsurance'
            ])
        },
    }
</script>
