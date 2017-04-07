<!--
    Based on https://gist.github.com/martinlindhe/f34489126eb635cb241f
-->
<template>
    <!-- Wrap select into <p> to be able to add language flag -->
     <p> 
        <span class="lang-sm" v-bind:lang="currentLangCode"></span>
        <select id="language-picker" class="form-control" :value="currentLangName" @change="updateLanguage($event.target.value)" >
            <option v-for="lang in languages" :selected="lang.code===currentLangCode">{{lang.name}}</option>
        </select> 
    </p>
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
        computed: {
            currentLangCode(){
                return Vue.config.lang
            },
            currentLangName(){
                let lang = this.languages.filter(lang => lang.code === this.currentLangCode)[0]
                return lang.name
            }
        },
        methods: {
            updateLanguage(name){
                let lang = this.languages.filter(lang => lang.name === name)[0]
                Vue.config.lang = lang.code
            }
        }
    }
</script>
<style>
     #language-picker {
        background: rgba(0, 0, 0, 0);
        border-color: rgba(0, 0, 0, 0);
        box-shadow: none;
        margin-top: 7.5px;
        color:white;
        padding-right:0;
    }
/* OLD STYLE
    #language-picker {
        margin-left: 5px;
        width:103px;
        height: 28px;
        float: right;
        font-size: 14px;
        background: rgba(0, 0, 0, 0);
        border-color: rgba(0, 0, 0, 0);
        padding: 0;
        box-shadow: none;
        color: #ecf0f1;
    }
*/
    .lang-sm{
        margin-top: 5px;
    }
 option{
        color:black;
    }
</style>