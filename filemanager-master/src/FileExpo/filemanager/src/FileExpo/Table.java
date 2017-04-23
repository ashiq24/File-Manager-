package FileExpo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by Ashiqur Rahman on 4/16/2017.
 */
public class Table {
    FileTable tableMoodel;
    JTable table;
    Mediator mediator;
    Table(Mediator mediator)
    {
        this.mediator=mediator;
        tableMoodel=new FileTable();
        tableMoodel.addRow(new DefaultMutableTreeNode(Main.currentDir));
        table=new JTable(tableMoodel);
        tableMoodel.setColumnWidths(table);
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    if(row>-1) {
                        File file=tableMoodel.Files.get(row);
                        Main.currentDir=file;
                        mediator.loadchange();
                    }
                }
            }
        });
    }
    JTable getTable()
    {
        return table;
    }
}
