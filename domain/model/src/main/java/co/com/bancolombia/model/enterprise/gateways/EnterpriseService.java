package co.com.bancolombia.model.enterprise.gateways;

import co.com.bancolombia.model.enterprise.CreditState;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprise.SuperIntReport;
import co.com.bancolombia.model.enterprisevalidation.EnterpriseValidation;
import reactor.core.publisher.Mono;

public interface EnterpriseService {
    Mono<EnterpriseValidation> validateEnterprise(Enterprise enterprise);

    Mono<Enterprise> searchrestriccions(Enterprise enterprise);

    Mono<CreditState> searchCreditState(Enterprise enterprise);

    Mono<SuperIntReport> searchSuperIntReports(Enterprise enterprise);
}
