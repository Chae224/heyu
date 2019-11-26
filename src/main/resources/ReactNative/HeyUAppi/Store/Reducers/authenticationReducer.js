
const initialState = {  heyUserLocation:{
    heyUserLongitude: "1",
    heyUserLatitude: "1",
    heyUserAccuracy:"44000",
    heyUserSearchRadius:10000,
  },
  heyUserAuthentication:{
    heyUserName: 'blou',
    heyUserPassword: 'blou',
    heyUserPasswordConfirm: 'blou',
  },

  heyUserIsConnected:false
}



function updateAuthentication(state = initialState, action) {
    let nextState
    switch (action.type) {
      case 'UPDATE_AUTH':
           nextState = {
             ...state,
             heyUserAuthentication:action.value
           }
           return nextState || state 
      case 'UPDATE_CONNECT':
        nextState = {
          ...state,
          heyUserIsConnected:action.value
        }
      return nextState || state
    default:
      return state
    }
  }
  
  export default updateAuthentication