
import Vue from 'vue'
import store from '../../store'

/**
 * A functional component. This component does not contain any HTML or CSS, only the script part.
 * The goal of this component is to show the component only if the user has the correct permissions.
 *
 * Important: this component can only accept one child! Other children won't be rendered.
 *
 * @param permission: An object containing a resource and actions field which the user needs to view the component
 */

export default Vue.component('protected-element', {
    functional: true,
    render(createElement, context) {
        if(store.getters.hasPermission(context.props.permission)){
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


