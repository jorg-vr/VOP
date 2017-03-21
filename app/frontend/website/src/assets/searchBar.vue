<!--
    Component to search objects by their keys.

    This does not function properly yet!
-->
<template>
    <div class="row">
        <div class="col-md-4">
            <div id="search_input">
                <div class="input-group">
                    <input type="text" class="form-control input-mg" @keyup.enter="search" v-bind:placeholder=getTitle v-model="searchQuery"/>
                    <span class="input-group-btn">
                            <button v-on:click="search" class="btn btn-primary btn-mg" type="button">
                                <i class="fa fa-search" aria-hidden="true"></i>
                            </button>
                        </span>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <select class="form-control" id="sel1" v-model="selected">
                    <option value="startValue" hidden>zoeken op</option>
                    <option v-for="option in options" v-bind:value="option.value">
                        {{option.text}}
                    </option>
                </select>
            </div>
        </div>

        <div class="col-md-1 ">
            <span class="input-group-btn">
                <button type="button" class="btn btn-primary btn-sm" id="addButton" v-on:click="add">
                    <i class="fa fa-plus" aria-hidden="true"></i>
                </button>
            </span>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                searchQuery: '',
                selected: 'startValue'
            }
        },
        props: {
            placeholder: {
                type: String,
                default: ''
            },
            options: {
                type: Array,
                default: function() { return []}
            },
            getSearchQuery: Function,
            getSelectedOption: Function
        },
        methods: {
            search () {

            }

        },
        computed: {
            getTitle () {
                var value = this.placeholder + ' zoeken'
                return value
            },
            getSearchQuery () {
                return this.searchQuery.trim().toLowerCase();
            },
            getSelectedOption () {
                return this.selected
            }
        }
    }

</script>