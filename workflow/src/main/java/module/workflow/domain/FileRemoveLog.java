/*
 * @(#)FileRemoveLog.java
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

import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.i18n.BundleUtil;

/**
 * 
 * @author Paulo Abrantes
 * 
 */
public class FileRemoveLog extends FileRemoveLog_Base {

    public FileRemoveLog(WorkflowProcess process, User person, String... argumentsDescription) {
        super();
        init(process, person, argumentsDescription);
    }

    @Override
    public String getDescription() {
        return BundleUtil.getString("resources/WorkflowResources", "label.description.FileRemoveLog", getDescriptionArguments()
                .toArray(new String[] {}));
    }

}
