import React from 'react';
import {Dimensions, Slider, View, Text,  StyleSheet, Button} from 'react-native';

//const used for the styles const
const ScreenWidth = Math.round(Dimensions.get('window').width);
const screenHeight = Math.round(Dimensions.get('window').height);

export default class SearchScreen extends React.Component {

  state = {
    sliderValue : 10,



  }

  handleOnSliderChangeFetch = (sliderValue) => {


    //Pas fini relocaliser ici le fetch du geoloc

    

    // fetch('http://192.168.8.105:8080/updateLocation', {
    //   method: 'POST',
    //   headers: {
    //   Accept: 'application/json',
    //   'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify({
    //   heyUserAuthentication: this.props.heyUserAuthentication,
    //   heyUserLocation: {
    //     ... heyUserLocation,
    //     heyUserSearchRadius:this.state.sliderValue,
    //   },
     
    //   }),
    //    }).then((response) => response.json())
    //   .then((responseJson) => {
    //     this.setState({ heyUserNearU:responseJson.heyUserNearU });
    //       console.log(this.state.heyUserNearU);
    //       console.log("longueur du tableau = "+ this.state.heyUserNearU.length);
    //       return responseJson.heyUserNearU;
    //   })
    //   .catch((error) => {
    //       console.error(error);
    //   });






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

