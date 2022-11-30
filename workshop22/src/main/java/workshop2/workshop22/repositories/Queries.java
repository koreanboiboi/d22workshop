package workshop2.workshop22.repositories;

public class Queries {

    public static final String SELECT_ALL_RSVP = "select id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%Y\") as confirmation_date, comments from rsvp";

    public static final String SELECT_ALL_RSVP_BY_NAME = "select id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%Y\") as confirmation_date, comments from rsvp where name = ?";
    
    public static final String SQL_INSERT_RSVP = "insert into rsvp(name, email, phone, confirmation_date, comments) values (?,?,?,?,?)";

    public static final String SQL_UPDATE_RSVP_BY_EMAIL = "update rsvp set name = ?, email = ?, phone = ? , confirmation_date = ?, comments = ? where email = ?";

    public static final String SQL_TOTAL_COUNT_RSVP = "select count(*) as total from rsvp";

    public static final String SQL_SEARCH_RSVP_BY_EMAiL = "select id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%Y\") as confirmation_date , comments from rsvp where email = ?";
}



