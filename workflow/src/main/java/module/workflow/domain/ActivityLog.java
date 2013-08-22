/*
 * @(#)ActivityLog.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: Luis Cruz, Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Case Handleing Based Workflow Module.
 *
 *   The Case Handleing Based Workflow Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Workflow Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Workflow Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.workflow.domain;

import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.i18n.BundleUtil;
import pt.utl.ist.fenix.tools.util.Strings;

/**
 * 
 * @author Diogo Figueiredo
 * @author João Antunes
 * @author Luis Cruz
 * @author Paulo Abrantes
 * 
 */
public class ActivityLog extends ActivityLog_Base {

    protected ActivityLog() {
        super();
    }

    public ActivityLog(WorkflowProcess process, User person, String operationName, String... argumentsDescription) {
        super();
        init(process, person, argumentsDescription);
        setOperation(operationName);
    }

    @Override
    public String getDescription() {
        WorkflowActivity<WorkflowProcess, ActivityInformation<WorkflowProcess>> activity =
                getProcess().getActivity(getOperation());

        Strings arguments = getDescriptionArguments();
        if (arguments != null && !arguments.isEmpty()) {
            return BundleUtil.getString(activity.getUsedBundle(), "label.description." + activity.getClass().getName(),
                    getDescriptionArguments().toArray(new String[] {}));
        } else {
            return activity.getLocalizedName();
        }
    }

    @Override
    public boolean isConnectedToCurrentHost() {
        final VirtualHost virtualHost = VirtualHost.getVirtualHostForThread();
        return virtualHost != null && getWorkflowSystem() == virtualHost.getWorkflowSystem();
    }

}
