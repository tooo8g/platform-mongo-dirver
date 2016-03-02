package com.platform.mongo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.platform.io.bean.TreeStruct;

public class Tree {
	
	
	public static void findChild(Document root, List<TreeStruct> certs,
			Map<String, List<TreeStruct>> pids) {
		findChild(root, certs, pids, true);
	}

	public static void findChild(Document root, List<TreeStruct> certs,
			Map<String, List<TreeStruct>> pids,boolean flag) {
		if(flag){
			certs = sortTree(certs);
		}
		for (TreeStruct cert : certs) {
			Document v = new Document();
			v.put(cert.getName(), cert.getValue());
			List<Document> c = root.get("childs", List.class);
			if (c == null) {
				c = new ArrayList<Document>();
				root.put("childs", c);
			}
			c.add(v);
			List<TreeStruct> childs = pids.get(cert.getId());
			if (childs != null)
				findChild(v, childs, pids);
		}
	}

	public static List<TreeStruct> sortTree(List<TreeStruct> tree) {
		TreeStruct[] t = tree.toArray(new TreeStruct[tree.size()]);
		Arrays.sort(t, new Comparator<TreeStruct>() {

			public int compare(TreeStruct o1, TreeStruct o2) {
				int cp = 0;
				if (o1.getPy_char() > o2.getPy_char())
					cp = 1;
				else if (o1.getPy_char() < o2.getPy_char())
					cp = -1;
				return cp;
			}
		});

		List<TreeStruct> data = new ArrayList<TreeStruct>();
		char title = 0;
		for (TreeStruct ts : t) {
			if (ts.getPy_char() != title) {
				title = ts.getPy_char();
				data.add(new TreeStruct("", "name_title", String.valueOf(
						ts.getPy_char()).toUpperCase()));
			}
			data.add(ts);
		}

		return data;
	}
}
