package database;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BackendApplication;

import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseClient {

    private final Firestore database;

    public FirebaseClient(){
        database = FirestoreClient.getFirestore();
    }

    private static final Logger logger = LoggerFactory.getLogger(FirebaseAuth.class);

    public <T> void addDocument(DocumentReference documentReference, T object) {
        try{
            documentReference.set(object).get();
        } catch (InterruptedException e){
            logger.error("Firebase request was interrupted. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (CancellationException e){
            logger.error("Firebase request was cancelled, please check your database. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (ExecutionException e){
            logger.error("Firebase request was interrupted while execution, please check your database.  Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public <T> void addToCollection(CollectionReference collectionReference, String documentName, T object) {
        try{
            collectionReference.document(documentName).set(object).get();
        } catch (InterruptedException e){
            logger.error("Firebase request was interrupted. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (CancellationException e){
            logger.error("Firebase request was cancelled, please check your database. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (ExecutionException e){
            logger.error("Firebase request was interrupted while execution, please check your database.  Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public <T> void updateDocument(DocumentReference documentReference, T newObject) {
        try{
            documentReference.set(newObject).get();
        } catch (InterruptedException e){
            logger.error("Firebase request was interrupted. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (CancellationException e){
            logger.error("Firebase request was cancelled, please check your database. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (ExecutionException e){
            logger.error("Firebase request was interrupted while execution, please check your database.  Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }

    }

    public DocumentReference getDocument(String collectionName, String documentName){
        return database.collection(collectionName).document(documentName);
    }

    public void deleteDocument(CollectionReference collectionReference, String documentName){
        try {
            DocumentReference documentReference = collectionReference.document(documentName);
            documentReference.delete().get();
        } catch (InterruptedException e){
            logger.error("Firebase request was interrupted. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (CancellationException e){
            logger.error("Firebase request was cancelled, please check your database. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (ExecutionException e){
            logger.error("Firebase request was interrupted while execution, please check your database.  Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }

    }

    public CollectionReference getCollection(String collectionName){
        return database.collection(collectionName);
    }
}
