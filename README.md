# Android_End_Clothing_App

A demo app presenting architecture based on MVVM, Repository and Interactor patterns - including Dependency Injection implementation.

The architecture of app has been based on the MVVM design pattern.

As the code consists of one view, it contains exactly one Fragment (encapsulated within an Activity), and its appropriate ViewModel.

Model layer (data) has been organized using Repository design pattern. It's the only gate for data access from ViewModels' perspective.

Repository uses Interactors to communicate with API (Network Interactor) and with internal database (DataBase Interactor).

Communication with the Back-End has been constructed using Retrofit library (to connect with the external API), and RxJava (to provide correct data flow).

Internal database (caching) has been based on Room library.

To make the app maintainable and testable I have decided to use Dagger2 library, providing one-way direction of injecting dependencies. In this situation we can be sure that Activity contains ViewModel, but ViewModel doesn't know about Activity. Similarly ViewModel contains Repository, Repository contains Interactors. All of these dependencies are injected dynamically.

Code of the app has been organized into 3 main directories - Injection (dependency injection files), Data (model layer files), and Features (view and its related files).

Testing part of the app consists of Unit and UI Testing. Unit Tests has been written for each architectural layer (viewmodels, repository, interactors) using JUnit and Mockito libraries. UI Tests are created using the Espresso framework.

Please do not hesitate to ask in case of any further questions. I will be happy to clarify each uncertainty.

I hope you like my app.
