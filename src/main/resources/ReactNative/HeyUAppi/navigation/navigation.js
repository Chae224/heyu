// Navigation/Navigation.js

import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';

import SearchScreen from '../components/SearchScreen';
import LoggingScreen from '../components/LoggingScreen';
import MyGeolocation from '../components/MyGeolocation';
import HeyURegistration from '../components/HeyURegistration';
import UpdateHeyUserNearUList from '../components/UpdateHeyUserNearUList';



const AppNavigator = createStackNavigator(
    {
      
      Logging: {
        screen: LoggingScreen,
        navigationOptions: {
         title: 'Logging',
         headerStyle: {
          backgroundColor: '#b4815e',
        }
        }
      },
      Search: SearchScreen,
      MyGeolocation: MyGeolocation,
      HeyURegistration:HeyURegistration,
      UpdateHeyUserNearUList:UpdateHeyUserNearUList
    },

    {
      initialRouteName: 'MyGeolocation',
      defaultNavigationOptions: {
        headerStyle: {
          backgroundColor: '#f4511e',
        }
      }
    }
  );
  
  
  export default createAppContainer(AppNavigator);