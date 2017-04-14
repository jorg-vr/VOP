
import Vue from 'vue'

export const getResourceActionText = function (resourceName, actionsName) {
    return Vue.t('actions.' + actionsName, {subject: Vue.t(resourceName + '.' + resourceName)}).capitalize()
}
