import React from 'react';
import {View, Text,  StyleSheet, Button, Image} from 'react-native';
import Swiper from 'react-native-swiper'

const styles = StyleSheet.create({
  wrapper: {},
  slide1: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#9DD6EB'
  },
  text: {
    color: '#fff',
    fontSize: 30,
    fontWeight: 'bold'
  }
})


class Search extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <Swiper style={styles.wrapper} showsButtons={false}>
                {heyUserNearU.map((user, index)=> {
                    return(
                        <View style={styles.slide1} key={index}>
                            <Image source={{uri:user.heyUserPic}}></Image>
                            <Text style={styles.text}>{user.heyUserMessage}</Text>
                        </View>
                    )
                })}
            </Swiper>
        )
    }
}

export default Search;