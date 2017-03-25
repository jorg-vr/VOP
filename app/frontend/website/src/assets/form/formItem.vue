<template>
    <div class="row">
        <div class="form-group">
            <label v-if="label" class="col-sm-4 control-label">{{label}}</label>
            <div class="col-sm-8">
                <slot></slot>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        props: {
            label: String
        }
    }
</script>
