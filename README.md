"# Android API Exercise" 

Tech stack:

- Dagger 2
- Retrofit
- OkHttp
- Gson
- Picasso

The UpcomingGuidePresenter uses the ApiService to make requests and then acts on the callback response
to the UpcomingGuidePresenter.View interface to be easily mockable.

The MainActivity implements this interface, so it can pass along the deserialized response in the form of List of Guide objects
to the recyclerview adaptor for displaying as UI.

The only UI interaction currently implemented for the view holder to is redirect to the guide url on click.

Other possible future improvements:

- include a search bar to filter by name, date, venue location.
- request for user location to filter by nearby upcoming guides.
- display as a calendar grid view where guides can strech across multiple days based on duration