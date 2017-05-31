#Android API Exercise

Tech stack:

- Dagger 2
- Retrofit
- OkHttp
- Gson
- Picasso
- Android Components Room

Abstraction Levels:

- GuideRepository ( access to persisted database with Android Components Room library )
- GuideInteractor ( manages requests for network calls and local storage )
- GuidePresenter ( passes data models returned from interactor to be displayed by view through interface )
- GuideView ( aka MainActivity or fragment currently )

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