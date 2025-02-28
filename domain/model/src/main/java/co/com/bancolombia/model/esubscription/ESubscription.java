package co.com.bancolombia.model.esubscription;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprisevalidation.EnterpriseValidation;
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
public class ESubscription {
    private EnterpriseValidation validation;
    private Enterprise enterprise;
    private Agreement agreement;

    public static ESubscription createSubscription(EnterpriseValidation ev){
        Agreement agreement = Agreement.builder().build();
        return ESubscription.builder()
                .validation(ev)
                .enterprise(ev.getEnterprise())
                .agreement(agreement)
                .build();
    }
}
