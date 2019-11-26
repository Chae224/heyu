
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

function updateLocation(state = initialState, action) {
  let nextState
  switch (action.type) {
    case 'UPDATE_LOCATION':
        console.log("Case Update_location");
        console.log("action reçue = "+action.value.heyUserLongitude)
         nextState = {
           ...state,
           heyUserLocation:action.value
         }
    
       // nextState.heyUserLocation=action.value;

    console.log("returned state : "+nextState.heyUserLocation.heyUserLongitude)
    return nextState || state
  default:
    return state
  }
}





export default updateLocation