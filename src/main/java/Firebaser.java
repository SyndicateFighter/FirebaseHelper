import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by alexandernohe on 11/11/15.
 */
public class Firebaser {
    private Firebase myFirebaseRef = new Firebase("https://testingnohe.firebaseio.com/");
    private AuthData authData;

    public void createNewUser(String email, String password) throws InterruptedException {
        final CountDownLatch done = new CountDownLatch(1);
        System.out.println("STARTINGTOADDUSER");
        this.myFirebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid: " + result.get("uid"));
                done.countDown();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                System.out.println("THERE WAS AN ERROR");
                // there was an error
                done.countDown();
            }
        });
        done.await();
    }

    public void logThatUserIn(String email, String password) throws InterruptedException {
        final CountDownLatch done = new CountDownLatch(1);
        this.myFirebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                setAuthData(authData);
                done.countDown();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                done.countDown();
            }
        });
        done.await();
    }

    final private void setAuthData(AuthData newAuthData)
    {
        this.authData = newAuthData;
    }

    public AuthData getAuthData()
    {
        return this.authData;
    }

    public void StoreData(String object) throws InterruptedException {
        final CountDownLatch done = new CountDownLatch(1);
        this.myFirebaseRef.child(getAuthData().getUid()).setValue(object, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    done.countDown();
                } else {
                    System.out.println("Data saved successfully.");
                    done.countDown();
                }
            }
        });
        done.await();
    }
}
