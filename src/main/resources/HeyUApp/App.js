//This is an example code to get Geolocation//  
import React from 'react';
//import react in our code. 
import {View, Text,  StyleSheet, Image ,PermissionsAndroid,Platform} from 'react-native';
//import all the components we are going to use.
import Geolocation from '@react-native-community/geolocation';
 
 
export default class App extends React.Component {
  state = {
    heyUserName: 'nobody',
    heyUserPassword: '0000',
    heyUserSearchRadius: 5857873,
    currentLongitude: "0",//Initial Longitude
    currentLatitude: "0",//Initial Latitude
    currentAccuracy:"0",
    positionCountChange: 0,
    heyUserNearU: []
 }


 componentDidMount = () => {
    var that =this;
    //Checking for the permission just after component loaded
    if(Platform.OS === 'ios'){
      this.callLocation(that);
    }else{
      async function requestLocationPermission() {
        try {
          const granted = await PermissionsAndroid.request(
            PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,{
              'title': 'Location Access Required',
              'message': 'This App needs to Access your location'
            }
          )
          if (granted === PermissionsAndroid.RESULTS.GRANTED) {
            //To Check, If Permission is granted
            that.callLocation(that);
            
          } else {
            alert("Permission Denied");
          
          }
        } catch (err) {
          alert("err",err);
          console.warn(err)
        }
      }
      requestLocationPermission();
    }    
  }

  updateHeyUserNearUList = (that) => {
    console.log("updateHeyUserNearUList s'execute")

    fetch('http://192.168.1.64:8080/updateLocation', {
     method: 'POST',
     headers: {
       Accept: 'application/json',
       'Content-Type': 'application/json',
     },
     body: JSON.stringify({
       heyUserName: that.state.heyUserName,
       heyUserPassword: that.state.heyUserPassword,
       heyUserSearchRadius: that.state.heyUserSearchRadius,
       heyUserLongitude: that.state.currentLongitude,
       heyUserLatitude: that.state.currentLatitude,
     }),
   }).then((response) => response.json())
       .then((responseJson) => {
         that.setState({ heyUserNearU:responseJson.heyUserNearU });
         console.log(that.state.heyUserNearU);
         console.log("longueur du tableau = "+ that.state.heyUserNearU.length);
         return responseJson.heyUserNearU;
       })
       .catch((error) => {
         console.error(error);
       });
  }
  


 callLocation(that){
  //alert("callLocation Called");
    Geolocation.getCurrentPosition(
      //Will give you the current location
       (position) => {
          const currentLongitude =JSON.stringify(position.coords.longitude);
          //getting the Longitude from the location json
          const currentLatitude = JSON.stringify(position.coords.latitude);
          //getting the Latitude from the location json
          const currentAccuracy = JSON.stringify(position.coords.accuracy);
          //getting the Latitude from the location json
          that.setState({ currentAccuracy:currentAccuracy });
          //Setting state Longitude to re re-render the Longitude Text
          that.setState({ currentLatitude:currentLatitude });
          //Setting state Latitude to re re-render the Longitude Text
          that.setState({ currentLongitude:currentLongitude });
          //Setting state Latitude to re re-render the Longitude Text
       },
       (error) => alert(error.message),
       { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000,distanceFilter: 3 }

    );
    that.watchID = Geolocation.watchPosition((position) => {
      //Will give you the location on location change
        console.log(position);
        const currentLongitude = JSON.stringify(position.coords.longitude);
          //getting the Longitude from the location json
          const currentLatitude =JSON.stringify(position.coords.latitude);
          //getting the Latitude from the location json
          const currentAccuracy = JSON.stringify(position.coords.accuracy);
        //getting the Latitude from the location json
       that.setState({ currentLongitude:currentLongitude });
       //Setting state Longitude to re re-render the Longitude Text
       that.setState({ currentLatitude:currentLatitude });
       //if position changes
       that.setState({ positionCountChange:this.state.positionCountChange+1});
       //accuracy
       that.setState({ currentAccuracy:currentAccuracy });

      // Fetch Here
      that.updateHeyUserNearUList(that);
      

    },
    (error) => alert(error.message),
      { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000, distanceFilter: 3 }
    );
  }




 componentWillUnmount = () => {
    Geolocation.clearWatch(this.watchID);
 }





 
 render() {
    return (
       <View style = {styles.container}>
          <Image
            source={{uri:'https://png.icons8.com/dusk/100/000000/compass.png'}}
            style={{width: 100, height: 100}}
          />
          <Text style = {styles.boldText}>
             You are Here
          </Text>
          <Text style={{justifyContent:'center',alignItems: 'center',marginTop:16}}>
            Longitude: {this.state.currentLongitude}
          </Text>
          <Text style={{justifyContent:'center',alignItems: 'center',marginTop:16}}>
            Latitude: {this.state.currentLatitude}
          </Text>
          <Text style={{justifyContent:'center',alignItems: 'center',marginTop:16}}>
          currentAccuracy: {this.state.currentAccuracy}
          </Text>
          <Text style={{justifyContent:'center',alignItems: 'center',marginTop:16}}>
            PositionCountChange: {this.state.positionCountChange}
          </Text>
       </View>
    )
 }
}



//Styles
const styles = StyleSheet.create ({
 container: {
    flex: 1,
    alignItems: 'center',
    justifyContent:'center',
    marginTop: 50,
    padding:16,
    backgroundColor:'white'
 },
 boldText: {
    fontSize: 30,
    color: 'red',
 }
})