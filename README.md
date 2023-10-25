## Carplace
Simple Android application shat shows a car list from API.

The project developed with multi-moduled architecure. This provides separation of concerns, and all of modules has own responsibilities.
<br />
 - Model module includes network models, sealed filter classes and IDataSource interface.

 - Network-Retrofit module includes Retrofit networking and model module. In the network-retrofit module, dependency injection used with simple kotlin object. I only expose DataSource class for other modules, other classes are internal or private. Processes in network-retrofit module are working on background thread and error safety.
 
 - Network-Ktor module includes KTOR networking and model module. I've developed this module before I realize the networking should be Retrofit. This module also has unit tests and also implements IDataSource. _HTTP Client module could be changed between Ktor and Retrofit. If you want to try this, update HTTP_CLIENT value on gradle.properties file._

 - Application module imports all of the modules and all features. I’ve used feature based packaging. While developing application, I’ve used __single activity pattern__ and I’ve used _Navigation Component_ for switching fragments. With the MVVM pattern view should dumb, that only observes the changes of ViewModel. When the ViewModel initializes, it has loading state then it gets product list from repository. When the response comes, ViewModel emits the UI state with StateFlow then UI collects the result.

## Here are the screen recordings of application
<a href="#"><img width="19%" height="auto" src="https://github.com/emirhansoylu/Carplace/blob/master/recording_light.gif" height="100px"/></a>
<a href="#"><img width="19%" height="auto" src="https://github.com/emirhansoylu/Carplace/blob/master/recording_dark.gif" height="100px"/></a>

## Highlighted Specs
<img align="left" alt="Deno" width="24px" src="https://user-images.githubusercontent.com/6463980/28998869-97bca9dc-7a03-11e7-8a95-3bbe9c1f7926.png"/> Developed with Kotlin!
- ⚡ Dark/Light, Portrait/Land support.
- ⚡ Multi moduled architecture. (app, network-ktor, network-retrofit, model)
- ⚡ Unit tests on network-ktor module.
- ⚡ Used Android Architecture and modern programming libraries. (such as Retrofit, Ktor, Hilt, Glide, Navigation Component, Splash Screen Api, Room, Coroutines, Flow)
- ⚡ Preferred Material 3 design principles.
- ⚡ Single Activity pattern and transaction animations.
- ⚡ Caching and error handling.
<br />

## Extras
- 💪 MVVM Pattern
- 💪 Reactive programming with flows and coroutines
- 💪 Received properties from BuildConfigField
