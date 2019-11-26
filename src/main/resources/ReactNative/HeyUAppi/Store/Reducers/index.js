import { combineReducers } from 'redux'

import authenticationReducer from './authenticationReducer'
import LocationReducer from './LocationReducer'

const rootReducer = combineReducers({
	authenticationReducer,
	LocationReducer,
})

export default rootReducer