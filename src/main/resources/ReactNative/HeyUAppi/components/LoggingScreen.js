import React from 'react';
import {Button, View, Text, TextInput,  StyleSheet, Image ,PermissionsAndroid,Platform} from 'react-native';
import { connect } from 'react-redux'


class LoggingScreen extends React.Component{

  static navigationOptions = {
    title: 'Logging'
  };




  constructor(props) {
    super(props);
    this.state= {
        heyUserAuthentication:{
            heyUserName: 'gasper',
            heyUserPassword: '123',
            
        },
        heyUserIsConnected: false,
        messageSent:"Veuillez saisir les informations",
     
    }
}



updateAuthentication = () => {
    const action = { type: "UPDATE_AUTH", value: this.state.heyUserAuthentication }
    this.props.dispatch(action)
}

updateConnected = () => {
    const action = { type: "UPDATE_CONNECT", value: this.state.heyUserIsConnected}
    this.props.dispatch(action)
}

leaveLogging(){
    console.log("leaveLogging")
    if(this.state.heyUserIsConnected == true){
    this.props.navigation.navigate('Search')
    }   
}


logging(){
console.log("fetch de Logging")
    fetch('http://192.168.1.64:8080/login', {
        method: 'POST',
        headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            heyUserAuthentication:{
            heyUserName: this.state.heyUserAuthentication.heyUserName,
            heyUserPassword: this.state.heyUserAuthentication.heyUserPassword,
            }
        }),
         }).then((response) => response.json())
        .then((responseJson) => {
            this.setState({heyUserIsConnected: responseJson.connected});
            this.setState({messageSent: responseJson.messageSent});
            this.updateAuthentication();
            this.updateConnected();
            this.leaveLogging();
            console.log(this.state.heyUserIsConnected)
            return responseJson.heyUserIsConnected;
        })
        .catch((error) => {
            console.error(error);
        });


        
}



render() {
return (
  <View style={{padding: 10}}>
    <TextInput
      style={{height: 40}}
      placeholder="UserName"
     //onChangeText={(text) => this.setState({heyUserAuthentication:{heyUserName:text}})}
     onChangeText={(text) => this.setState({heyUserAuthentication:{ ...this.state.heyUserAuthentication, heyUserName:text}})}

      value={this.state.text}
    />

    <TextInput
      style={{height: 40}}
      placeholder="Password"
      onChangeText={(text) => this.setState({heyUserAuthentication:{ ...this.state.heyUserAuthentication, heyUserPassword:text}})}
      value={this.state.text}
    />



    <Button title="logging" onPress={() =>this.logging() }/>

    <Text>{this.state.messageSent}</Text>
    <Button
      title="Go to Registering"
      onPress={() => this.props.navigation.navigate('HeyURegistration')}
    />

    <Text>Longitude = {this.props.heyUserLocation.heyUserLongitude}</Text>
    <Text>Latitude = {this.props.heyUserLocation.heyUserLatitude}</Text>
    <Text>{this.state.heyUserAuthentication.heyUserName}</Text>
    <Text>{this.state.heyUserAuthentication.heyUserPassword}</Text>

    </View>
);
}




}




const mapStateToProps = (state) => {

  return {

    heyUserLocation:state.loc.heyUserLocation
  }


}


const mapDispatchToProps = (dispatch) => {
  return {
    dispatch: (action) => { dispatch(action) }
  }
}


export default connect(mapStateToProps, mapDispatchToProps)(LoggingScreen)

