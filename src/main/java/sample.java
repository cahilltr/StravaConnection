import com.strava.api.v3.ApiClient;
import com.strava.api.v3.ApiException;
import com.strava.api.v3.Configuration;
import com.strava.api.v3.auth.OAuth;
import com.strava.api.v3.models.DetailedActivity;
import com.strava.api.v3.models.SummaryAthlete;
import com.strava.api.v3.services.ActivitiesApi;
import com.strava.api.v3.services.AthletesApi;

public class sample {

    public static void main(String[] args) throws ApiException {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure OAuth2 access token for authorization: strava_oauth
//        OAuth strava_oauth = (OAuth) defaultClient.getAuthentication("strava_oauth");
//        strava_oauth.setAccessToken("YOUR ACCESS TOKEN");

        OAuth strava_oauth = (OAuth) defaultClient.getAuthentication("strava_oauth");
        strava_oauth.setAccessToken("your_access_token");


        ActivitiesApi apiInstance = new ActivitiesApi();
        //activity ID is 4 mile creek intervals on 4/17
        DetailedActivity detailedActivity = apiInstance.getActivityById(1515633651L,false);

        System.out.println(detailedActivity);


        AthletesApi athletesApi = new AthletesApi();

        SummaryAthlete summaryAthlete = new SummaryAthlete();


        System.out.println(athletesApi.getLoggedInAthlete());

    }

}
