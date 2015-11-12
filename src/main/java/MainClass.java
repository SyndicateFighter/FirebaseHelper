import com.firebase.client.AuthData;


/**
 * Created by alexandernohe on 11/11/15.
 */
public class MainClass {

    public static void main(String [ ] args) throws InterruptedException {
        System.out.println("Start");
        Firebaser firebaser = new Firebaser();
        firebaser.createNewUser("madeupemail@esri.com", "passwordz");
        firebaser.logThatUserIn("madeupemail@esri.com", "passwordz");
        AuthData authdata = firebaser.getAuthData();
        firebaser.StoreData("Spoiler Alert, Harry kills Dumbledore");
        System.out.println(authdata.getUid());
        System.out.println("OVER");
    }

}
