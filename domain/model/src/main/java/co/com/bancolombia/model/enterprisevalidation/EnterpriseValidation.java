package co.com.bancolombia.model.enterprisevalidation;
import co.com.bancolombia.model.enterprise.CreditState;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprise.SuperIntReport;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EnterpriseValidation {
    private Enterprise enterprise;
    private boolean nitIsValidity;
    private boolean hasRestrictions;
    private CreditState creditState;
    private SuperIntReport superIntReport;
}
