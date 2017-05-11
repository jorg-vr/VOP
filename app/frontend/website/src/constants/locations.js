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
export const INVOICE = "invoices/"
export const SURETY = 'insurances/'
export const SURETYDETAIL = 'sureties/'
export const COMMISSION = 'vehicles/types/274776102288499320837812636171303961393/commissions/'

//Example location for updated resources: export const SURETY = 'contracts/{contract}/insurances'
