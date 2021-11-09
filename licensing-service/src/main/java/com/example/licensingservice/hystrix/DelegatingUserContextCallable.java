package com.example.licensingservice.hystrix;

import java.util.concurrent.Callable;

import com.example.licensingservice.utils.UserContext;
import com.example.licensingservice.utils.UserContextHolder;

public final class DelegatingUserContextCallable<V> implements Callable<V> {
    private final Callable<V> deletage;
    private UserContext originalUserContext;
    
    public DelegatingUserContextCallable(Callable<V> delegate, UserContext userContext){
        this.deletage = delegate;
        this.originalUserContext = userContext;
    }
    
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);
        try{
            return deletage.call();
        } finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext){
        return new DelegatingUserContextCallable<V>(delegate, userContext);
    }
}
