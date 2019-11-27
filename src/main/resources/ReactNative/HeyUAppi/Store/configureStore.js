import { createStore } from 'redux';
import LocationReducer from './Reducers/LocationReducer'
import rootReducer from './Reducers/index'



export default createStore(rootReducer)



