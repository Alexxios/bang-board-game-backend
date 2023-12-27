package server.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import database.FirebaseClient;
import models.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class UsersService {

    private static final String collectionName = "users";

    public boolean isUserExists(String userId) throws ExecutionException, InterruptedException {
        CollectionReference collection = FirebaseClient.getCollection(collectionName);
        List<QueryDocumentSnapshot> users = collection.get().get().getDocuments();
        for (QueryDocumentSnapshot document : users){
            User user = document.toObject(User.class);
            if (Objects.equals(user.getUserId(), userId)){
                return true;
            }
        }
        return false;
    }

    public String addUser(String userId) throws ExecutionException, InterruptedException {
        CollectionReference collection = FirebaseClient.getCollection(collectionName);
        FirebaseClient.addToCollection(collection, userId);
        return userId;
    }
}
