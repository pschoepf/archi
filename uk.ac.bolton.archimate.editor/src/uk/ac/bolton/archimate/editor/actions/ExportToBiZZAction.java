/*******************************************************************************
 * Copyright (c) 2010 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 *******************************************************************************/
package uk.ac.bolton.archimate.editor.actions;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

import uk.ac.bolton.archimate.editor.model.IModelExporter;
import uk.ac.bolton.archimate.editor.model.export.BiZZdesignExporter;
import uk.ac.bolton.archimate.model.IArchimateModel;

/**
 * Export to BiZZdesign Architect Action
 * 
 * @author Phillip Beauvoir
 */
public class ExportToBiZZAction extends AbstractModelSelectionAction {
    
    public ExportToBiZZAction(IWorkbenchWindow window) {
        super("Model To BiZZdesign Architect...", window);
        setId("uk.ac.bolton.archimate.editor.action.exportBiZZ");
    }
    
    @Override
    public void run() {
        IArchimateModel model = getActiveArchimateModel();
        if(model != null) {
            try {
                IModelExporter exporter = new BiZZdesignExporter();
                exporter.export(model);
            }
            catch(IOException ex) {
                MessageDialog.openError(workbenchWindow.getShell(), "Error saving file", ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    protected void updateState() {
        setEnabled(getActiveArchimateModel() != null);
    }
}