
import Vue from 'vue'
import store from '../../store'

/**
 * Important: this component can only take one child! Other children won't be rendered.
 */

export default Vue.component('protected-element', {
    functional: true,
    render(createElement, context) {
        if(store.getters.hasPermissionWithActions(context.props.permission)){
            return context.children[0]
        }
    },
    props: {
        /**
         * A permission contains 2 keys: a resource and actions (a list).
         * The button will only be shown in the user has the given permission.
         * If no permission is passed, the button will always be shown.
         */
        permission: Object
    }
})


