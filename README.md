# Restaurant
This app has two screens
    1. Show a list of restaurants around Doordash HQ location
    1. Show details of the selected restaurant

* The app uses
    * Dagger for dependency injection and uses subcomponents to limit the life of objects created in dagger graph
    * Retrofit for handling network calls
    * RxJava for handling & sharing the data across different objects
    * Picasso for loading the images
    * Mockito for unit tests
    * Leakcanary for detecting any memory leaks
    
The app supports both Portrait and Landscape orientations. The app leverages ViewModels to reuse data across portrait and landscape orientations
