package FileExpo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Ashiqur Rahman on 4/16/2017.
 */
public class Table {
    FileTable tableMoodel;
    JTable table;
    Table()
    {
        tableMoodel=new FileTable();
        tableMoodel.addRow(new DefaultMutableTreeNode(Main.currentDir));
        table=new JTable(tableMoodel);
        tableMoodel.setColumnWidths(table);
    }
    JTable getTable()
    {
        return table;
    }
}
