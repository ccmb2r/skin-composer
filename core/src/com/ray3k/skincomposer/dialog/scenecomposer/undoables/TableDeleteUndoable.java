package com.ray3k.skincomposer.dialog.scenecomposer.undoables;

import com.ray3k.skincomposer.data.ColorData;
import com.ray3k.skincomposer.data.DrawableData;
import com.ray3k.skincomposer.dialog.DialogSceneComposer;
import com.ray3k.skincomposer.dialog.scenecomposer.DialogSceneComposerModel;

public class TableDeleteUndoable implements SceneComposerUndoable {
    private DialogSceneComposerModel.SimTable table;
    private DialogSceneComposerModel.SimActor parent;
    private DialogSceneComposer dialog;
    
    public TableDeleteUndoable() {
        dialog = DialogSceneComposer.dialog;
        table = (DialogSceneComposerModel.SimTable) dialog.simActor;
        parent = table.parent;
    }
    
    @Override
    public void undo() {
        if (parent instanceof DialogSceneComposerModel.SimCell) {
            ((DialogSceneComposerModel.SimCell) parent).child = table;
        } else if (parent instanceof DialogSceneComposerModel.SimGroup) {
            ((DialogSceneComposerModel.SimGroup) parent).children.add(table);
        }
        
        if (dialog.simActor != table) {
            dialog.simActor = table;
            dialog.populateProperties();
            dialog.populatePath();
        }
    }
    
    @Override
    public void redo() {
        if (parent instanceof DialogSceneComposerModel.SimCell) {
            ((DialogSceneComposerModel.SimCell) parent).child = null;
        } else if (parent instanceof DialogSceneComposerModel.SimGroup) {
            ((DialogSceneComposerModel.SimGroup) parent).children.removeValue(table, true);
        }
        
        if (dialog.simActor != parent) {
            dialog.simActor = parent;
            dialog.populateProperties();
            dialog.populatePath();
        }
    }
    
    @Override
    public String getRedoString() {
        return "Redo \"Delete Table\"";
    }
    
    @Override
    public String getUndoString() {
        return "Undo \"Delete Table\"";
    }
}