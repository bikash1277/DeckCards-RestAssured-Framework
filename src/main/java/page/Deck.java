package page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deck {
    private boolean success;
    private String deck_id;
    private  int remaining;
    private  boolean shuffled;

}
