<!--
    Component used for showing an object in a list.
    This component requires the following properties to function correctly:
    - object: Object with values to show
    - visibleKeys: Keys of values which have to be shown
    - show: The name of the link to the show page of this object.
    - edit: The name of the link to the edit page of this object.
    - remove: A function which removes this object.
-->
<template>
    <div class="row" v-if="object">
        <router-link :to="{name: this.show, params: {id: object.id}}">
            <div class="panel panel-default col-sm-10">
                <div class="panel-body">
                    <table>
                        <tr>
                            <td v-for="value in values"> {{value}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </router-link>
        <button-edit v-if="edit" :route="{name: edit, params: {id: object.id}}"></button-edit>
        <button-remove v-if="remove" @click="remove({id: object.id})"></button-remove>
    </div>
</template>
<script>
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import buttonRemove from '../../assets/buttons/buttonRemove.vue'

    export default {
        props: {
            object: Object, //Object with values to show
            visibleKeys: Array, //Keys of values which have to be shown
            show: String, //Name of link to show page of the given object.
            edit: String, //Name of link to edit page of the given object.
            remove: Function, //Function to remove the given object.
        },
        components: {
            buttonEdit, buttonRemove
        },
        computed: {
            values() {
                let values = []
                for(let i=0; i<this.visibleKeys.length; i++){
                    let key = this.visibleKeys[i]
                    if(this.object[key]){
                        values.push(this.object[key])
                    }
                }
                return values
            }
        }
    }

</script>
<style>

    .btn-md {
        margin-left: 5px;
        margin-top: 8px;
    }

    .panel {
        margin-right: 5px;
    }

    .panel table  {
        /* Create columns with equal width */
        width: 100%;
        table-layout: fixed;
    }
    a td {
        color: black;
        /* Trigger columns with equal width */
    }
    a div.panel:hover {
        background-color: #eee;
    }

</style>
