/*
 * @(#)WorkflowUnitQueueAutoComplete.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Meta-Workflow Module.
 *
 *   The Meta-Workflow Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Meta-Workflow Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Meta-Workflow Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.metaWorkflow.presentationTier.renderers.autoCompleteProviders;

import pt.ist.bennu.core.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import module.metaWorkflow.domain.WorkflowUnitQueue;
import module.workflow.domain.WorkflowQueue;

/**
 * 
 * @author Shezad Anavarali
 * 
 */
public class WorkflowUnitQueueAutoComplete implements AutoCompleteProvider<WorkflowQueue> {

    @Override
    public Collection<WorkflowQueue> getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
        Set<WorkflowQueue> queues = new HashSet<WorkflowQueue>();
        String[] values = StringNormalizer.normalize(value).toLowerCase().split(" ");

        final Collection<WorkflowUnitQueue> lookUpQueues = WorkflowUnitQueue.readAll();
        for (WorkflowQueue queue : lookUpQueues) {
            final String normalizedQueueName = StringNormalizer.normalize(queue.getName()).toLowerCase();

            if (hasMatch(values, normalizedQueueName)) {
                queues.add(queue);
            }
        }
        return queues;
    }

    private boolean hasMatch(String[] input, String queueNameParts) {
        for (final String namePart : input) {
            if (queueNameParts.indexOf(namePart) == -1) {
                return false;
            }
        }
        return true;
    }

}
