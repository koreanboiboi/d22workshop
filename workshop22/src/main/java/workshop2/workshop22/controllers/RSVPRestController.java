package workshop2.workshop22.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import workshop2.workshop22.models.RSVP;
import workshop2.workshop22.services.RSVPService;

@RestController
@RequestMapping(path = "/api/rsvp",produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPRestController {
    

    @Autowired
    private RSVPService rsvpSvc;

    @GetMapping
    public ResponseEntity<String> getALLRSVP(@RequestParam (required = false) String q) throws Exception{

        List<RSVP> rsvp = rsvpSvc.getALLRSVP(q);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (RSVP r: rsvp)
        arrBuilder.add(r.toJSON());
        JsonArray result = arrBuilder.build();

        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(result.toString());

    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRSVP(@RequestBody String json){
        RSVP rsvp = null;
        RSVP rsvpResult = null;
        JsonObject response;

        try {
            rsvp = RSVP.create(json);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            response = Json.createObjectBuilder().add("error", e.getMessage()).build();

            return ResponseEntity.badRequest().body(response.toString());
        }

        rsvpResult = rsvpSvc.insertPurchaseOrder(rsvp);
        response = Json.createObjectBuilder().add("rsvpId", rsvpResult.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response.toString());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putRSVP(@RequestBody String json) {
        RSVP rsvp = null;
        boolean rsvpResult = false;
        JsonObject resp;
        try {
            rsvp = RSVP.create(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(resp.toString());
        }

        rsvpResult = rsvpSvc.updateRSVP(rsvp);
        resp = Json.createObjectBuilder()
                .add("updated", rsvpResult)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resp.toString());
    }

    @GetMapping(path = "/count")
    public ResponseEntity<String> countRSVP(){
        JsonObject responseJo;
        Integer totalCountRsvp = rsvpSvc.getTotalRSVP();

        responseJo = Json.createObjectBuilder().add("total_cnt", totalCountRsvp).build();

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(responseJo.toString());
    }
}
