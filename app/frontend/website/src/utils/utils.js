
import Vue from 'vue'

/**
 * Returns the title for a page with the given resourceName and actionName
 * @param resourceName
 * @param actionsName
 */
export const getResourceActionText = function (resourceName, actionsName) {
    return Vue.t('actions.' + actionsName, {subject: Vue.t(resourceName + '.' + resourceName)}).capitalize()
}
