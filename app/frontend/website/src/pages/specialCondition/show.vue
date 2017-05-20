<!--
    This page shows a certain special condition in detail.

    @param id: The id of the special condition to be shown.

-->
<template>
    <div>
        <div class="page-header">
            <h1>{{condition.title | capitalize }} ({{condition.referenceCode}})
                <delete-component  :resource="resource" :id="condition.id" :back="back"></delete-component>  </h1>
        </div>
        <div class="col-md-8">
            <p>{{condition.text}}</p>

            <!-- Go back to overview contract page -->
            <button-back :route="back"> </button-back>

        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import deleteComponent from '../../assets/general/deleteComponent.vue'

    export default {
        data(){
            return {
                resource: resources.CONDITION,
                back:{name:resources.CONTRACT.name.plural()}
            }
        },
        components: {
            listComponent,buttonAdd,buttonBack,deleteComponent
        },
        props: {
            id: String,
        },
        created(){
            this.fetchCondition({id: this.id})
        },
        computed: {
            ...mapGetters([
                'condition'
            ])
        },
        methods: {
            ...mapActions([
                'fetchCondition',
            ]),
            ...mapMutations([
                'setSpecialConditions'
            ]),
        },
    }
</script>