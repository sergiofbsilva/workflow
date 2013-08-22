/*
 * @(#)WorkflowProcessFiles.java
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
package module.workflow.presentationTier.renderers;

import java.util.Iterator;
import java.util.List;

import module.workflow.domain.ProcessFile;
import module.workflow.domain.WorkflowProcess;

import org.apache.commons.lang.StringUtils;

import pt.ist.bennu.core.i18n.BundleUtil;
import pt.ist.fenixWebFramework.renderers.OutputRenderer;
import pt.ist.fenixWebFramework.renderers.components.HtmlBlockContainer;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlLink;
import pt.ist.fenixWebFramework.renderers.components.HtmlParagraphContainer;
import pt.ist.fenixWebFramework.renderers.components.HtmlScript;
import pt.ist.fenixWebFramework.renderers.components.HtmlText;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

/**
 * 
 * @author João Antunes
 * @author Shezad Anavarali
 * @author Luis Cruz
 * @author Paulo Abrantes
 * 
 */
public class WorkflowProcessDocuments extends OutputRenderer {

    private String downloadFormat;
    private String removeFormat;

    public String getDownloadFormat() {
        return downloadFormat;
    }

    public void setDownloadFormat(String downloadFormat) {
        this.downloadFormat = downloadFormat;
    }

    public String getRemoveFormat() {
        return removeFormat;
    }

    public void setRemoveFormat(String removeFormat) {
        this.removeFormat = removeFormat;
    }

    @Override
    protected Layout getLayout(Object arg0, Class arg1) {
        return new Layout() {

            @Override
            public HtmlComponent createComponent(Object arg0, Class arg1) {
                WorkflowProcess process = (WorkflowProcess) arg0;

                HtmlBlockContainer container = new HtmlBlockContainer();

                List<Class<? extends ProcessFile>> displayableFileTypes = process.getDisplayableFileTypes();
                for (Class<? extends ProcessFile> fileType : displayableFileTypes) {
                    container.addChild(generate(process, fileType, displayableFileTypes.size() > 1));
                }

                return container;
            }

            private HtmlComponent generate(WorkflowProcess process, Class<? extends ProcessFile> fileType, boolean shouldShowLabel) {
                List<? extends ProcessFile> files = process.getFileDocuments(fileType);

                HtmlBlockContainer blockContainer = new HtmlBlockContainer();
                HtmlParagraphContainer container = new HtmlParagraphContainer();
                blockContainer.addChild(container);

                if (shouldShowLabel) {
                    container.addChild(new HtmlText(pt.ist.bennu.core.util.legacy.BundleUtil.getLocalizedNamedFroClass(fileType)
                            + ": "));
                }

                if (shouldShowLabel && files.isEmpty()) {
                    container.addChild(new HtmlText("-"));
                } else {
                    Iterator<? extends ProcessFile> iterator = files.iterator();

                    while (iterator.hasNext()) {
                        ProcessFile file = iterator.next();

                        if (!file.isInNewStructure()) {
                            continue;
                        }

                        HtmlLink downloadLink = new HtmlLink();
                        String filename = file.getDisplayName();
                        if (StringUtils.isEmpty(filename)) {
                            filename = file.getFilename();
                        }
                        //joantune: taking care of the confirmation action in case this is 
                        //a file whose access is logged
                        downloadLink.setId("access-" + file.getExternalId());
                        downloadLink.setIndented(false);
                        downloadLink.setBody(new HtmlText(filename));
                        downloadLink.setUrl(RenderUtils.getFormattedProperties(getDownloadFormat(), file));
                        container.addChild(downloadLink);
                        if (file.shouldFileContentAccessBeLogged()) {
                            container.addChild(accessConfirmation(file));
                        }

                        if (file.isPossibleToArchieve() && file.getProcess().isFileEditionAllowed()) {

                            HtmlLink removeLink = new HtmlLink();
                            removeLink.setIndented(false);
                            removeLink.setId("remove-" + file.getExternalId());
                            removeLink.setBody(new HtmlText("("
                                    + RenderUtils.getResourceString("WORKFLOW_RESOURCES", "link.removeFile") + ")"));
                            removeLink.setUrl(RenderUtils.getFormattedProperties(getRemoveFormat(), file));

                            container.addChild(removeLink);
                            container.addChild(removeConfirmation(file));
                        }

                        if (iterator.hasNext()) {
                            container.addChild(new HtmlText(", "));
                        }
                    }
                }
                return blockContainer;

            }

            private HtmlComponent accessConfirmation(ProcessFile file) {
                HtmlScript script = new HtmlScript();
                script.setContentType("text/javascript");
                String displayName = file.getDisplayName();
                if (displayName == null) {
                    displayName = file.getFilename();
                }
                script.setScript("linkConfirmationHookLink('access-"
                        + file.getExternalId()
                        + "', '"
                        + BundleUtil.getString("resources/WorkflowResources", "label.fileAccess.logged.confirmMessage",
                                displayName) + "' , '" + displayName + "');");
                return script;
            }

            private HtmlComponent removeConfirmation(ProcessFile file) {
                HtmlScript script = new HtmlScript();
                script.setContentType("text/javascript");
                String displayName = file.getDisplayName();
                if (displayName == null) {
                    displayName = file.getFilename();
                }
                script.setScript("linkConfirmationHook('remove-" + file.getExternalId() + "', '"
                        + BundleUtil.getString("resources/WorkflowResources", "label.fileRemoval.confirmation", displayName)
                        + "' , '" + displayName + "');");
                return script;
            }

        };
    }
}
