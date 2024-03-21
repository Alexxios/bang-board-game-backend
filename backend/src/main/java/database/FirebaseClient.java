package database;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseClient {

    private final Firestore database;

    public FirebaseClient(){
        database = FirestoreClient.getFirestore();
    }

    public <T> void addDocument(DocumentReference documentReference, T object) throws ExecutionException, InterruptedException {
        new Thread(() -> {
            ApiFuture<WriteResult> collection = documentReference.set(object);
        }).start();
    }

    public <T> void addToCollection(CollectionReference collectionReference, String documentName, T object) throws ExecutionException, InterruptedException {
        new Thread(() -> {
            try {
                collectionReference.document(documentName).set(object).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public <T> void updateDocument(DocumentReference documentReference, T newObject) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collection = documentReference.set(newObject);
        collection.get();
    }

    public DocumentReference getDocument(String collectionName, String documentName){
        return database.collection(collectionName).document(documentName);
    }

    public void deleteDocument(CollectionReference collectionReference, String documentName){
        new Thread(() -> {
            DocumentReference documentReference = collectionReference.document(documentName);
            documentReference.delete();
        }).start();
    }

    public CollectionReference getCollection(String collectionName){
        return database.collection(collectionName);
    }
}
