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
export const INSURANCE ='contracts/{contract}/insurances/'
export const PERMISSIONS = 'permissions/'
export const INVOICE = CLIENT+"{company}/invoices/"
export const VEHICLE_INVOICE = INVOICE+"{invoice}/vehicle-invoices/"
export const SURETY = CLIENT+'{company}/sureties/'
export const COMMISSION = '{resource}/{resourceId}/commissions'
export const CONTRACT = 'contracts/'
export const CONDITION= 'special-conditions/'
export const LOG = '{resource}/{resourceId}/logs/'
export const FUNCTION = 'users/{userId}/functions/'

//Example location for updated resources: export const SURETY = 'contracts/{contract}/insurances'
