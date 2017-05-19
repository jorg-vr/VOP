<template>
    <div class="row">
        <div class="form-group">
            <label class="col-xs-3 control-label" :for="name">{{text | capitalize}}</label>
            <p class="col-xs-9 select-row">
                <select id="country-select" class="form-control select-item" ref="select"
                        :value="object[name]"
                        @change="onInput($event.target.value)"
                        v-validate="validator"
                        :data-vv-name="name"
                        :data-vv-as="text"
                        :has-error="errors.has(name)">
                    <option value="" disabled hidden>{{$t('actions.select', {subject: text}).capitalize()}}</option>
                    <option  v-for="country in countries[lang]"
                             :selected="country.code === object[name]"
                             :value="country.code">
                        {{country.name}}
                    </option>
                </select>
            </p>
            <span v-show="errors.has(name)" class="help is-danger col-xs-offset-3 col-xs-9">
                {{ errors.has(name) ? errors.first(name) : '' | capitalize  }}
        </span>
        </div>
    </div>
</template>
<script>
    import countries from './countries'
    import Vue from 'vue'


    export default {
        data(){
            return {
                countries
            }
        },
        mounted(){
            let vm = this
            let selector = $("#country-select")
            selector.select2();
            selector.on('change', function (evt) {
                vm.$set(vm.object, vm.name, evt.target.value)
            });
        },
        props: {
            object: Object,
            text: String,
            name: String,
            rules: String
        },
        computed: {
            validator(){
                return this.rules ? this.rules : ''
            },
            lang(){
                return Vue.config.lang
            }
        }
    }
</script>
<style>
    .select2-container .select2-selection--single {
        height: 40px;
        padding: 5px 5px;
    }

    .select2-container .select2-selection--single select [has-error='true'], [has-error='true']:focus {
        border-color: #ff3860;
    }
    .select2-container .select2-selection--single .form-control, input {
        border-width: 1.5px;
    }
    .select2-container--default .select2-selection--single .select2-selection__arrow {
        height: 37px;
    }
</style>