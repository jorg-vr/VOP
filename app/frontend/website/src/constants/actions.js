/**
 * Created by Jarre on 13-4-2017.
 *
 * A list of actions available in the backend. Every resource has a name which can be used for internationalization,
 * a path which points to the name of the path in frontend and a list of values which can be used for permission checking
 */


export default {
    READ_ALL: {
        name: 'read',
        path: function(resource){
            return resource.plural
        },
        values: ['READ_MINE', 'READ_ALL']
    },
    READ_ONE: {
        name: 'read',
        path: function(resource){
            return resource
        },
        values: ['READ_MINE', 'READ_ALL']
    },
    CREATE: {
        name: 'create',
        path: function(resource){
            return 'new_' + resource
        },
        values: ['CREATE_MINE', 'CREATE_ALL']
    },
    UPDATE: {
        name: 'create',
        path: function(resource){
            return 'edit_' + resource
        },
        values: ['UPDATE_MINE', 'UPDATE_ALL']
    },
    DELETE: { //Delete doesn't have its own path.
        name: 'delete',
        values: ['REMOVE_MINE', 'REMOVE_ALL']
    }
}
