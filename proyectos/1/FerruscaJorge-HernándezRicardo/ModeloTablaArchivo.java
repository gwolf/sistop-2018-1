import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.filechooser.FileSystemView;

import javax.imageio.ImageIO;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.nio.channels.FileChannel;

import java.net.URL;
public class ModeloTablaArchivo extends AbstractTableModel 
{
    private File[] files;
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    private String[] columns = 
    {
        "Icono",
        "Archivo",
        "Path/nombre",
        "Tamanio",
        "Ultima modificacion",
        "R",
        "W",
        "E",
        "D",
        "F",
    };

    ModeloTablaArchivo() 
    {
        this(new File[0]);
    }

    ModeloTablaArchivo(File[] files) 
    {
        this.files = files;
    }

    public Object getValueAt(int row, int column) 
    {
        File file = files[row];
        switch (column) 
        {
            case 0:
                return fileSystemView.getSystemIcon(file);
            case 1:
                return fileSystemView.getSystemDisplayName(file);
            case 2:
                return file.getPath();
            case 3:
                return file.length();
            case 4:
                return file.lastModified();
            case 5:
                return file.canRead();
            case 6:
                return file.canWrite();
            case 7:
                return file.canExecute();
            case 8:
                return file.isDirectory();
            case 9:
                return file.isFile();
            default:
                System.err.println("Error logico");
        }
        return "";
    }

    public int getColumnCount() 
    {
        return columns.length;
    }

    public Class<?> getColumnClass(int column) 
    {
        switch (column) 
        {
            case 0:
                return ImageIcon.class;
            case 3:
                return Long.class;
            case 4:
                return Date.class;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return Boolean.class;
        }
        return String.class;
    }

    public String getColumnName(int column) 
    {
        return columns[column];
    }

    public int getRowCount() 
    {
        return files.length;
    }

    public File getFile(int row) 
    {
        return files[row];
    }

    public void setFiles(File[] files) 
    {
        this.files = files;
        fireTableDataChanged();
    }
}

class FileTreeCellRenderer extends DefaultTreeCellRenderer 
{

    private FileSystemView fileSystemView;

    private JLabel label;

    FileTreeCellRenderer() 
    {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean selected,boolean expanded,boolean leaf,int row,boolean hasFocus) 
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        File file = (File)node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) 
        {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else 
        {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}