package database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import server.BackendApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Level;

public class FirebaseAuth {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseAuth.class);

    public void authorizeFirebase() {
        ClassLoader classLoader = BackendApplication.class.getClassLoader();
        try(InputStream serviceAccount = Objects.requireNonNull(classLoader.getResourceAsStream("serviceAccountKey.json"))){
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (IOException e){
            throw new RuntimeException("Can not authorize firebase, please, check out authorization file");
        }


        logger.info("Firebase authorize successful");
    }
}
