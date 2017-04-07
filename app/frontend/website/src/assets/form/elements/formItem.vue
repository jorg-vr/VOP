<template>
    <div class="row">
        <div class="form-group">
            <label v-if="label" class="col-md-2 col-sm-3 col-xs-3 control-label">{{label}}</label>
            <div :class="getColumnClass">
                <slot></slot>
            </div>

        </div>
    </div>
</template>
<script>
    export default {
        props: {
            label: String
        },
        computed: {
            getColumnClass(){
                if(this.label){
                    return "col-md-6 col-sm-7 col-xs-8"
                }
                else {
                    return ""
                }
            }
        }
    }
</script>
