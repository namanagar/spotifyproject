import java.util.Scanner;

/**
 * Created by Naman on 5/6/2017.
 */
public class SpotifyDriver {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the playlist generator!\nIf the song you like has a generic title, also type in the artist's name after the song!\n\nEnter the name of a song you like:");
        String songName = scan.nextLine();
        System.out.println("Enter how many songs you want in your playlist: ");
        int PLAYLIST_SIZE = Integer.parseInt(scan.nextLine());
        System.out.println("Creating playlist...\n");
        SongTitlePlaylist playlist = new SongTitlePlaylist(songName, PLAYLIST_SIZE);
        playlist.getPlaylist();
    }
}
