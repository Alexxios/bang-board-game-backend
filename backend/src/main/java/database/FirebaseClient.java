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
        documentReference.set(object).get();
    }

    public <T> void addToCollection(CollectionReference collectionReference, String documentName, T object) throws ExecutionException, InterruptedException {
        collectionReference.document(documentName).set(object).get();
    }

    public <T> void updateDocument(DocumentReference documentReference, T newObject) throws ExecutionException, InterruptedException {
        documentReference.set(newObject).get();
    }

    public DocumentReference getDocument(String collectionName, String documentName){
        return database.collection(collectionName).document(documentName);
    }

    public void deleteDocument(CollectionReference collectionReference, String documentName){
        try {
            DocumentReference documentReference = collectionReference.document(documentName);
            documentReference.delete().get();
        } catch (Exception e){

        }

    }

    public CollectionReference getCollection(String collectionName){
        return database.collection(collectionName);
    }
}
