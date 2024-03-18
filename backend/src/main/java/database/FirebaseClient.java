package database;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firestore.v1.Write;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseClient {

    private final Firestore database;

    public FirebaseClient(){
        database = FirestoreClient.getFirestore();
    }

    public <T> String addToDocument(DocumentReference documentReference, T object) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collection = documentReference.set(object);
        return collection.get().getUpdateTime().toString();
    }

    public <T> DocumentReference addToCollection(CollectionReference collectionReference, T object) throws ExecutionException, InterruptedException {
        return collectionReference.add(object).get();
    }

    public <T> DocumentReference addToCollection(CollectionReference collectionReference, String documentName, T object) throws ExecutionException, InterruptedException {
        collectionReference.document(documentName).set(object).get();
        return collectionReference.document(documentName).get().get().getReference();
    }

    public String deleteFormDocument(DocumentReference documentReference) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> result = documentReference.delete();
        return result.get().getUpdateTime().toString();
    }

    public <T> String updateDocument(DocumentReference documentReference, T newObject) throws ExecutionException, InterruptedException {
        return addToDocument(documentReference, newObject);
    }

    public DocumentReference getDocument(String collectionName, String documentName){
        return database.collection(collectionName).document(documentName);
    }

    public DocumentReference getDocument(CollectionReference collectionReference, String documentName){
        return collectionReference.document(documentName);
    }

    public void deleteDocument(CollectionReference collectionReference, String documentName){
        DocumentReference documentReference = collectionReference.document(documentName);
        documentReference.delete();
    }

    public DocumentSnapshot getDocumentSnapshot(CollectionReference collectionReference, String documentName) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = collectionReference.document(documentName);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        return future.get();
    }

    public CollectionReference getCollection(String collectionName){
        return database.collection(collectionName);
    }

    public CollectionReference getCollection(DocumentReference documentReference, String collectionName){
        return documentReference.collection(collectionName);
    }
}
