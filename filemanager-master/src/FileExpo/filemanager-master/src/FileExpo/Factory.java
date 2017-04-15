package FileExpo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Objects;

/**
 * Created by Ashiqur Rahman on 4/11/2017.
 */
public class Factory {
    Mediator mediator;
    public Factory(Mediator mediator) {
        this.mediator=mediator;
    }

    public JScrollPane getcomponent(int i)
    {
        JScrollPane jp;
        if(i==1){
            Table table=new Table();
            mediator.setFileTable(table.tableMoodel);
             jp= new JScrollPane(table.getTable());
        }
        else
        {
            TitlesModel list=new TitlesModel(new DefaultMutableTreeNode(Main.currentDir));
            mediator.setTitlesModel(list);
            jp= new JScrollPane(list.getList());
        }
        jp.setPreferredSize(new Dimension(500,480));
        return jp;

    }


}
