/**
 * 
 */
package module.metaWorkflow.scripts.manual;

import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.scheduler.custom.CustomTask;
import module.metaWorkflow.domain.WorkflowMetaType;
import module.metaWorkflow.domain.WorkflowMetaTypeVersion;
import module.workflow.domain.WorkflowSystem;

/**
 * @author João Antunes (joao.antunes@tagus.ist.utl.pt) - 14 de Jun de 2012
 * 
 *         FENIX-345 passing all of the relations that were directly on
 *         WorkflowMetaType to WorkflowMetaTypeVersion
 * 
 */
public class MigrateDataToMetaTypeVersion extends CustomTask {

    /*
     * (non-Javadoc)
     * 
     * @see pt.ist.bennu.core.domain.scheduler.WriteCustomTask#doService()
     */
    @Override
    public void runTask() {

        int migratedWorkflowMetaTypes = 0;
        int wronglyMigrated = 0;
        int totalNrMetaTypes = 0;

        try {
            for (VirtualHost virtualHost : MyOrg.getInstance().getVirtualHostsSet()) {
                VirtualHost.setVirtualHostForThread(virtualHost);

                //FENIX-345 passing all of the relations that were directly on WorkflowMetaType to WorkflowMetaTypeVersion
                WorkflowSystem workflowSystem = WorkflowSystem.getInstance();
                if (workflowSystem == null) {
                    continue;
                }
                for (WorkflowMetaType workflowMetaType : WorkflowSystem.getInstance().getMetaTypesSet()) {
                    totalNrMetaTypes++;
                    if (workflowMetaType.getVersionsSet().isEmpty()) {
                        //let's create the new version which will use the relations 
                        WorkflowMetaTypeVersion metaTypeVersion = new WorkflowMetaTypeVersion(workflowMetaType);
                        metaTypeVersion
                                .publish("Migração de dados, corrido a partir da Task de migração MigrateDataToMetaTypeVersion");
                        migratedWorkflowMetaTypes++;
                    }
                }

                //let's check the newly migrated instances
                for (WorkflowMetaType workflowMetaType : WorkflowSystem.getInstance().getMetaTypesSet()) {
                    if (workflowMetaType.getVersionsSet().isEmpty()) {
                        wronglyMigrated++;
                    } else if (!workflowMetaType.getMetaProcesses().containsAll(workflowMetaType.getMetaProcessesOld())) {
                        wronglyMigrated++;
                    } else if (!workflowMetaType.getProcessStates().containsAll(workflowMetaType.getProcessStatesOld())) {
                        wronglyMigrated++;
                    } else if (!workflowMetaType.getFieldSet().equals(workflowMetaType.getFieldSetOld())) {
                        wronglyMigrated++;
                    }
                }
            }
        } finally {
            VirtualHost.releaseVirtualHostFromThread();
        }

        taskLog("Summary:");
        taskLog("From " + totalNrMetaTypes + " metaTypes, migrated " + migratedWorkflowMetaTypes + " and " + wronglyMigrated
                + " had errors in the migration");

    }
}
