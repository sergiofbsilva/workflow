/**
 * 
 */
package module.metaWorkflow.exceptions;

import pt.ist.bennu.core.domain.exceptions.DomainException;

/**
 * @author João Antunes (joao.antunes@tagus.ist.utl.pt) - 14 de Jun de 2012
 * 
 * 
 */
@SuppressWarnings("serial")
public class MetaWorkflowDomainException extends DomainException {
    public static final String bundle = "resources/MetaWorkflowResources";

    public MetaWorkflowDomainException(String key, String... args) {
        super(bundle, key, args);
    }

    public MetaWorkflowDomainException(String key, Throwable cause, String... args) {
        super(cause, bundle, key, args);
    }

}
