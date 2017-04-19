/**
 * All possible paths for API calls to the backend.
 */

export const FLEET = 'fleets/'
export const CLIENT = 'companies/'
export const USER = 'users/'
export const VEHICLE = 'vehicles/'
export const VEHICLE_TYPE = VEHICLE + 'types/'
export const AUTHENTICATION = 'auth/'
export const LOGIN = AUTHENTICATION + 'login/'
export const REFRESH = AUTHENTICATION + 'refresh/'
export const CURRENT_USER =  USER + 'me/'
export const ROLE = AUTHENTICATION + 'roles/'
export const USER_FUNCTION = CURRENT_USER + 'functions/'
export const INSURANCE ='contracts/'
export const PERMISSIONS = 'permissions/'