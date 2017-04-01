<template>
    <div class="row search-bar">
        <div class="input-group">
            <input type="text" class="form-control" :placeholder="$t('search_bar.search_for') + ' ' + title" v-model="value" @input="onInput"/>
            <div class="input-group-btn">
                <div class="btn-group" role="group">
                    <!-- This button toggles the search-form -->
                    <button type="button" class="btn btn-default" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
                        <span class="caret"></span>
                    </button>
                </div>
            </div>
        </div>
        <search-form @submit="$emit('submit')" @reset="$emit('reset')"><slot></slot></search-form>
    </div>
</template>

<script>
    import searchForm from './searchForm.vue'

    export default {
        data() {
            return {
                value: ''
            }
        },
        props: {
            title: String
        },

        components: {
            searchForm
        },
        methods: {
            onInput(){
                this.$emit('input', this.value)
            }
        }
    }
</script>
<style>
    .search-bar {
        margin: auto;
        margin-bottom: 20px;
    }

    .input-group-btn .btn-group {
        display: flex !important;
    }
</style>