<template>
    <div>
        <div class="page-header">
            <h1>Vloot {{this.$route.params.id}}</h1>
        </div>
        <div>

        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                subfleets : [ //Some test subfleets, this will be filled in with the actual subfleets
                    {type: 'vrachtwagen', vehicles : [
                        {license_number: 'VA'},
                        {license_number: 'VB'},
                        {license_number: 'VC'}
                    ]},
                    {type: 'persoonwagen', vehicles : [
                        {license_number: 'PA'},
                        {license_number: 'PB'},
                        {license_number: 'PC'}
                    ]}
                ]
            }
        },
        created() {
            //this.fetchFleetList()
        },
        methods: {
            fetchSubFleetList (){
                this.$http.get('TODO').then(response => {
                    this.subfleets = response.body
                })
            }
        }
    }
</script>