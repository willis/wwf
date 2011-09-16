package com.mpaike.core.database.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;

@SuppressWarnings("serial")
public class OrderBy extends Condition {
	private OrderType orderType;

	protected OrderBy(String field, OrderType orderType) {
		this.field = field;
		this.orderType = orderType;
	}

	public static OrderBy asc(String field) {
		return new OrderBy(field, OrderType.ASC);
	}

	public static OrderBy desc(String field) {
		return new OrderBy(field, OrderType.DESC);
	}

	public Order getOrder() {
		Order order = null;
		if (OrderType.ASC == orderType) {
			order = Order.asc(getField());
		} else if (OrderType.DESC == orderType) {
			order = Order.desc(getField());
		}
		return order;
	}

	public static Order[] asOrders(OrderBy[] orderBys) {
		if (orderBys != null) {
			List<Order> list = new ArrayList<Order>();
			for (int i = 0; i < orderBys.length; i++) {
				if(orderBys[i]!=null){
					list.add(orderBys[i].getOrder());
				}
			}
			if(list.size()>0){
				return list.toArray(new Order[0]);
			}else{
				return null;
			}
		} else {
			return null;
		}

	}
	
	public static String asOrdersString(OrderBy[] orderBys) {
		if (orderBys != null) {
			StringBuilder sb = new StringBuilder(" order by ");
			for (int i = 0,n=orderBys.length; i < n; i++) {
				if(orderBys[i]==null){
					continue;
				}
				sb.append(orderBys[i].getField());
				if(orderBys[i].orderType==OrderType.ASC){
					sb.append(" asc");
				}else{
					sb.append(" desc");
				}
				if(i!=(n-1)){
					sb.append(",");
				}
			}
			if(sb.toString().equals(" order by ")){
				return "";
			}else{
				return sb.toString();
			}
			
		} else {
			return "";
		}

	}

	public static enum OrderType {
		ASC, DESC
	}

}
