<template>
    <div>
        <list-header :visibleKeys="visibleKeys" :resource="resource"></list-header>
        <list-item v-for="object in objects"
                        v-if="object"
                        :resource="resource"
                        :object="object"
                        :visibleKeys="visibleKeys"
                        :key="object.id">
        </list-item>
    </div>
</template>
<script>
    import listHeader from './listHeader.vue'
    import listItem from './listItem.vue'

    export default {
        props: {
            objects: Array, //Object with values to show
            visibleKeys: Array, //Keys of values which have to be shown
            rowClass: String, //Class for the listItems.
            resource: Object  //Name of the resource this list shows.
        },
        components: {
            listHeader,
            listItem
        }
    }
</script>