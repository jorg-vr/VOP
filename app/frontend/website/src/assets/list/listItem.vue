<!--
Component used for showing an object in a list.

@param object: Object with values to show
@param visibleKeys: Keys of values which have to be shown
@param resource: The resource of the object which is show
@param rowClass (optional): the HTML class for the row of this component
-->
<template>
    <div v-if="object">
        <router-link :to="{name: resource.name, params: {id: object.id}}">
            <div class="col-md-10 col-sm-9 col-xs-9 list-group-item link-group-item">
                <table>
                    <tr>
                        <td v-for="value in values"> {{value}}</td>
                    </tr>
                </table>
            </div>
        </router-link>
        <div class="col-md-2 col-sm-33 col-xs-3">
            <button-edit :resource="resource" :params="{id: object.id}"></button-edit>
            <button-remove :resource="resource" @click="showModal=true"></button-remove>
            <!-- Modal used for submitting the remove action -->
            <confirm-modal v-show="showModal" @cancelModal="showModal=false" @confirmModal="confirmAction()"></confirm-modal>
        </div>
    </div>
</template>
<style>
table  {
    /* Create columns with equal width */
    width: 100%;
    table-layout: fixed;
}

.btn-info .btn-danger {
    margin-top: 17px;
}
.link-group-item {
    border-width: 0 0 1px 0;
    padding: 20px 0 20px 20px;
}

.link-group-item:hover {
    background-color: #e7eaec;
}

</style>
<script>
    import {mapActions,mapGetters} from 'vuex'
    import buttonEdit from '../buttons/buttonEdit.vue'
    import buttonRemove from '../buttons/buttonRemove.vue'
    import confirmModal from '../general/modal.vue'

    export default {
        data(){
            return{
                showModal:false
            }
        },
        props: {
            object: Object, //Object with values to show
            visibleKeys: Array, //Keys of values which have to be shown
            rowClass: String, //Class for this object.
            resource: Object  //Name of the resource this component shows.

        },
        components: {
            buttonEdit, buttonRemove,confirmModal
        },
        computed: {
            //Compute the values of the object to be shown
            values() {
                let values = []
                for(let i=0; i<this.visibleKeys.length; i++){
                    let key = this.visibleKeys[i]
                    if(this.object[key]){
                        values.push(this.object[key])
                    }
                }
                return values
            },
            ...mapGetters([
                'contractId'
            ])
        },
        methods:{
            confirmAction: function(){
                // hide modal
                this.showModal=false
                // remove object
                // special case deletion of insurance
                if(this.resource.name == 'surety'){
                    this.$store.dispatch('delete' + this.resource.name.capitalize(), {id: this.object.id, contractId:this.contractId})
                }
                else{
                    this.$store.dispatch('delete' + this.resource.name.capitalize(), {id: this.object.id})
                }
            }
        }
    }

</script>
