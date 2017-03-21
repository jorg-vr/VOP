<!--
    Component used for showing an object in a list.
    This component requires the following properties to function correctly:
    - textValues: An array with the values of the object to be shown.
    - objectId: The identifier of the object.
    - show: The name of the link to the show page of this object.
    - edit: The name of the link to the edit page of this object.
    - remove: A function which removes this object.
-->
<template>
    <div class="row">
        <router-link :to="{name: this.show, params: {id: this.objectId}}">
            <div class="panel panel-default col-sm-10">
                <div class="panel-body">
                    <table>
                        <tr>
                            <td v-for="text in textValues">{{text}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </router-link>
        <router-link :to="{name: this.edit, params: {id: this.objectId}}">
            <button type="button" class="btn btn-md btn-info">
                <i aria-hidden="true" class="fa fa-pencil"></i>
            </button>
        </router-link>
        <button type="button" class="btn btn-md btn-danger" v-on:click="remove(objectId)">
            <i aria-hidden="true" class="fa fa-trash"></i>
        </button>
    </div>
</template>

<script>

export default {
    props: {
        textValues: Array, //Array with text to show
        objectId: String, //Unique Id of this object
        show: String, //Name of link to show page of the given object.
        edit: String, //Name of link to edit page of the given object.
        remove: Function, //Function to remove the given object.
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
