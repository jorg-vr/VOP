<template>
    <div class="row search-bar">
        <div class="input-group">
            <input type="text" class="form-control" :placeholder="$t('search_bar.search_for') + ' ' + title" v-model="value" @input="onInput"/>
            <div class="input-group-btn">
                <div class="btn-group" role="group">
                    <div class="dropdown dropdown-lg">
                        <button type="button" class="btn btn-default" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
                            <span class="caret"></span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-collapse collapse">
            <form class="form-horizontal collapse-form" @submit.prevent="onSubmit" role="form">
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
    import formInput from '../form/elements/formInput.vue'
    export default {
        data() {
            return {
                value: ''
            }
        },
        components: {
            formInput
        },
        props: {
            title: String
        },
        methods: {
            onInput(){
                this.$emit('input', this.value)
            },
            onSubmit(){
                this.$emit('submit')
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
    .search-bar {
        margin: auto;
        margin-bottom: 20px;
    }

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
    .input-group-btn .btn-group {
        display: flex !important;
    }
</style>