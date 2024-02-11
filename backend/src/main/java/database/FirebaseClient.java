package database;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firestore.v1.Write;

import javax.swing.text.Document;
import java.util.concurrent.ExecutionException;

public class FirebaseClient {

    private static final Firestore database;

    static {
        database = FirestoreClient.getFirestore();
    }

    public static <T> String addToDocument(DocumentReference documentReference, T object) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collection = documentReference.set(object);
        return collection.get().getUpdateTime().toString();
    }

    public static <T> DocumentReference addToCollection(CollectionReference collectionReference, T object) throws ExecutionException, InterruptedException {
        return collectionReference.add(object).get();
    }

    public static <T> DocumentReference addToCollection(CollectionReference collectionReference, String documentName, T object) throws ExecutionException, InterruptedException {
        collectionReference.document(documentName).set(object).get();
        return collectionReference.document(documentName).get().get().getReference();
    }

    public static String deleteFormDocument(DocumentReference documentReference) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> result = documentReference.delete();
        return result.get().getUpdateTime().toString();
    }

    public static <T> String updateDocument(DocumentReference documentReference, T newObject) throws ExecutionException, InterruptedException {
        return addToDocument(documentReference, newObject);
    }

    public static DocumentReference getDocument(String collectionName, String documentName){
        return database.collection(collectionName).document(documentName);
    }

    public static DocumentReference getDocument(CollectionReference collectionReference, String documentName){
        return collectionReference.document(documentName);
    }

    public static void deleteDocument(CollectionReference collectionReference, String documentName){
        DocumentReference documentReference = collectionReference.document(documentName);
        documentReference.delete();
    }

    public static DocumentSnapshot getDocumentSnapshot(CollectionReference collectionReference, String documentName) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = collectionReference.document(documentName);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        return future.get();
    }

    public static CollectionReference getCollection(String collectionName){
        return database.collection(collectionName);
    }

    public static CollectionReference getCollection(DocumentReference documentReference, String collectionName){
        return documentReference.collection(collectionName);
    }
}
