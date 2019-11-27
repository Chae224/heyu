
import React from 'react';
import {Dimensions, Slider, View, Text,  StyleSheet, Button} from 'react-native';
import { connect } from 'react-redux'

//const used for the styles const
const ScreenWidth = Math.round(Dimensions.get('window').width);
const screenHeight = Math.round(Dimensions.get('window').height);

class SearchScreen extends React.Component {


  state = {

    sliderValue : 10,
    // heyUserNearU: [],

  }

  dispatchRadius(){
    console.log("Le dispatch se fait");
    const action = { type: "UPDATE_RADIUS", value: this.state.sliderValue }
    console.log(action.value);
    this.props.dispatch(action)

  }

  


  handleOnSliderChangeFetch = (sliderValue) => {
  this.dispatchRadius()

  }

  handleOnSliderChange = (sliderValue) => {
     this.setState({sliderValue:Math.round(this.toExponential(sliderValue))})

  }

  toExponential = (x) => {
    // return Math.pow(x,3) + 10;
    // return 10+Math.pow(x,4)
    // return 1000*Math.exp(x)-1000
    return 10*Math.exp(x)-10

  }

  toLisible = (x) => {
    let unit ="metres";
    if(x>1000){
    x=Math.round(x/1000);
    unit="kilometres"
    }
  return radius = ""+x+" "+unit;

  }

  render() {
      
      return (
        <View style={styles.container}>
            <View>

              <Button title="registering" onPress={() => this.props.navigation.navigate('HeyURegistration')}/>

            </View>

            <Text>Longitude = {this.props.heyUserLocation.heyUserLongitude}</Text>
            <Text>Latitude = {this.props.heyUserLocation.heyUserLatitude}</Text>
            <Text>name = {this.props.heyUserAuthentication.heyUserName}</Text>
            <Text>mp = {this.props.heyUserAuthentication.heyUserPassword}</Text>


            <View style={styles.sliderContainer}>
              <Text>Search radius</Text>
              <Slider
                style={styles.slider}
                minimumValue={0}
                // maximumValue={125.9919}  //x³+10
                // maximumValue={37.605}  //x4
                //  maximumValue={7.7}  //1000exp
                 maximumValue={12.206}  //100exp


                onValueChange={this.handleOnSliderChange}
                onSlidingComplete={this.handleOnSliderChangeFetch}
              />

               <Text style={styles.sliderInfos}>{this.toLisible(this.state.sliderValue)}</Text>
            </View>
        </View>
      );
  }
}


const styles = StyleSheet.create({
  container: {
    height : screenHeight,
  },

  sliderContainer: {
    flex:1,
    height : 130,
    marginBottom : 160,
    alignItems: "center",
    justifyContent: "flex-end",
  },

  sliderInfos : {
    color : "red",
    marginTop : 30,
    fontWeight : "bold"
  },

  slider : {
    transform: [{ scaleX: 3.1 }, { scaleY: 3.1 }],
    width : ScreenWidth / 3,
  },
});


  




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
     heyUserNearU:state.loc.heyUserNearU



  }


}




export default connect(mapStateToProps, mapDispatchToProps)(SearchScreen)
