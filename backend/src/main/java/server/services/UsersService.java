package server.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import database.FirebaseClient;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;


@Service
public class UsersService {

    private static final String collectionName = "users";

    @Autowired
    private FirebaseClient firebaseClient;

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    public boolean isUserExists(String userId) {
        CollectionReference collection = firebaseClient.getCollection(collectionName);

        try{
            List<QueryDocumentSnapshot> users = collection.get().get().getDocuments();
            for (QueryDocumentSnapshot document : users){
                User user = document.toObject(User.class);
                if (Objects.equals(user.getNickname(), userId)){
                    return true;
                }
            }
        } catch (InterruptedException e){
            logger.error("Firebase request was interrupted. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (CancellationException e){
            logger.error("Firebase request was cancelled, please check your database. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (ExecutionException e){
            logger.error("Firebase request was interrupted while execution, please check your database.  Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }

        return false;
    }

    public void deleteUser(String nickname) {
        CollectionReference collection = firebaseClient.getCollection(collectionName);
        firebaseClient.deleteDocument(collection, nickname);
    }

    public User addUser(User user) {
        CollectionReference collection = firebaseClient.getCollection(collectionName);
        firebaseClient.addToCollection(collection, user.getNickname(), user);
        return user;
    }
}
