<template>
    <div>
        <button  type="button" class="btn btn-default btn-block" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
            {{$t('commission.commissions')}}
            <span class="caret"></span>
        </button>
        <show class="panel-collapse collapse" :id="id" :loc="loc"></show>
        <button  type="button" class="btn btn-default btn-block" data-toggle="collapse" data-target=".panel-collapse2" aria-expanded="false">
            {{$t('commission.commissions')}}
            <span class="caret"></span>
        </button>
        <edit class="panel-collapse2 collapse" :id="id" :loc="loc"></edit>
    </div>
</template>
<script>
    import show from './show.vue'
    import edit from './edit.vue'
    export default {
        props:{
            id:String,
            loc:String
        },
        components: {
            show,edit
        }
    }
</script>
