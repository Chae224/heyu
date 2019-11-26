
const initialState = {  heyUserLocation:{
    heyUserLongitude: "1",
    heyUserLatitude: "1",
    heyUserAccuracy:"44000",
    heyUserSearchRadius:10000,
  },
  heyUserAuthentication:{
    heyUserName: '',
    heyUserPassword: '',
    heyUserPasswordConfirm: '',
  },
}

export default function updateLocation(state = initialState, action) {
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

// export const updateAuth = function updateAuthentication(state = initialState, action) {
//   let nextState
//   switch (action.type) {
//     case 'UPDATE_AUTH':
//          nextState = {
//            ...state,
//            heyUserAuthentication:action.value
//          }
//     case 'UPDATE_CONNECT':
//       nextState = {
//         ...state,
//         heyUserIsConnected:action.value
//       }
//     return nextState || state
//   default:
//     return state
//   }
// }
