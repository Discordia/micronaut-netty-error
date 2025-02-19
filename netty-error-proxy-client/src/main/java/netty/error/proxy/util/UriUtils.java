package netty.error.proxy.util;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import java.nio.file.Paths;
import netty.error.proxy.controller.ErrorBody;

public class UriUtils {
    public static String normalize(String path) {
        return Paths.get(path).normalize().toString();
    }

    public static String validatePath(HttpRequest<?> request) {
        final String path = request.getPath();
        if (!path.equalsIgnoreCase(normalize(path))) {
            throw new HttpStatusException(HttpStatus.FORBIDDEN, new ErrorBody("PathInvalid", "Relative paths not allowed"));
        }
        return path;
    }
}
