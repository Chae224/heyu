https://facebook.github.io/react-native/docs/getting-started.html#android-development-environment
https://facebook.github.io/react-native/docs/running-on-device
https://reactnavigation.org/docs/en/getting-started.html

On installe npm, android studio les sdk (aussi ceux de lollipop pour moi)


On installe react natif:
npm install -g react-native-cli
react-native init ProjectName



On va dans l'app :
 cd ProjectName



On installe la geoloc:
npm install @react-native-community/geolocation --save



On install les peers requis:
npm install -g npm-install-peers
npm-install-peers
npm install typescript
npm install eslint


On install cocoapods, et variables env:
sudo gem install cocoapods
export GEM_HOME=$HOME/.gem
export PATH=$GEM_HOME/bin:$PATH

Paste in : android/app/src/main/AndroidManifest.xml:
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> 


lancer react native server:
react-native start

puiis:

react-native run-android


pour build un apk dans /android:
gradlew assembleRelease

pour ouvrior un port:
iptables -A INPUT -p tcp -m tcp --dport 7777 -j ACCEPT # ouvrir le port 777
iptables -D INPUT -p tcp -m tcp --dport 7777 -j ACCEPT # annuler l'ouverture
iptables -L # regarder ce qui est ouvert et ce qui l'est pas



npm install --save react-native-range-slider

ouvrir posgres a l'ext:
iptables -A INPUT -p tcp -m tcp --dport 5432 -j ACCEPT
sudo ufw unable
ufw allow 5432
/etc/postgresql/12/main/pg_hba.conf
sudo nano pg_hba.conf
à la fin rajouter :
host    all             all             192.168.8.1/24          trust


dans le fichier postgresql.conf:
decomenter la ligne liste et mettre *
listen_addresses = '*'          # what IP address(es) to listen on; (avant le * c'etait localhost)

dans le terminal taper /etc/init.d/postgresql restart
sudo ufw disable
sudo ufw enable

reouvrir les ports 8080 et 8081



export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/tools/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools




