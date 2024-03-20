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

    public <T> String addDocument(DocumentReference documentReference, T object) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collection = documentReference.set(object);
        return collection.get().getUpdateTime().toString();
    }

    public <T> DocumentReference addToCollection(CollectionReference collectionReference, String documentName, T object) throws ExecutionException, InterruptedException {
        collectionReference.document(documentName).set(object).get();
        return collectionReference.document(documentName).get().get().getReference();
    }

    public <T> String updateDocument(DocumentReference documentReference, T newObject) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collection = documentReference.set(newObject);
        return collection.get().getUpdateTime().toString();
    }

    public DocumentReference getDocument(String collectionName, String documentName){
        return database.collection(collectionName).document(documentName);
    }

    public void deleteDocument(CollectionReference collectionReference, String documentName){
        DocumentReference documentReference = collectionReference.document(documentName);
        documentReference.delete();
    }

    public CollectionReference getCollection(String collectionName){
        return database.collection(collectionName);
    }
}
