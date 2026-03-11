package ifw.daw.sger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import ifw.daw.sger.services.StripeService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class StripeWebhookController {

	@Autowired
	StripeService stripeServ;
	
	
	@Value("${stripe.webhook.secret}")
	private String webhookSecret;
	
	
	@PostMapping("/webhook/stripe")
	@ResponseBody
	public ResponseEntity<String> handleStripeWebhook(
	        HttpServletRequest request,
	        @RequestBody String payload,
	        @RequestHeader("Stripe-Signature") String sigHeader) throws Exception {

	    Event event;
	    System.out.println("➡️ WEBHOOK RECIBIDO");
	    try {
	    	System.out.println("Mongolo");
	        event = Webhook.constructEvent(
	            payload,
	            sigHeader,
	            webhookSecret
	        );
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().build();
	    }
	    System.out.println(" no paaaaasaaaaa");
	    System.out.println("Event type: " + event.getType());
	    System.out.println("Event response: " + event.getLastResponse());
	    System.out.println(event.getType());
	    if ("checkout.session.completed".equals(event.getType())) { 
	    	System.out.println("paaaaasaaaaa");
	    	Session session = (Session) event.getDataObjectDeserializer()
	    			.getObject()
	    			.orElse(null);
	        stripeServ.procesarPago(session);
	    }
	    System.out.println("No pasa");

	    return ResponseEntity.ok().build();
	}
	
}
