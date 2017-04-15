package FileExpo;

import java.awt.Dimension;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileTable extends AbstractTableModel {

    private List<List<Object>> rows;

    private String[] columns = {"Icon", "File", "Size",
            "Last Modified"};

    public FileTable() {
        this.rows = new ArrayList<List<Object>>();
        DateFormat.getDateInstance(DateFormat.DEFAULT);
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return rows.get(row).get(column);
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 2:
                return Long.class;
            case 3:
                return Date.class;
            default:
                return String.class;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void addRow(DefaultMutableTreeNode node){

        File file = (File) node.getUserObject();
        if(file.isDirectory()) {
            for (File child : file.listFiles()) {
                List<Object> list = new ArrayList<Object>();
                list.add(FileSystemView.getFileSystemView().getSystemIcon(child));
                list.add(child.getName().toString());
                list.add(child.length());
                list.add(new Date(child.lastModified()));
                this.rows.add(list);
            }
        }else
        {
            List<Object> list = new ArrayList<Object>();
            list.add(FileSystemView.getFileSystemView().getSystemIcon(file));
            list.add(file.getName().toString());
            list.add(file.length());
            list.add(new Date(file.lastModified()));
            this.rows.add(list);
        }



    }

    public void removeRows() {
        this.rows.clear();
    }

    public int setColumnWidths(JTable table) {


        int width = setColumnWidth(table, 0, 30);
        width += setColumnWidth(table, 1, 200);
        width += setColumnWidth(table, 2, 70);
        width += setColumnWidth(table, 3, 80);

        return width + 30;
    }

    private int setColumnWidth(JTable table, int column, int width) {
        TableColumn tableColumn = table.getColumnModel()
                .getColumn(column);
        JLabel label = new JLabel((String) tableColumn.getHeaderValue());
        Dimension preferred = label.getPreferredSize();
        width = Math.max(width, (int) preferred.getWidth() + 14);
        tableColumn.setPreferredWidth(width);
        tableColumn.setMinWidth(width);

        return width;
    }



}
