import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.ArtistRequest;
import com.wrapper.spotify.methods.RelatedArtistsRequest;
import com.wrapper.spotify.methods.TopTracksRequest;
import com.wrapper.spotify.methods.TrackSearchRequest;
import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Naman on 5/11/2017.
 */
public class SongTitlePlaylist {
    private String songName;
    private int numSongs;

    public SongTitlePlaylist(String songName, int numSongs){
        this.songName = songName;
        this.numSongs = numSongs;
    }
    public void getPlaylist(){
        Api api = Api.builder()
                .clientId("0c62cc69ba06425eaa39080de821a6e9")
                .clientSecret("33cdec2c5c5c4907ab76262f57aa4198")
                .build();
        List<Track> playlist = new ArrayList<Track>();
        final TrackSearchRequest request = api.searchTracks(songName).market("US").build();
        try{
            final Page<Track> trackSearchResult = request.get();
            List<Track> results = trackSearchResult.getItems();
            if (results.size() == 0){
                System.out.println("Sorry, we couldn't find the requested song. Try again with another song!");
            }
            Track songID = results.get(0);
            List<SimpleArtist> originalArtists = songID.getArtists();
            final ArtistRequest artistRequest = api.getArtist(originalArtists.get(0).getId()).build();
            Artist theArtist = artistRequest.get();
            final RelatedArtistsRequest relatedArtistsRequest = api.getArtistRelatedArtists(theArtist.getId()).build();
            List<Artist> relatedArtists = relatedArtistsRequest.get();
            relatedArtists.add(0, theArtist); //adds the original artist to the related artists
            for (int i = 0; i < relatedArtists.size(); i++){
                final TopTracksRequest topTracksRequest = api.getTopTracksForArtist(relatedArtists.get(i).getId(), "US").build();
                List<Track> topTracks = topTracksRequest.get();
                for (int j = 0; j < topTracks.size(); j++){
                    playlist.add(topTracks.get(j));
                }
            }
            Collections.shuffle(playlist);
            for (int i = 0; i < numSongs; i++){
                System.out.println(playlist.get(i).getName() + " - " + playlist.get(i).getArtists().get(0).getName());
            }
            // at this point, playlist is created as a list of Track objects. Song titles and artists printed to console above.

            /*final PlaylistCreationRequest playlistCreationRequest = api.createPlaylist("namanafremd", "Generated Playlist based on " + songName)
                    .publicAccess(true)
                    .build();
            Playlist newPlaylist = playlistCreationRequest.get();

            List<String> trackURIs = new ArrayList<String>();
            for (int i = 0; i < playlist.size(); i++){
                trackURIs.add(playlist.get(i).getUri());
            }
            final AddTrackToPlaylistRequest addTrackRequest = api.addTracksToPlaylist("namanafremd", newPlaylist.getId(), trackURIs)
                    .position(0)
                    .build();
            addTrackRequest.get();
            System.out.println("Check your Spotify account!");  THIS CREATES A REAL SPOTIFY PLAYLIST (BROKEN BECAUSE API WRAPPER CAN'T ADD MUSIC TO PLAYLISTS)*/
        }
        catch (Exception e) {
            e.getMessage();
        }
    }

}
