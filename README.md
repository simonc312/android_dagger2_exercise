#Android API Exercise

Tech stack:

- [Dagger 2] [google/dagger2]
- [Retrofit] [square/retrofit]
- [OkHttp 3] [square/okhttp]
- [Gson] [google/gson]
- [Picasso] [square/picasso]
- [Android Components Room] [google/room]
- [Facebook Stetho] [fb/stetho]


[square/retrofit]: https://square.github.io/retrofit/
[square/okhttp]: https://github.com/square/okhttp
[square/picasso]: https://square.github.io/picasso/

[google/dagger2]: https://google.github.io/dagger/
[google/gson]: https://github.com/google/gson
[google/room]: https://developer.android.com/topic/libraries/architecture/room.html

[fb/stetho]: https://facebook.github.io/stetho/

Abstraction Levels:

- GuideRepository ( access to persisted database with Android Components Room library )
- GuideInteractor ( manages requests for network calls and local storage )
- GuidePresenter ( passes data models returned from interactor to be displayed by view through interface )
- GuideView ( aka MainActivity or fragment currently )

Facebook Stetho provides detailed request and response visualization through Chrome much like Charles Proxy.

Example of Instrumented UI Test for MainActivity demonstrates using TestApplication to build FakeMainAppComponent.
Using the OkHttpIdlingResource allows Espresso to be notified when adapter is loaded from network request data and can perform UI interaction.
IntentsTestRule is used because I wanted to verify that the chrome intent was started.

The MainActivity implements this interface, so it can pass along the deserialized response in the form of List of Guide objects
to the recyclerview adapter for displaying as UI.

Clicking the view holder will redirect to the guide url on click.
Submitting a query in the search view will fetch matches from local storage.

Other possible future improvements:

- include a search bar to filter by name, date, venue location.
- request for user location to filter by nearby upcoming guides.
- display as a calendar grid view where guides can stretch across multiple days based on duration