<template>
    <div>
        <button id="search-vehicle" type="button" class="btn btn-default btn-block" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
            {{$t("function.functions") | capitalize}}
            <span class="caret"></span>
        </button>
        <div class="function-panel panel-collapse collapse">
            <list-component :resource="resource" :listObject="listObject" :show="false" :edit="false"></list-component>
            <button-add :resource="resource" :params="params"></button-add>
        </div>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        data(){
            return {
                resource: resources.FUNCTION,
                params: {userId: this.userId}
            }
        },
        props: {
            userId: String
        },
        components: {
            listComponent, buttonAdd
        },
        created(){
            this.fetchFunctions({ids: this.params})
        },
        computed: {
            ...mapGetters([
                'functions'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['roleName', 'companyName'];
                listObj.values = this.functions
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchFunctions'
            ])
        }
    }
</script>
<style>
    .function-panel .clickable-td {
        cursor: inherit;
    }
</style>