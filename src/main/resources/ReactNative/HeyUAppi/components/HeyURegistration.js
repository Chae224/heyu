import React from 'react';
import {View, Text,  StyleSheet, TextInput, Button} from 'react-native';
import { connect } from 'react-redux'

class Registration extends React.Component {
    constructor(props) {
        super(props);
        this.state= {
            heyUserAuthentication:{
                heyUserName: 'gasper',
                heyUserPassword: '123',
                heyUserConfirmPassword: '456',
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

    leaveRegistering(){
        console.log("leaveRegistration")
        if(this.state.heyUserIsConnected == true){
        this.props.navigation.navigate('Search')
        }   
    }


    register(){
    console.log("fetch de registering")
        fetch('http://192.168.1.64:8080/registering', {
            method: 'POST',
            headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                heyUserAuthentication:{
                heyUserName: this.state.heyUserAuthentication.heyUserName,
                heyUserPassword: this.state.heyUserAuthentication.heyUserPassword,
                heyUserConfirmPassword: this.state.heyUserAuthentication.heyUserConfirmPassword
                }
            }),
             }).then((response) => response.json())
            .then((responseJson) => {
                this.setState({heyUserIsConnected: responseJson.connected});
                this.setState({messageSent: responseJson.messageSent});
                this.updateAuthentication();
                this.updateConnected();
                this.leaveRegistering();
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
          onChangeText={(text) => this.setState({heyUserAuthentication:{ ...this.state.heyUserAuthentication, heyUserName:text}})}
          value={this.state.text}
        />

        <TextInput
          style={{height: 40}}
          placeholder="Password"
          onChangeText={(text) => this.setState({heyUserAuthentication:{ ...this.state.heyUserAuthentication, heyUserConfirmPassword:text}})}
          value={this.state.text}
        />


        <TextInput
          style={{height: 40}}
          placeholder="Verify Password"
          onChangeText={(text) => this.setState({heyUserAuthentication:{heyUserConfirmPassword:text}})}
          value={this.state.text}
        />
        <Button title="registering" onPress={() =>this.register() }/>

        <Text>{this.state.messageSent}</Text>
        <Button
          title="Go to Logging"
          onPress={() => this.props.navigation.navigate('Logging')}
        />


      </View>
    );
  }





}






const mapDispatchToProps = (dispatch) => {
  return {
    dispatch: (action) => { dispatch(action) }
  }
}


export default connect(mapDispatchToProps)(Registration)

