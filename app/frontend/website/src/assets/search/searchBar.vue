<template>
    <div class="row">
        <div class="input-group" id="adv-search">
            <input type="text" class="form-control" placeholder="Search for snippets" v-model="value" @input="onInput"/>
            <div class="input-group-btn">
                <div class="btn-group" role="group">
                    <div class="dropdown dropdown-lg">
                        <button type="button" class="btn btn-default" data-toggle="collapse" aria-expanded="false">
                            <span class="caret"></span>
                        </button>
                        <div class="panel-collapse collapse">
                            <form class="form-horizontal" role="form">
                                <slot></slot>
                            </form>
                        </div>
                    </div>
                    <button type="button" class="btn btn-primary"><span class="fa fa-search" aria-hidden="true"></span></button>
                </div>
            </div>
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
        methods: {
            onInput(){
                this.$emit('input', this.value)
            }
        }
    }
</script>
<style>
    #adv-search {
        margin: auto;
        margin-bottom: 20px;
    }
    .dropdown.dropdown-lg .dropdown-menu {
        margin-top: -1px;
        padding: 6px 20px;
    }
    .input-group-btn .btn-group {
        display: flex !important;
    }
    .btn-group .btn {
        border-radius: 0;
        margin-left: -1px;
    }
    .btn-group .btn:last-child {
        border-top-right-radius: 4px;
        border-bottom-right-radius: 4px;
    }
    .btn-group .form-horizontal .btn[type="submit"] {
        border-top-left-radius: 4px;
        border-bottom-left-radius: 4px;
    }
    .form-horizontal .form-group {
        margin-left: 0;
        margin-right: 0;
    }
    .form-group .form-control:last-child {
        border-top-left-radius: 4px;
        border-bottom-left-radius: 4px;
    }

    @media screen and (min-width: 768px) {
        #adv-search {
            width: 500px;
        }
        .dropdown.dropdown-lg {
            position: static !important;
        }
        .dropdown.dropdown-lg .dropdown-menu {
            min-width: 500px;
        }
    }
</style>