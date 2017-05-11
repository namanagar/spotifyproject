import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.methods.ArtistSearchRequest;
import com.wrapper.spotify.methods.RelatedArtistsRequest;
import com.wrapper.spotify.methods.TopTracksRequest;
import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Naman on 5/11/2017.
 */
public class ArtistRecommender {
    private String artistName;
    private List<Artist> relatedArtists;
    private Api api;

    public ArtistRecommender(String artistName){
        this.artistName=artistName;
    }
    public void getArtists(){
        api = Api.builder()
                .clientId("0c62cc69ba06425eaa39080de821a6e9")
                .clientSecret("33cdec2c5c5c4907ab76262f57aa4198")
                .build();
        final ArtistSearchRequest request = api.searchArtists(artistName).market("SE").limit(10).build();
        try{
            final Page<Artist> artistSearchResult = request.get();
            final List<Artist> results = artistSearchResult.getItems();
            if (results.size() == 0){
                System.out.println("Sorry, we couldn't find the requested artist. Try again with another artist!");
            }
            Artist theArtist = results.get(0);
            final RelatedArtistsRequest relatedArtistsRequest = api.getArtistRelatedArtists(theArtist.getId()).build();
            relatedArtists = relatedArtistsRequest.get();
            for (int i = 0; i < relatedArtists.size(); i++){
                System.out.println((i + 1) + ". "+ relatedArtists.get(i).getName());
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
    }

    public void getSongs(int artistNum)throws WebApiException, IOException{
        Artist simArtist = relatedArtists.get(artistNum);
        final TopTracksRequest topTracksRequest = api.getTopTracksForArtist(simArtist.getId(), "US").build();
        List<Track> topTracks = topTracksRequest.get();
        for  (int i = 0; i < topTracks.size(); i++){
            System.out.println(topTracks.get(i).getName());
        }
    }
}
