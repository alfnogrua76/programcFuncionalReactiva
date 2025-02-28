package co.com.bancolombia.usecase.esubscription;

import co.com.bancolombia.model.enterprise.*;
import co.com.bancolombia.model.enterprise.gateways.EnterpriseService;
import co.com.bancolombia.model.enterprisevalidation.EnterpriseValidation;
import co.com.bancolombia.model.esubscription.ESubscription;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

@RequiredArgsConstructor
public class ESubscriptionUseCase {
    private final EnterpriseService entService;
    private final ESubscription eSubscripcion;

    public Mono<ESubscription> subscribeEnterprise(Enterprise enterprise){

        return entService.validateEnterprise(enterprise)
                //Mono.defer() -> es un concepto de una funcion peresosa//osea solo se ejecute cuando se necesita
                //Ojo el  Map y el flapmap son perezosos por naturaleza --> Mono<EnterpriseValidation> validateEnterprise(Enterprise enterprise);
                .switchIfEmpty(Mono.defer(()-> Mono.error(new EnterpriseNotFoundException())))
                //Ojo este resultado del flapMapdepende de lo que devuelve el servicio entService.validateEnterprise(enterprise)
                //osea en este caso un
                .flatMap((ev)-> searchRestrictions(ev))
                .flatMap(this::searchCreditStateAndReports)
                .map((t)-> addInfoToValidation(t))
//Para no colocar todo esto construimos un nuevo metodo -> addInfoToValidation
//                    return t.getT3().toBuilder()
//                            .enterprise(enterprise)
//                            .creditState(t.getT1())
//                            .superIntReport(t.getT2())
//                            .build();
//                })
                .map(ESubscription::createSubscription)
                .onErrorMap(
                        (e)-> !(e instanceof EnterpriseNotFoundException),
                        (e2)-> new EnterpriseNotValidException(e2.getMessage())
                );

    }



    private EnterpriseValidation addInfoToValidation(Tuple3<CreditState, SuperIntReport, EnterpriseValidation> t) {
        return t.getT3().toBuilder()
                .enterprise(t.getT3().getEnterprise())
                .creditState(t.getT1())
                .superIntReport(t.getT2())
                .build();
    }

    private Mono<EnterpriseValidation> searchRestrictions(EnterpriseValidation ev) {
        return entService.searchrestriccions(ev.getEnterprise())
                .map((e) -> {
                    boolean hasRest = e.getRestrictions().size() != 0;
                    return ev.toBuilder().enterprise(ev.getEnterprise()).hasRestrictions(hasRest).build();
                });
    }

    private Mono<Tuple3<CreditState, SuperIntReport, EnterpriseValidation>> searchCreditStateAndReports(EnterpriseValidation ev) {
        return Mono.zip(
                entService.searchCreditState(ev.getEnterprise()),
                entService.searchSuperIntReports(ev.getEnterprise()),
                Mono.just(ev)
        );
    }

}
