<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("condition.conditions") | capitalize}}
                <button-add :resource="resource"></button-add>
            </h1>
        </div>

        <!-- Render an info-pane for every client. Once all the data is loaded, the table will be shown.-->
        <list-component :resource="resource" :listObject="listObject" ></list-component>
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
                filters: {address: {}},
                resource: resources.CONDITION
            }
        },
        components: {
            listComponent, buttonAdd
        },
        created() {
            this.setLoading({loading: true})
            this.fetchConditions().then(() => {
                this.setLoading({loading: false })
            })
        },
        computed: {
            ...mapGetters([
                'conditions'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ["title","referenceCode"];
                listObj.values = this.conditions;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchConditions',
            ]),
            ...mapMutations([
                'setLoading'
            ])
        }
    }
</script>