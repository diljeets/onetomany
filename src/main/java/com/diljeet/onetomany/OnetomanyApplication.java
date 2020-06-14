package com.diljeet.onetomany;

import com.diljeet.onetomany.resources.CustomerService;
import com.diljeet.onetomany.resources.OrderService;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("/webapi")
public class OnetomanyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        // register root resource
        classes.add(CustomerService.class);
        classes.add(OrderService.class);
        return classes;
    }
}
