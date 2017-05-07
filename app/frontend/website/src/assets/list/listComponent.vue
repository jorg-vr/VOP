<template>
    <div>
        <table class="table-hover table">
            <thead>
                <tr>
                    <th v-for="head in listObject.headers">{{$t(head).capitalize()}}</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="value in listObject.values" class="list-tr">
                    <td v-for="header in listObject.headers" class="clickable-td" @click="tdclick(value.id)">
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

.clickable-td {
    cursor:pointer;
}
tr.list-tr {
    border-bottom: 1px solid #e6e6e6;  
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
        methods: {
            tdclick: function(id) {
                this.$router.push({name: this.resource.name, params: {id: id}});
            }
        }
    }
    
</script>
