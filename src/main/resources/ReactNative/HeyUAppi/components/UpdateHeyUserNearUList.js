
import React from 'react';
import {Dimensions, Slider, View, Text,  StyleSheet, Button} from 'react-native';
import { connect } from 'react-redux'


class UpdateHeyUserNearUList extends React.Component {


  state = {

    heyUserNearU: [],

  }


  dispatchHeyUserNearU(heyUserNearU){
    console.log("Le dispatch se fait");
    const action = { type: "UPDATE_HEYUSERNEARU", value: heyUserNearU }
    console.log(action.value);
    this.props.dispatch(action)

  }




  updateHeyUserNearUList = () => {
    console.log("updateHeyUserNearUList s'execute")
    console.log("radius envoyé:  "+this.props.heyUserSearchRadius);
    if(this.props.heyUserIsConnected == true){
        fetch('http://192.168.1.64:8080/updateLocation', {
        method: 'POST',
        headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({
        heyUserAuthentication: this.props.heyUserAuthentication,
        heyUserLocation: {
          heyUserLongitude: this.props.heyUserLocation.heyUserLongitude,
          heyUserLatitude: this.props.heyUserLocation.heyUserLatitude,
          heyUserAccuracy:this.props.heyUserLocation.heyUserAccuracy,
          heyUserSearchRadius:this.props.heyUserSearchRadius,
          },
     
        }),
         }).then((response) => response.json())
        .then((responseJson) => {
             this.dispatchHeyUserNearU(responseJson.heyUserNearU);
             console.log(this.state.heyUserNearU);
            return responseJson.heyUserNearU;
        })
        .catch((error) => {
            console.error(error);
        });

    }


  }




  componentDidUpdate =() =>{
    this.updateHeyUserNearUList();
  
    }

  componentDidMount =() =>{
    const goToLogging = () => this.props.navigation.navigate('Logging');
    goToLogging();
  
    }

  render() {
      
      return (null
      );
  }
}




const mapDispatchToProps = (dispatch) => {
    return {
      dispatch: (action) => { dispatch(action) }
    }
}
  

const mapStateToProps = (state) => {


  return {
    heyUserAuthentication:state.auth.heyUserAuthentication,
    heyUserIsConnected:state.auth.heyUserIsConnected,
    heyUserLocation:state.loc.heyUserLocation,
    heyUserSearchRadius:state.loc.heyUserSearchRadius
  }


}




export default connect(mapStateToProps, mapDispatchToProps)(UpdateHeyUserNearUList)
