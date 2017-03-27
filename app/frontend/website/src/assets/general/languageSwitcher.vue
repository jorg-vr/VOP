<!--
    Based on https://gist.github.com/martinlindhe/f34489126eb635cb241f
-->
<template>
    <select class="selectpicker" data-width="fit">
        <option v-for="lang in languages" :class="'flag-icon flag-icon-' + lang.code">{{lang.name}}</option>
    </select>
</template>

<script>
    import Vue from 'vue';
    export default {
        data() {
            return {
                lang: Vue.config.lang,
                languages: [
                    {
                        code: 'nl',
                        name: 'Nederlands'
                    },
                    {
                        code: 'en',
                        name: 'English'
                    }
                ]
            }
        },
        components: {
            language: {
                props: ['code', 'name'],
                template: '<span @click="selectLanguage">{{ name }}</span>',
                methods: {
                    electLanguage(e) {
                        Vue.config.lang = this.code;
                    }
                }
            }
        }
    }
</script>
