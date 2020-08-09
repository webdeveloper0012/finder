package com.finder.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class CommonUtil {
    public static boolean isCurrentUserBelongsToRole(String roleName) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = iterator.next();
            if (grantedAuthority.getAuthority().equals(roleName))
                return true;
        }
        return false;
    }

    public static String getOtp(int digits) {
        Random random = new Random();
        String otp = String.format("%04d", random.nextInt(10000));
        return otp;
    }

    public static boolean isAdminUser() {
        return isCurrentUserBelongsToRole("ADMIN");
    }

    public static String getCurrentUserId() {
        // principal is the user id
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public static Object copyNonNullProperties(Object source, Object target) {
        if (source == null || target == null || target.getClass() != source.getClass()) return null;

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final Field property : target.getClass().getDeclaredFields()) {
            Object providedObject = src.getPropertyValue(property.getName());
            // avoid updating collection objects
            if (providedObject != null && !(providedObject instanceof Collection<?>)) {
                trg.setPropertyValue(
                        property.getName(),
                        providedObject);
            }
        }
        return target;
    }
}
