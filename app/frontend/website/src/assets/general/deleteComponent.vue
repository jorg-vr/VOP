<template>
    <div>
        <button-edit v-if="edit" :resource="resource" :params="params" ></button-edit>
        <button-remove v-if="remove" :resource="resource"  @click="tdshowModal()"></button-remove>
        <!-- Confirmation Modal -->
        <confirm-modal v-show="showModal"
                       @cancelModal="showModal=false"
                       @confirmModal="confirmAction()"
                       @close="showModal=false "
                       :modalHeaderTitle=" $t('modal.titleConfirm') | capitalize"
                       :modalBodyText="$t('modal.textConfirm') | capitalize"
                       :confirmButtonText="$t('modal.button1') | capitalize "
                       :cancelButtonText="$t('modal.button2') | capitalize ">
        </confirm-modal>

    </div>
</template>
<script>
    import {mapActions, mapGetters} from 'vuex'
    import buttonEdit from '../buttons/buttonEdit.vue'
    import buttonRemove from '../buttons/buttonRemove.vue'
    import confirmModal from './modal.vue'

    export default {
        data() {
            return {
                showModal: false
            }
        },
        props: {
            id:String,
            resource: Object,
            params:Object, //for routing
            ids: Object, //for api
            edit: {
                Boolean,
                default: true
            },
            remove: {
                Boolean,
                default: true
            }
        },
        created(){
            this.params=this.params?this.params:{};
            this.params.id=this.id;
        },
        components: {
            buttonRemove,
            buttonEdit,
            confirmModal
        },
        methods: {
            confirmAction: function(){
                // hide modal
                this.showModal=false
                // remove object
                this.$store.dispatch('delete' + this.resource.name.capitalize(), {id: this.id, ids: this.ids})
            },
            tdshowModal: function() {
                this.showModal = true
            }
        }
    }
</script>
