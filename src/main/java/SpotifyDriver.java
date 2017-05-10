import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.wrapper.spotify.*;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.methods.*;
import com.wrapper.spotify.models.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Naman on 5/6/2017.
 */
public class SpotifyDriver {

    private static List<Track> playlist;
    public static void main(String[] args) throws WebApiException, IOException{
        Api api = Api.builder()
                .clientId("0c62cc69ba06425eaa39080de821a6e9")
                .clientSecret("33cdec2c5c5c4907ab76262f57aa4198")
                .build();
        List<Track> playlist = new ArrayList<Track>();
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the playlist generator!\nIf the song you like has a generic title, also type in the artist's name after the song!\n\nEnter the name of a song you like:");
        String songName = scan.nextLine();
        System.out.println("Enter how many songs you want in your playlist: ");
        int PLAYLIST_SIZE = Integer.parseInt(scan.nextLine());
        final TrackSearchRequest request = api.searchTracks(songName).market("US").build();
        try{
            final Page<Track> trackSearchResult = request.get();
            List<Track> results = trackSearchResult.getItems();
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
            for (int i = 0; i < PLAYLIST_SIZE; i++){
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
            System.out.println("Check your Spotify account!");  THIS CREATES A REAL SPOTIFY PLAYLIST (BROKEN)*/
        }
        catch (Exception e) {
            e.getMessage();
        }
    }
}
