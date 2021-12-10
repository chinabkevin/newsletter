package com.thinkdevs.controller;

import com.thinkdevs.services.ConfirmationCodeVerifier;
import com.thinkdevs.services.UnsubscribeService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;
import io.micronaut.web.router.exceptions.UnsatisfiedQueryValueRouteException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

@Controller("/unsubscribe")
public class UnsubscribeController {

    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final UnsubscribeService unsubscribeService;

    public UnsubscribeController(ConfirmationCodeVerifier confirmationCodeVerifier, UnsubscribeService unsubscribeService) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.unsubscribeService = unsubscribeService;
    }

    @Produces(MediaType.TEXT_HTML)
    @View("unsubscribe")
    @Get
    HttpResponse<?> unsubscribe(@Nullable @QueryValue String token){
        if (StringUtils.isEmpty(token)){
            try {
                return HttpResponse.seeOther(new URI("/404"));
            }catch (URISyntaxException ex){
                return HttpResponse.serverError();
            }
        }
        Optional<String> emailOptional = confirmationCodeVerifier.verify(token);
        if (!emailOptional.isEmpty()){
           return notFound();

        }
        unsubscribeService.unsubscribe(emailOptional.get());
        return HttpResponse.ok(Collections.emptyMap());
    }

    private HttpResponse<?> notFound(){
            try {
                return HttpResponse.seeOther(new URI("/404"));
            }catch (URISyntaxException ex){
                return HttpResponse.serverError();
            }

    }

    @Error
    public HttpResponse<?> unsatisfiedQueryValueRouter(HttpRequest request,
                                                       UnsatisfiedQueryValueRouteException exception){
        try {
            return HttpResponse.seeOther(new URI("/404"));
        }catch (URISyntaxException ex){
            return HttpResponse.serverError();
        }
    }



}
