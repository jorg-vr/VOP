<template>
    <div class="panel panel-default">
        <div class="panel-body">
            {{ fleet.company }}
        </div>
    </div>
</template>

<script>

export default {
    props: {
        fleet: Object
    }
}    

</script>
