<template>
    <div>
        <table class="table-hover table">
            <thead>
                <tr>
                    <th v-for="head in listObject.headers">
                        {{$t(resource.name + '.' + head).capitalize()}}
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="value in listObject.values" class="list-tr">
                    <td v-for="header in listObject.headers" class="clickable-td" @click="tdclick(value.id)">
                        {{value[header]}}
                    </td>
                    <td class="stretch">
                        <button-edit :resource="resource" :params="paramsWithId(value.id)" ></button-edit>
                        <button-remove :resource="resource"  @click="tdshowModal(value.id)"></button-remove>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- Confirmation Modam -->
        <confirm-modal v-show="showModal" 
            @cancelModal="showModal=false" 
            @confirmModal="confirmAction()" 
            :modalHeaderTitle=" $t('modal.titleConfirm') | capitalize"
            :modalBodyText="$t('modal.textConfirm') | capitalize" 
            :confirmButtonText="$t('modal.button1') | capitalize "
            :cancelButtonText="$t('modal.button2') | capitalize ">        
        </confirm-modal> 

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
        computed: {
            par(){
                if(this.params==undefined){
                    return {};
                }else{
                    return this.params
                }
            }
        },

        props: {
            resource: Object,
            listObject: Object,
            params:Object,
            ids: Object //Object with id's for creating the correct POST/PUT route.
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
                this.$router.push({name: this.resource.name, params: this.paramsWithId(id)});
            },
            confirmAction: function(){
                // hide modal
                this.showModal=false
                // remove object
                // special case deletion of insurance
                this.$store.dispatch('delete' + this.resource.name.capitalize(), {id: this.selectedvalue, ids: this.ids})
            },
            tdshowModal: function(id) {
                this.showModal = true
                this.selectedvalue=id
            },
            paramsWithId(id){
                this.par.id=id;
                return this.par;
            }
        }
    }
    
</script>
