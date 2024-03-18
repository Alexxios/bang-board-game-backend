package server.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import database.FirebaseClient;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


@Service
public class UsersService {

    private static final String collectionName = "users";

    @Autowired
    private FirebaseClient firebaseClient;

    public boolean isUserExists(String userId) throws ExecutionException, InterruptedException {
        CollectionReference collection = firebaseClient.getCollection(collectionName);
        List<QueryDocumentSnapshot> users = collection.get().get().getDocuments();
        for (QueryDocumentSnapshot document : users){
            User user = document.toObject(User.class);
            if (Objects.equals(user.getNickname(), userId)){
                return true;
            }
        }
        return false;
    }

    public void deleteUser(String nickname) {
        CollectionReference collection = firebaseClient.getCollection(collectionName);
        firebaseClient.deleteDocument(collection, nickname);
    }

    public User addUser(User user) throws ExecutionException, InterruptedException {
        CollectionReference collection = firebaseClient.getCollection(collectionName);
        firebaseClient.addToCollection(collection, user.getNickname(), user);
        return user;
    }
}
