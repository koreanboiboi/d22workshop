package workshop2.workshop22.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import workshop2.workshop22.models.RSVP;
import workshop2.workshop22.repositories.RSVPRepository;

@Service
public class RSVPService {

    @Autowired
    private RSVPRepository rsvpRepo;

    public List<RSVP> getALLRSVP(String q) throws Exception{
        return rsvpRepo.getAllRSVP(q);
    }
    
    public RSVP insertPurchaseOrder(final RSVP rsvp){
        return rsvpRepo.insertRSVP(rsvp);
    }

    public boolean updateRSVP(final RSVP rsvp){

        return rsvpRepo.updateRSVP(rsvp);
    }

    public Integer getTotalRSVP() {
        return rsvpRepo.getTotalRSVP();
    }
}
