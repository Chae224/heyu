import { combineReducers } from 'redux'

import authenticationReducer from './authenticationReducer'
import LocationReducer from './LocationReducer'

const rootReducer = combineReducers({
	auth:authenticationReducer,
	loc:LocationReducer,
})



export default rootReducer