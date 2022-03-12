# Marvel Characters

Marvel Characters is an Android application that shows characters and their descriptions

## Requirements
- [Marvel public & private API keys](https://developer.marvel.com/signup)

## Tech stack
- Room
- Retrofit
- Glide
- Gson
- Dagger 2
- RxJava 3
- JUnit 4
- MVVM + LiveData + Clean Architecture (or it looks like this :D)

## Installation

```batch
git clone git@github.com:plplmax/marvel-characters.git
```

```batch
cd marvel-characters
```

```batch
touch local.properties
```

```batch
printf "publicApiKey=<YOUR_PUBLIC_KEY>\nprivateApiKey=<YOUR_PRIVATE_KEY>" >> local.properties
```

Just to build an APK:

```batch
./gradlew assembleDebug
```

or to build and immediately install it on a running emulator or connected device:

```batch
./gradlew installDebug
```

## Demo

https://user-images.githubusercontent.com/50287455/158012490-09eb5642-a36c-42a1-b30b-db71fcc8ec49.mp4

## Contributing
If you want to make small changes, please create a pull request. Major changes will be rejected.

## License
[MIT](https://choosealicense.com/licenses/mit/)
