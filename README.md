<p align="center">
  <h2>Albums App</h2>

  <p>
  A simple Android app built with Kotlin consuming provided API endpoint to display top 100 albums from apple music. It is built using the Repository and MVVM patterns as well as Architecture Components.

Min Api Level : 21 Supports over 99% devices

Build System : Gradle
</p>

### Architecture

MVVM is a Model-View-ViewModel architecture that removes the tight coupling between each component.
Most importantly, in this architecture the children don't have the direct reference to the parent,
they have the reference through observables.

### Layers

- Domain
    - It represents the business logic of the Android application. It consists of the use-cases and
      use-case can be used for handling business logic. Apps ui layer will be communicating to data
      layer through this layer.

- Data
    - It represents the data and the business logic of the Android application. It consists of the
      logic - local and remote data source, model classes and the repository.

- View
    - It consists of the UI code. It sends the user action to the ViewModel but does not get the
      response back directly. To get the response, it has to subscribe to the observables which the
      ViewModel exposes to it.

- ViewModel
    - This is a bridge between the View and the Domain layer). It does not have any clue which view
      has to use it as it does not have a direct reference to the View.

### Libraries Added

- [Jetpack🚀](https://developer.android.com/jetpack)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI
      related data in a lifecycle conscious way and act as a channel between the repository and the
      ui
    - [Compose](https://developer.android.com/jetpack/compose) - latest toolkit provided by Android
      team to develop ui
    - [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer
      over SQLite

- [Coroutines](https://developer.android.com/kotlin/coroutines) - a concurrency design pattern that
  you can use on Android to simplify code that executes asynchronously
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out
  of the box.
- [Gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert
  Java Objects into JSON and back.
- [Coil](https://coil-kt.github.io/coil/compose/) - An image loading and caching library for Android
  focused on smooth scrolling
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) -
  Lightweight dependency injection library for android.
- [Truth](https://truth.dev/) - It is an assertion library used in Android testing.
- [JUnit](https://junit.org/junit4/) - It is a simple framework to write repeatable tests.
- [Mockito](https://junit.org/junit4/) - It is a simple framework to write repeatable tests.