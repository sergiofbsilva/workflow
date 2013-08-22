/**
 * 
 */
package module.workflow.domain.exceptions;

import pt.ist.bennu.core.domain.exceptions.DomainException;

/**
 * @author João Antunes (joao.antunes@tagus.ist.utl.pt) - 11 de Set de 2012
 * 
 *         Convenience class that wraps the {@link DomainException} constructors
 *         with the default workflow resource bundle
 */
public class WorkflowDomainException extends DomainException {
    private static final long serialVersionUID = 1L;

    private static final String bundle = "resources/WorkflowResources";

    /**
     * @param key
     * @param args
     */
    public WorkflowDomainException(String key, String... args) {
        super(bundle, key, args);
    }

    /**
     * @param key
     * @param cause
     * @param args
     */
    public WorkflowDomainException(String key, Throwable cause, String... args) {
        super(cause, bundle, key, args);
    }

}
