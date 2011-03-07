/*******************************************************************************
 * Copyright (c) 2010 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 *******************************************************************************/
package uk.ac.bolton.archimate.editor.diagram.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import uk.ac.bolton.archimate.editor.preferences.Preferences;
import uk.ac.bolton.archimate.editor.ui.ArchimateNames;
import uk.ac.bolton.archimate.editor.ui.ColorFactory;
import uk.ac.bolton.archimate.editor.ui.FontFactory;
import uk.ac.bolton.archimate.editor.utils.PlatformUtils;
import uk.ac.bolton.archimate.editor.utils.StringUtils;
import uk.ac.bolton.archimate.model.IArchimateElement;
import uk.ac.bolton.archimate.model.IDiagramModelArchimateObject;
import uk.ac.bolton.archimate.model.IDiagramModelObject;


/**
 * Abstract Figure for containing a IDiagramModelObject
 * 
 * @author Phillip Beauvoir
 */
public abstract class AbstractDiagramModelObjectFigure extends Figure
implements IDiagramModelObjectFigure {
    
    private IDiagramModelObject fDiagramModelObject;
    
    protected Color fFillColor;
    protected Color fFontColor;
    
    public AbstractDiagramModelObjectFigure(IDiagramModelObject diagramModelObject){
        fDiagramModelObject = diagramModelObject;
        setUI();
    }
    
    public IDiagramModelObject getDiagramModelObject() {
        return fDiagramModelObject;
    }
    
    /**
     * Set the UI
     */
    abstract protected void setUI();
    
    /**
     * Set the font to that in the model, or failing that, as per user's default
     */
    protected void setFont() {
        String fontName = fDiagramModelObject.getFont();
        setFont(FontFactory.get(fontName));
        
        // Need to do this after font change
        getTextControl().revalidate();
    }
    
    @Override
    public void setFont(Font f) {
        if(PlatformUtils.isWindows()) {
            f = FontFactory.getAdjustedWindowsFont(f);
        }
        
        super.setFont(f);
    }
    
    /**
     * Set the fill color to that in the model, or failing that, as per default
     */
    protected void setFillColor() {
        String val = fDiagramModelObject.getFillColor();
        Color c = ColorFactory.get(val);
        if(c != fFillColor) {
            fFillColor = c;
            repaint();
        }
    }
    
    /**
     * Set the font color to that in the model, or failing that, as per default
     */
    protected void setFontColor() {
        String val = fDiagramModelObject.getFontColor();
        Color c = ColorFactory.get(val);
        if(c != fFontColor) {
            fFontColor = c;
            getTextControl().setForegroundColor(c);
        }
    }
    
    /**
     * @return The Fill Color to use
     */
    protected Color getFillColor() {
        if(fFillColor == null) {
            return ColorFactory.getDefaultColor(fDiagramModelObject);
        }
        return fFillColor;
    }
    
    protected void setToolTip() {
        if(!Preferences.doShowViewTooltips()) {
            setToolTip(null); // clear it in case user changed Prefs
            return;
        }
        
        String text = StringUtils.safeString(fDiagramModelObject.getName());
        
        if(getToolTip() == null) {
            setToolTip(new ToolTipFigure());
        }
        
        ((ToolTipFigure)getToolTip()).setText(text);
        if(fDiagramModelObject instanceof IDiagramModelArchimateObject) {
            IArchimateElement element = ((IDiagramModelArchimateObject)fDiagramModelObject).getArchimateElement();
            String type = ArchimateNames.getDefaultName(element.eClass());
            ((ToolTipFigure)getToolTip()).setType("Type: " + type);
        }
    }

    public void dispose() {
    }
}