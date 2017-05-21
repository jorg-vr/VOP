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
                    <td v-for="header in listObject.headers" :class="(clickable ? '' : 'non-') + 'clickable-td'" @click="tdclick(value.id)">
                        {{value[header]}}
                    </td>
                    <td class="stretch">
                        <button-edit v-if="edit" :resource="resource" :params="getRouteParams(value.id)"></button-edit>
                        <button-remove v-if="remove" :resource="resource"  @click="tdshowModal(value.id)"></button-remove>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- Confirmation Modal -->
        <confirm-modal v-show="showModal" 
            @cancelModal="showModal=false" 
            @confirmModal="confirmAction()" 
            @close="showModal=false "
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
    import confirmModal from './modal.vue'

    export default {
        data() {
            return {
                showModal: false,
                clickable: false
            }
        },
        props: {
            resource: Object,
            listObject: Object,
            ids: Object, //Object with id's for creating the correct POST/PUT route.
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

            //These booleans tell if the listComponent should give access to the show, edit and delete of the resource.
            show: {
                type: Boolean,
                default: true
            },
            edit: {
                Boolean,
                default: true
            },
            remove: {
                Boolean,
                default: true
            }
        },
        created(){
            this.clickable = this.show && this.hasPermissionForRoute(this.resource.name)
        },
        components: {
            buttonRemove,
            buttonEdit,
            confirmModal
        },
        computed: {
            ...mapGetters([
                'hasPermissionForRoute'
            ])
        },
        methods: {
            tdclick: function(id) {
                if(this.clickable){
                    this.$router.push({name: this.resource.name, params: this.getRouteParams(id)});
                }
            },
            confirmAction: function(){
                // hide modal
                this.showModal=false
                // remove object
                this.$store.dispatch('delete' + this.resource.name.capitalize(), {id: this.selectedvalue, ids: this.ids})
            },
            tdshowModal: function(id) {
                this.showModal = true
                this.selectedvalue=id
            },

            /**
             * Get the params for the show and edit route of the object with the given ID
             * @param id the ID of the object
             * @returns {{id}} An object with route parameters
             */
            getRouteParams(id){
                let params = {id}
                for (let key in this.ids) {
                    if (this.ids.hasOwnProperty(key)) {
                        params[key] = this.ids[key]
                    }
                }
                return params
            }
        }
    }
</script>
