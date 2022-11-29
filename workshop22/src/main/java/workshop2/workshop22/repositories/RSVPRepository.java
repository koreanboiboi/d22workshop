package workshop2.workshop22.repositories;

import static workshop2.workshop22.repositories.Queries.SELECT_ALL_RSVP;
import static workshop2.workshop22.repositories.Queries.SELECT_ALL_RSVP_BY_NAME;
import static workshop2.workshop22.repositories.Queries.SQL_INSERT_RSVP;
import static workshop2.workshop22.repositories.Queries.*;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import workshop2.workshop22.models.RSVP;
import workshop2.workshop22.models.RSVPTotalCntMapper;

@Repository
public class RSVPRepository {

    @Autowired
    private JdbcTemplate temp;

    public List<RSVP> getAllRSVP (String q) throws Exception {
        final List<RSVP> rsvp = new LinkedList<>();
        SqlRowSet result = null;
        if(q == null){
           result = temp.queryForRowSet(SELECT_ALL_RSVP);
        }else{
            result = temp.queryForRowSet(SELECT_ALL_RSVP_BY_NAME, q);
        }

        while(result.next()){
            rsvp.add(RSVP.create(result));
        }

        return rsvp;
    }

    // searchrsvpbyname is already enable by the above code do we still need to implement this?

    // public RSVP searchRSVPByName(String name) {
    //     // prevent inheritance
    //     final List<RSVP> rsvps = new LinkedList<>();
    //     // perform the query
    //     final SqlRowSet rs = temp.queryForRowSet(SELECT_ALL_RSVP_BY_NAME, name);

    //     while (rs.next()) {
    //         rsvps.add(RSVP.create(rs));
    //     }
    //     return rsvps.get(0);
    // }

    public RSVP insertRSVP(final RSVP rsvp){

        KeyHolder keyholder = new GeneratedKeyHolder();
        temp.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RSVP,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rsvp.getName());
            ps.setString(2, rsvp.getEmail());
            ps.setString(3, rsvp.getPhone());
            System.out.println("Confirmation date > " + rsvp.getConfirmationDate());
            ps.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()));
            ps.setString(5, rsvp.getComments());
            return ps;
        }, keyholder);

        BigInteger primaryKeyVal = (BigInteger) keyholder.getKey();
        rsvp.setId(primaryKeyVal.intValue());
        return rsvp;

    }

    public boolean updateRSVP(final RSVP rsvp) {
        return temp.update(SQL_UPDATE_RSVP_BY_EMAIL,
                rsvp.getName(),
                rsvp.getEmail(),
                rsvp.getPhone(),
                new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()),
                rsvp.getComments(),
                rsvp.getEmail()) > 0;
    }
    
    public Integer getTotalRSVP(){
        List<RSVP>rsvp = temp.query(SQL_TOTAL_COUNT_RSVP, new RSVPTotalCntMapper(), new Object [] {});

        return rsvp.get(0).getTotalCnt();
    }
}
