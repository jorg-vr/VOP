<!--
A collapsible form part of a searchbar. This form can be used for advanced searching objects.
-->
<template>
    <div class="row search-bar">
        <button id="search-vehicle" type="button" class="btn btn-default btn-block" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
            {{$t('search_bar.search')}}
            <span class="caret"></span>
        </button>
        <div class="panel-collapse collapse">
            <form class="form-horizontal collapse-form" @submit.prevent="onSubmit" @keyup.enter="onSubmit" role="form">
                <slot></slot>
                <button type="button" class="btn btn-primary" @click="reset">Reset</button>
                <button type="button" class="btn btn-primary search-button text-right" @click="onSubmit">
                    <span class="fa fa-search" aria-hidden="true"></span>
                </button>
            </form>
        </div>
    </div>

</template>
<script>
    export default {
        methods: {
            onSubmit(){
                this.$emit('search')
                $('.collapse').collapse('hide')
            },
            reset(){
                this.$emit('reset')
                $('.collapse').collapse('hide')
            }
        }
    }
</script>
<style>
    .collapse-form {
        border: 1px solid #ccc;
        border-radius: 4px;
        width: 100%;
        text-align: right;
    }

    .collapse-form button {
        margin-right: 15px;
        margin-bottom: 10px;
    }

    .collapse-form {
        margin: 0px auto;
    }

    .collapse-form .form-group {
        margin: 10px 30px;
    }
</style>