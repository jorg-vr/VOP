<!--
    This page shows a certain special condition in detail.

    @param id: The id of the special condition to be shown.

-->
<template>
    <div>
       <div class="page-header">
        <h1>{{$t("condition.condition") | capitalize }} </h1>
        </div>
    <div class="col-md-8">
      <table class="table show-table" v-if="condition">
        <tr>
            <td>{{$t('condition.title') | capitalize }}</td>
            <td>{{condition.title}} </td>
        </tr>
        <tr>
            <td>{{$t('condition.referenceCode') | capitalize }}</td>
            <td> {{condition.referenceCode}}  </td>
        </tr>
        <tr>
            <td>{{$t('condition.text') | capitalize }}</td>
            <td> {{condition.text}}  </td>
        </tr>
    </table> 

    <!-- Go back to overview contract page -->
    <button-back> </button-back>

    </div>
</div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import buttonPrevious from '../../assets/buttons/buttonPrevious.vue'
    import listComponent from "../../assets/list/listComponent.vue"
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'

    export default {
        components: {
            buttonPrevious,listComponent,buttonAdd,buttonBack
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