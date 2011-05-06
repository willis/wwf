package cn.com.icore.test;

import java.util.ArrayList;

public class PrimeNumber {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		System.out.print("100以内的质数有为：" );
		int sum = 0;
		for (int i = 2; i < 50000; i++) {
			if (isPrimeNumber(i)) {
				sum += i;
				System.out.print(i + ",");
			}
			
		}
		System.out.println("");
		System.out.println( (System.currentTimeMillis()-t1));;
		long t2 = System.currentTimeMillis();
		ArrayList<Integer> al = new ArrayList<Integer>();
		System.out.print("100以内的质数有为：");
		for (int i = 2; i <= 50000; i++) {
			boolean ok = true;
			for (int t : al) { // 用已有质数集作判断,减少比较次数
				if (i % t == 0) {
					ok = false;
					break;
				}
				if (t > i / 2) {
					break;
				}
			}
			if (ok) {
				System.out.print(i + ",");
				al.add(i);
			}
		}
		System.out.println("");
		System.out.println( (System.currentTimeMillis()-t2));
	}

	public static boolean isPrimeNumber(int number) {
		for (int i = 2; i < number / 2 + 1; i++) {

			if (number % i == 0) {
				return false;
				
			}
		}
		return true;
	}
}