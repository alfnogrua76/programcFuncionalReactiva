package co.com.bancolombia.model.enterprise;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Enterprise {
    private boolean validaty;
    private LinkedList<Restriction> restrictions;
}
