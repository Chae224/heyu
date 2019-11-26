import React from 'react';
import { Image, Button, View, Text } from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';

class HomeScreen extends React.Component {

  static navigationOptions = {
    headerTitle: () => <LogoTitle />,
    headerRight: () => (
      <Button
        onPress={() => alert('This is a button!')}
        title="Info"
        color="#fff"
      />
    ),
  }

  state = ({infos : {testId : "ça marche", testName : "Richardo Amigo"}});

  render() {
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Home Screen</Text>
        <Button
          title="Go to Details"
          onPress={() => this.props.navigation.navigate('Details')}
        />
      </View>
    );
  }
}

class DetailsScreen extends React.Component {

  static navigationOptions = ({navigation}) => {
    return {
      title: navigation.getParam('otherParam', 'Details screen')
    };
  };

  render() {
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Details Screen</Text>
        <Text>{JSON.stringify(this.props.navigation.getParam('testName', 'NO-ID'))}</Text>
        <Button
          title="Go to Details... again"
          onPress={() => this.props.navigation.push('Details')}
        />
        <Button
          title="Go to Home"
          onPress={() => this.props.navigation.navigate('Home.TestHome')}
        />
         <Button
          title="Go Back"
          onPress={() => this.props.navigation.goBack()}
        />
         <Button
          title="Change title"
          onPress={() => this.props.navigation.setParams({otherParam : 'Title changed'})}
        />
      </View>
    );
  }
}

const RootStack = createStackNavigator(
  {
    Home:{
      TestHome :HomeScreen,
      Details: DetailsScreen
    }
  },
  {
    initialRouteName: 'Home.TestHome',
    defaultNavigationOptions: {
      headerStyle: {
        backgroundColor: '#f4511e',
      }
    }
  }
);

const AppContainer = createAppContainer(RootStack);

class LogoTitle extends React.Component {
  render(){
    return(
      <Image
        source={require('./banana.png')}
        style={{ width: 100, height: 50 }}
        />    );
  }
}

export default class App extends React.Component {
  render() {
    return <AppContainer />;
  }
}

