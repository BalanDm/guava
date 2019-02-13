import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionManager {

    private static AtomicInteger i = new AtomicInteger(0);

    private static Cache<String, String> tokens = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .build();

    private static LoadingCache<String, UserProduct> products = CacheBuilder.newBuilder()
            .expireAfterAccess(0, TimeUnit.SECONDS)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build(new CacheLoader<String, UserProduct>() {
                @Override
                public UserProduct load(String s) throws Exception {
                    return SessionManager.load(s);
                }
            });


    public static UserProduct getProducts(String sessionToken) throws ExecutionException {
        return products.get(getUserIdBySessT(sessionToken));
    }

    static String getUserIdBySessT(String token) {
        System.out.println("token = " + token + " time = " + System.currentTimeMillis());
        return tokens.asMap().get(token);
    }

    private static UserProduct load(String userId) throws InterruptedException {
        if (i.get() == 0) {
            System.out.println("STOP  Thread = " + Thread.currentThread().getId());
            Thread.sleep(5000);
        }
        System.out.println("CALL LOAD USER_ID = " + userId + " Thread " + Thread.currentThread().getId());
        if (userId.equals("1")) {
            return new UserProduct(1L);
        } else if (userId.equals("2")) {
            return new UserProduct(2L);
        }
        return null;
    }

    public static AtomicInteger getI() {
        return i;
    }

    static String generateSessToken(String login) throws UnsupportedEncodingException {
        byte[] bytes = (login + new Date() + UUID.randomUUID().toString()).getBytes(Charsets.UTF_8.name());
        return UUID.nameUUIDFromBytes(bytes).toString();
    }

    public static void addSessionToken(String sessionToken, String userId) {
        tokens.put(sessionToken, userId);
    }
}
