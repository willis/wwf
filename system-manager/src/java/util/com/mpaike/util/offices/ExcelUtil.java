/*
 * Copyright (C) 2009-2012 WWF Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in WWF's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 */
package com.mpaike.util.offices;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author ChenH
 * @date 2012-3-9 TODO 导出Excel的工具类，只需给相应参数即可
 */
public class ExcelUtil {
	/**
	 * 
	 * @param sheetName
	 * @param out
	 * @param lineOne
	 * @param count
	 */

	public static void write(String sheetName, OutputStream out,
			List<String> lineOne, List<List> count) {

		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WritableSheet ws = wwb.createSheet(sheetName, 0);
		Label lab_head;
		// 写到第一行的标题
		for (int i = 0; i < lineOne.size(); i++) {
			lab_head = new Label(i, 0, lineOne.get(i));
			try {
				WritableFont fontNormal = new WritableFont(WritableFont.ARIAL,
						12);
				lab_head.setCellFormat(new WritableCellFormat(fontNormal));

				ws.setColumnView(i, 13);
				ws.addCell(lab_head);
			} catch (RowsExceededException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}

		// 开始写入数据
		jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");
		jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf);
		for (int i = 0; i < count.size(); i++) {
			List<String> obj = count.get(i);
			for (int j = 0; j < obj.size(); j++) {

				try {
					double d = format(obj.get(j));
					jxl.write.Number labelNF = new jxl.write.Number(j, i + 1,
							d, wcfN);
					ws.addCell(labelNF);
				} catch (Exception e1) {
					lab_head = new Label(j, i + 1, (String) obj.get(j));
					try {
						ws.addCell(lab_head);
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			wwb.write();
			wwb.close();
		} catch (Exception e) {
		}
	}

	public static double format(String str) throws Exception {
		if (str.substring(str.length() - 1).equals(" ")) {
			throw new Exception();
		}
		return Double.parseDouble(str);
	}

}
