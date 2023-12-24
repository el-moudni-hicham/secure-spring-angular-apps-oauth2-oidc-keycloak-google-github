package dev.moudni.customersfrontappthymeleaf.web;

import dev.moudni.customersfrontappthymeleaf.entites.Customer;
import dev.moudni.customersfrontappthymeleaf.model.Product;
import dev.moudni.customersfrontappthymeleaf.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    private CustomerRepository customerRepository;
    private ClientRegistrationRepository clientRegistrationRepository;
    @Value("${inventory.service.base.uri}")
    private String inventoryServiceBaseUri;

    public CustomerController(CustomerRepository customerRepository, ClientRegistrationRepository clientRegistrationRepository) {
        this.customerRepository = customerRepository;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String customers(Model model){
        List<Customer> customersList = customerRepository.findAll();
        model.addAttribute("customers", customersList);
        return "customers";
    }

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    @GetMapping("/oauth2Login")
    public String oauth2Login(Model model) {
        String authorizationRequestBaseUri = "oauth2/authorization";
        Map<String, String> oauth2AuthenticationUrls = new HashMap();
        // Get all clients Google, Github and Keycloak
        Iterable<ClientRegistration> clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        clientRegistrations.forEach(registration ->{
            oauth2AuthenticationUrls.put(registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getRegistrationId());
        });
        List<String> keys = new ArrayList<>(oauth2AuthenticationUrls.keySet());
        List<String> values = new ArrayList<>(oauth2AuthenticationUrls.values());

        model.addAttribute("urls", oauth2AuthenticationUrls);
        model.addAttribute("keys", keys);
        model.addAttribute("values", values);
        return "oauth2Login";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";
    }

    @GetMapping("/products")
    public String products(Model model){
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;
            DefaultOidcUser oidcUser = (DefaultOidcUser) oAuth2AuthenticationToken.getPrincipal();
            String jwtTokenValue=oidcUser.getIdToken().getTokenValue();
            RestClient restClient = RestClient.create(inventoryServiceBaseUri);
            List<Product> products = restClient.get()
                    .uri("/products")
                    .headers(httpHeaders -> httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer "+jwtTokenValue))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>(){});
            model.addAttribute("products",products);
            return "products";
        } catch (Exception e){
            return "redirect:/notAuthorized";
        }

    }

    @GetMapping("/auth")
    @ResponseBody
    public Authentication authentication(Authentication authentication){
        return authentication;
    }
}
