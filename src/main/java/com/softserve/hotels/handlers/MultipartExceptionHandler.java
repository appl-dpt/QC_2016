package com.softserve.hotels.handlers;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@WebFilter("/renter/uploadPhoto")
public class MultipartExceptionHandler extends OncePerRequestFilter {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MaxUploadSizeExceededException e) {
            handle(request, response, e);
        } catch (ServletException e) {
            LOG.info("servlet exception");
            if (e.getRootCause() instanceof MaxUploadSizeExceededException) {
                handle(request, response, (MaxUploadSizeExceededException) e.getRootCause());
            } else {
                throw e;
            }
        } catch (MultipartException e) {
            LOG.debug(e);
            handle(request, response);
        }

    }

    private static void handle(HttpServletRequest request, HttpServletResponse response,
            MaxUploadSizeExceededException e) throws ServletException, IOException {
        String redirect = "/Booking/UploadPhotoError/" + request.getParameter("idApartment");
        LOG.info(e);
        response.sendRedirect(redirect);
    }

    private static void handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String redirect = "/Booking/UploadPhotoError/" + request.getParameter("idApartment");
        response.sendRedirect(redirect);
    }

}
