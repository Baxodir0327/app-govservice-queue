package uz.pdp.govqueue.utils;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.govqueue.exceptions.MyException;
import uz.pdp.govqueue.security.MyUserDetails;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CommonUtils {

    public static Timestamp todayWithStartTime() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
    }

    public static Timestamp yesterdayWithStartTime() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalTime.of(0, 0)));
    }

    public static MyUserDetails getCurrentUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser"))
            throw new MyException("OKa yopiq yul", HttpStatus.UNAUTHORIZED);

        return (MyUserDetails) authentication.getPrincipal();
    }
}
