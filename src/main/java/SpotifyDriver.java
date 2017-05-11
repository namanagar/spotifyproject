import java.util.Scanner;

/**
 * Created by Naman on 5/6/2017.
 */
public class SpotifyDriver {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the playlist generator!\nMENU:\n1: PLAYLIST BY SONG TITLE\n2: PLAYLIST BY ARTIST TITLE\n3: ARTIST RECOMMENDER\nEnter your menu choice:");
        int choice = Integer.parseInt(scan.nextLine());
        if (choice == 1){
            System.out.println("Enter the name of a song:");
            String songName = scan.nextLine();
            System.out.println("Enter how many songs you want in your playlist: ");
            int PLAYLIST_SIZE = Integer.parseInt(scan.nextLine());
            System.out.println("Creating playlist...\n");
            SongTitlePlaylist playlist = new SongTitlePlaylist(songName, PLAYLIST_SIZE);
            playlist.getPlaylist();
        }
        else if (choice == 2){
            System.out.println("Enter the name of an artist: ");
            String artistName = scan.nextLine();
            System.out.println("Enter how many songs you want in your playlist: ");
            int PLAYLIST_SIZE = Integer.parseInt(scan.nextLine());
            System.out.println("Creating playlist...\n");
            ArtistTitlePlaylist playlist = new ArtistTitlePlaylist(artistName, PLAYLIST_SIZE);
            playlist.getPlaylist();
        }
        else if (choice == 3){
            System.out.println("Enter the name of an artist: ");
            String artistName = scan.nextLine();
            ArtistRecommender artistRec = new ArtistRecommender(artistName);
            System.out.println("Getting related artists...");
            artistRec.getArtists();
            System.out.println("Pick an artist by number to get songs you may like by them! Press 0 to Exit.");
            int artistNum = Integer.parseInt(scan.nextLine()) - 1;
            if (artistNum == -1){
                System.out.println("Bye!");
            }
            else{
                try {
                    artistRec.getSongs(artistNum);
                }
                catch (Exception e){
                    e.getMessage();
                }
            }
        }
    }
}
