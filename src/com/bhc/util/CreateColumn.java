package com.bhc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class CreateColumn {
	public void createClumns(final TableViewer viewer,WritableList input,String tablename,Class cclass,Set ceditable){
		Map<String, Object> tcmap=TableControl.analisyTableColunsInfo(tablename);
		Map<String, ColumnsDesc> mcds=(Map<String, ColumnsDesc>) tcmap.get("mcds");
		final Map<String, String> lmap = (Map<String, String>) tcmap.get("lmap");
		String[] cp=(String[]) tcmap.get("cp");
		String[] fields = (String[]) tcmap.get("fields");
		final Map<String, String[]> fmap = new HashMap<String, String[]>();
		viewer.setColumnProperties(cp);
		
		final Table itable = viewer.getTable();

		// itable.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 12, SWT.NORMAL));
		itable.setLinesVisible(true);
		itable.setHeaderVisible(true);
		
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		viewer.setContentProvider(contentProvider);
		IObservableSet knownElements = contentProvider.getKnownElements();
		IObservableMap[] labelMaps=new IObservableMap[mcds.size()];
		
		CellEditor[] ce = new CellEditor[viewer.getColumnProperties().length];
		for (int i = 0; i < mcds.size(); i++) {
			ColumnsDesc cdc=mcds.get(String.valueOf(i));
			final int index = i;
			TableViewerColumn viewcolumn = new TableViewerColumn(viewer,SWT.NONE);
			final TableColumn column = viewcolumn.getColumn();
			column.setText(cdc.getCtitle());
			column.setWidth(Integer.parseInt(cdc.getCwidth()));
			column.setResizable(true);
			column.setMoveable(true);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int dir = itable.getSortDirection();
					MyColumnSorter cs = new MyColumnSorter(lmap);
					cs.setColumn(index);
					if (itable.getSortColumn() == column) {
						dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
					} else {
						dir = SWT.DOWN;
					}
					itable.setSortDirection(dir);
					itable.setSortColumn(column);
					itable.setDragDetect(true);
					viewer.setSorter(cs);
				}
			});

			if(cdc.getCisedit()!=null && cdc.getCisedit().equals("true")){
//				if(cdc.getCtype().equals("select")){
//					String []pv=null;
//					if(cdc.getCname().equals("htmlwidget")){
//						pv=BaseIni.hw;
//					}else if(cdc.getCname().equals("prevsource")){
//						pv=BaseIni.hv;
//					}
//					ce[i] = new ComboBoxCellEditor(itable, pv);
//					fmap.put(cdc.getCcode(), pv);
//				}else{
					ce[i] = new TextCellEditor(itable);
//				}
			}else{
				ce[i]=null;
			}
			labelMaps[i]=BeanProperties.value(cclass,cdc.getCname()).observeDetail(knownElements);
		}

		ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps);
		viewer.setLabelProvider(labelProvider);
		viewer.setCellEditors(ce);
		viewer.setCellModifier(new MyCellModifier(mcds, fmap,ceditable));
		ViewerSupport.bind(viewer, input, BeanProperties.values(fields));
	}
}
