package pl.polsl;

import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@ApplicationScoped
public class ExceptionUtils {

    public NoSuchElementException noSuchElementException(String message) {
        return new NoSuchElementException(message);
    }

    public AccessDeniedException accessDeniedException(String message) {
        return new AccessDeniedException(message);
    }

}