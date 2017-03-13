<template>
    <div class="row">
        <router-link :to="{name: this.show, params: {id: this.object.id}}">
            <div class="panel panel-default col-sm-10">
                <div class="panel-body">
                    <table>
                        <tr>
                            <td v-for="value in getValues()">{{value}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </router-link>
        <router-link :to="{name: this.edit, params: {id: this.object.id}}">
            <button type="button" class="btn btn-md btn-info">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
            </button>
        </router-link>

        <button type="button" class="btn btn-md btn-danger" v-on:click="this.remove">
            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
        </button>
    </div>
</template>

<script>

export default {
    props: {
        keys: Array, //Array of properties which have to be shown of the given object.
        show: String, //Name of link to show page of the given object.
        edit: String, //Name of link to edit page of the given object.
        remove: Function, //Function to remove the given object.
        object: Object //the element to be shown in the list.
    },
    methods: {
        getValues(){
            let values = [];
            for(let i=0; i<this.keys.length; i++){
                if(this.object.hasOwnProperty(this.keys[0])){
                    values.push(this.object[this.keys[i]]);
                }
            }
            return values;
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
    table-layout: fixed;
}
a td {
    color: black;
    /* Trigger columns with equal width */
    width: 2%;
}
a div.panel:hover {
    background-color: #eee;
}

</style>
