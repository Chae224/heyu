
const initialState = {  heyUserLocation:{
  heyUserLongitude: "1",
  heyUserLatitude: "1",
  heyUserAccuracy:"44000",
},
heyUserAuthentication:{
  heyUserName: 'blou',
  heyUserPassword: 'blou',
  heyUserPasswordConfirm: 'blou',
},

heyUserIsConnected:false,
heyUserNearU:[],
heyUserSearchRadius:10
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
    
    console.log("returned state : "+nextState.heyUserLocation.heyUserLongitude)
    return nextState || state



      case 'UPDATE_HEYUSERNEARU':
        console.log("UPDATE_HEYUSERNEARU");
         nextState = {
           ...state,
           heyUserNearU:action.value
         }
    
    return nextState || state


    case 'UPDATE_RADIUS':
      console.log("UPDATE_RADIUS");
       nextState = {
         ...state,
         heyUserSearchRadius:action.value
       }
  
  return nextState || state


  default:
    return state
  }
}





export default updateLocation