<template>
    <div>
        <table class="table-hover table">
            <thead>
                <tr>
                    <th v-for="head in listObject.headers">{{$t(head).capitalize()}}</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="value in listObject.values">
                    <td v-for="header in listObject.headers">
                        {{value[header]}}
                    </td>
                    <td class="stretch">
                        <button-edit :resource="resource" :params="{id: value.id}"></button-edit>
                        <button-remove :resource="resource"></button-remove>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<style>
.stretch {
    width: 1%;
    white-space: nowrap;
}
table {
    
}
</style>
<script>
    import {mapActions, mapGetters} from 'vuex'
    import buttonEdit from '../buttons/buttonEdit.vue'
    import buttonRemove from '../buttons/buttonRemove.vue'
    import confirmModal from '../general/modal.vue'

    export default {
        data() {
            return {
                showModal: false
            }
        },
        props: {
            resource: Object,
            listObject: Object,
            /*
            { "headers" : ["Name", "CompanyName"],
              "values" : [
              {  "Name" : "Vlootx",
                 "CompanyName" : "Bedrijfx"
                 "id" : "idx"
             },
             {   "Name" : "Vlooty",
                 "CompanyName" : "Bedrijfy",
                 "id" : "idy"
             }]
            }
            */
        },
        components: {
            buttonRemove,
            buttonEdit,
            confirmModal
        },
        created() {
            
        }
    }
</script>
